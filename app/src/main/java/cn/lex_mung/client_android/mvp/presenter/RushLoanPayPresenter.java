package cn.lex_mung.client_android.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.simple.eventbus.Subscriber;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.PayStatusTags;
import cn.lex_mung.client_android.mvp.contract.RushLoanPayContract;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayResultEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementCreateEntity;
import cn.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;
import cn.lex_mung.client_android.mvp.ui.activity.RushOrdersActivity;
import cn.lex_mung.client_android.utils.DecimalUtil;
import cn.lex_mung.client_android.utils.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import static cn.lex_mung.client_android.app.EventBusTags.PAY_INFO.PAY_CONFIRM;
import static cn.lex_mung.client_android.app.EventBusTags.PAY_INFO.PAY_INFO;


@ActivityScope
public class RushLoanPayPresenter extends BasePresenter<RushLoanPayContract.Model, RushLoanPayContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RxPermissions mRxPermissions;

    private int requirementId = -1;

    private int requireTypeId = -1;//当前选择的需求ID
    private String requireTypeName;//当前选择的需求Name
    private double balance;//当前余额
    private int payType = 1;//支付方式
    private int couponId;

    private float payMoney;//实付金额
    private float deduction;//优惠抵扣金额

    private boolean flag = false;

    public void setRequireTypeId(int requireTypeId){
        this.requireTypeId = requireTypeId;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public void setPayMoney(float payMoney){
        this.payMoney = payMoney;
    }

    public void setRequireTypeName(String requireTypeName){
        this.requireTypeName = requireTypeName;
    }

    public void onCreate(int requireTypeId) {
        mRootView.showPayLayout();
        mRootView.setOrderMoney(String.format(mApplication.getString(R.string.text_yuan_money), AppUtils.formatAmount(mApplication, 0)));
    }


    public void getUserBalance() {
        UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        mModel.getUserBalance(userInfoDetailsEntity.getMemberId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BalanceEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BalanceEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            balance = baseResponse.getData().getBalanceAmount();
                            mRootView.setBalance(String.format(
                                    AppUtils.getString(mApplication, R.string.text_remaining_amount)
                                    , AppUtils.formatAmount(mApplication, balance)));
                        }
                    }
                });
    }

    public void releaseRequirement(String ua) {
        if (requireTypeId == -1) {
            mRootView.showMessage(mApplication.getString(R.string.text_please_select_service_type));
            return;
        }

        switch (payType) {
            case 1:
                if (!AppUtils.isWeixinAvilible(mRootView.getActivity())) {
                    AppUtils.makeText(mRootView.getActivity(), "微信未安装");
                    return;
                }
                break;
            case 2:
                if (!flag) {
                    getPermission(ua);
                    return;
                }
                break;
            case 3://余额支付
                if (payMoney > balance) {
                    mRootView.showLackOfBalanceDialog();
                    return;
                }
                break;
        }

        if (payMoney == 0) {
            mRootView.showMessage(mApplication.getString(R.string.text_amount_of_error));
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("requirementTypeId", requireTypeId);
        map.put("requirementTypeName", requireTypeName);
        mModel.requirementCreate(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RequirementCreateEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RequirementCreateEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            pay(ua, requirementId = Integer.valueOf(baseResponse.getData().getRequirementId()));
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    private void getPermission(String ua) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                releaseRequirement(ua);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法前往支付宝支付");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
            }
        }, mRxPermissions, mErrorHandler);
    }

    private void pay(String ua, int id) {
        long money = (long) DecimalUtil.multiply(payMoney,100);
        LogUtil.e("1:"+new BigDecimal(payMoney));
        LogUtil.e("1:"+new BigDecimal(100));
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);//金额
        map.put("type", payType);//支付类型 1微信 2支付宝 3余额支付 4会员卡支付
        if (deduction > 0) {
            map.put("deduction", deduction);//优惠金额
        }
        map.put("source", 2);//来源 2app
        map.put("product", 5);//订单类型 5发需求
        map.put("ua", ua);//ua
        if (payType == 4
                && couponId > 0) {//会员卡支付并且有会员卡
            map.put("useCoupon", 1);//使用优惠券
            map.put("other", "{\"requirementId\":" + id + ",\"couponId\":" + couponId + "}");
        } else {
            map.put("useCoupon", 0);//不使用优惠券
            map.put("other", "{\"requirementId\":" + id + "}");
        }
        String sign = "money=" + money + "&type=" + payType + "&source=" + 2 + "&ua=" + ua;
        map.put("sign", AppUtils.encodeToMD5(sign));
        mModel.pay(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading("正在获取支付信息..."))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PayEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PayEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_TYPE, requireTypeName);
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_MONEY, String.format(mApplication.getString(R.string.text_yuan_money), AppUtils.formatAmount(mApplication, payMoney)));
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_NO, baseResponse.getData().getOrderNo());
                            if (payType == 1) {//微信
                                DataHelper.setStringSF(mApplication, DataHelperTags.APP_ID, baseResponse.getData().getAppid());
                                IWXAPI api = WXAPIFactory.createWXAPI(mApplication, baseResponse.getData().getAppid(), true);
                                api.registerApp(baseResponse.getData().getAppid());
                                PayReq request = new PayReq();
                                request.appId = baseResponse.getData().getAppid();
                                request.partnerId = baseResponse.getData().getPartnerId();
                                request.prepayId = baseResponse.getData().getPrepayId();
                                request.packageValue = baseResponse.getData().getPkg();
                                request.nonceStr = baseResponse.getData().getNonceStr();
                                request.timeStamp = baseResponse.getData().getTimestamp();
                                request.sign = baseResponse.getData().getSign();
                                api.sendReq(request);
                                DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.RELEASE_DEMAND);
                            } else if (payType == 2) {//支付宝
                                Runnable payRunnable = () -> {
                                    PayTask payTask = new PayTask(mRootView.getActivity());
                                    Map<String, String> result = payTask.payV2(baseResponse.getData().getOrderInfo(), true);

                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {//余额or会员卡支付
                                DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.RELEASE_DEMAND);
                                Bundle bundle = new Bundle();
                                bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                                Intent intent = new Intent(mApplication, PayStatusActivity.class);
                                intent.putExtras(bundle);
                                mRootView.launchActivity(intent);
                            }
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    /**
     * 跳转抢单界面
     */
    @Subscriber(tag = PAY_INFO)
    private void goRushLoan(Message message) {
        switch (message.what) {
            case PAY_CONFIRM:
                mRootView.killMyself();
                Bundle bundle = new Bundle();
                bundle.clear();
                bundle.putInt(BundleTags.ID, requirementId);
                mRootView.launchActivity(new Intent(mApplication,RushOrdersActivity.class),bundle);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.RELEASE_DEMAND);
                    PayResultEntity payResult = new PayResultEntity((Map<String, String>) msg.obj);
                    Bundle bundle = new Bundle();
                    bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                    bundle.putString(BundleTags.ZFB, payResult.getResultStatus());
                    Intent intent = new Intent(mApplication, PayStatusActivity.class);
                    intent.putExtras(bundle);
                    mRootView.launchActivity(intent);
                    break;
                }
            }
        }
    };

    @Inject
    public RushLoanPayPresenter(RushLoanPayContract.Model model, RushLoanPayContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
