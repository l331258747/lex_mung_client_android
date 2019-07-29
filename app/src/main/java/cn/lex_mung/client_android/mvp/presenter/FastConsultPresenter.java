package cn.lex_mung.client_android.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.utils.DecimalUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RxLifecycleUtils;
import me.zl.mvp.utils.StringUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.PayStatusTags;
import cn.lex_mung.client_android.mvp.contract.FastConsultContract;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayResultEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_WX_PAY;

@ActivityScope
public class FastConsultPresenter extends BasePresenter<FastConsultContract.Model, FastConsultContract.View> {
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

    private List<SolutionTypeEntity> solutionTypeEntityList = new ArrayList<>();
    private List<String> solutionTypeStringList = new ArrayList<>();
    private String consultType;
    private int consultTypePos;
    private float payMoney;
    private double balance;
    private int payType = 1;
    private String mobile;
    private String name;
    private int sex = 1;
    private String payOrderNo;

    private boolean flag = false;

    @Inject
    public FastConsultPresenter(FastConsultContract.Model model, FastConsultContract.View rootView) {
        super(model, rootView);
    }

    public void setConsultType(String consultType) {
        this.consultType = consultType;
    }

    public void setConsultTypePos(int consultTypePos) {
        this.consultTypePos = consultTypePos;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    //设置订单原价
    public void setMoney(int position) {
        payMoney = solutionTypeEntityList.get(position).getPrice();
        mRootView.setOrderMoney(String.format(
                AppUtils.getString(mApplication, R.string.text_yuan_money)
                , StringUtils.getStringNum(payMoney)),payMoney);
    }

    public List<String> getSolutionTypeStringList() {
        return solutionTypeStringList;
    }

    public String getMobile() {
        return mobile;
    }

    public void onCreate() {
        solutionTypeEntityList.addAll(new Gson().fromJson(
                DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_TYPE)
                , new TypeToken<List<SolutionTypeEntity>>() {
                }.getType()));
        List<SolutionTypeEntity> solutionTypeEntityList2 = new ArrayList<>();

        for (SolutionTypeEntity entity : solutionTypeEntityList) {
            if (entity.getQuick() == 1) {
                solutionTypeStringList.add(entity.getTypeName());
                solutionTypeEntityList2.add(entity);
            }
        }
        solutionTypeEntityList = solutionTypeEntityList2;
        UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        mobile = userInfoDetailsEntity.getMobile();
        mRootView.setPhone(mobile);
        getUserBalance(userInfoDetailsEntity.getMemberId());
    }

    //获取余额
    private void getUserBalance(int id) {
        mModel.getUserBalance(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BalanceEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BalanceEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            balance = baseResponse.getData().getAllBalanceAmount();
                            mRootView.setBalance(String.format(
                                    AppUtils.getString(mApplication, R.string.text_remaining_amount)
                                    , StringUtils.getStringNum(balance)));
                        }
                    }
                });
    }


    private void getPermission(String name, String ua) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                pay(name, ua);
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

    //支付
    public void pay(String name, String ua) {
        this.name = name;
        if (payMoney == 0) {
            mRootView.showMessage("请选择问题类型");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            mRootView.showMessage("请输入您的称呼");
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
                    getPermission(name, ua);
                    return;
                }
                break;
            case 3:
                if (payMoney > balance) {
                    mRootView.showLackOfBalanceDialog();
                    return;
                }
                break;
        }
//        long money = new BigDecimal(payMoney).multiply(new BigDecimal(100)).intValue();
        long money = (long) DecimalUtil.multiply(payMoney,100);
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);
        map.put("type", payType);

        if (mRootView.getCouponPrice() > 0) {
//            long moneyCoupon = new BigDecimal(mRootView.getCouponPrice()).multiply(new BigDecimal(100)).intValue();
            long moneyCoupon = (long) DecimalUtil.multiply(mRootView.getCouponPrice(),100);
            map.put("deduction", moneyCoupon);//优惠金额
            map.put("useCoupon", 1);//使用优惠券
            map.put("other", mRootView.getCouponId());
        }

        map.put("source", 2);
        map.put("product", 2);
        map.put("ua", ua);
        String sign = "money=" + money + "&type=" + payType + "&source=" + 2 + "&ua=" + ua;
        map.put("sign", AppUtils.encodeToMD5(sign));
        mModel.pay(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading("正在获取支付信息..."))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PayEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PayEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            payOrderNo = baseResponse.getData().getOrderNo();
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_NO, baseResponse.getData().getOrderNo());
                            DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.FAST_CONSULT);
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
                            } else if (payType == 2) {//支付宝
                                Runnable payRunnable = () -> {
                                    PayTask payTask = new PayTask(mRootView.getActivity());
                                    Map<String, String> result = payTask.payV2(baseResponse.getData().getOrderInfo(), true);
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                };
                                new Thread(payRunnable).start();
                            } else {//余额
                                releaseFastConsult();
                            }
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResultEntity payResult = new PayResultEntity((Map<String, String>) msg.obj);
                    if ("9000".equals(payResult.getResultStatus())) {
                        releaseFastConsult();
                        return;
                    } else {
                        DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.FAST_CONSULT);
                        Bundle bundle = new Bundle();
                        bundle.putString(BundleTags.TYPE, "FAST_CONSULT");
                        bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                        bundle.putString(BundleTags.ZFB, payResult.getResultStatus());
                        Intent intent = new Intent(mApplication, PayStatusActivity.class);
                        intent.putExtras(bundle);
                        mRootView.launchActivity(intent);
                    }
                    break;
                }
            }
        }
    };

    /**
     * 刷新列表
     *
     * @param message message
     */
    @Subscriber(tag = REFRESH)
    private void refresh(Message message) {
        switch (message.what) {
            case REFRESH_WX_PAY:
                releaseFastConsult();
                break;
        }
    }

    //发送快速咨询（需要先支付才可以调用）
    private void releaseFastConsult() {
        Map<String, Object> map = new HashMap<>();
        map.put("payOrderNo", payOrderNo);
        map.put("payType", payType);
        map.put("payAmount", payMoney);
        map.put("phone", mobile);
        map.put("sex", sex);
        map.put("name", name);
        map.put("typeId", solutionTypeEntityList.get(consultTypePos).getId());
        mModel.releaseFastConsult(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OrderStatusEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OrderStatusEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.FAST_CONSULT);
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_TYPE, consultType);
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_MONEY, String.format(mApplication.getString(R.string.text_yuan_money), StringUtils.getStringNum(payMoney)
                            ));

                            Bundle bundle = new Bundle();
                            bundle.putString(BundleTags.TYPE, "FAST_CONSULT");
                            bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                            if (payType == 1) {
                                bundle.putInt(BundleTags.WX, BaseResp.ErrCode.ERR_OK);
                            } else if (payType == 2) {
                                bundle.putString(BundleTags.ZFB, "9000");
                            }
                            Intent intent = new Intent(mApplication, PayStatusActivity.class);
                            intent.putExtras(bundle);
                            mRootView.launchActivity(intent);
                            mRootView.killMyself();
                        }
                    }
                });
    }

    //获取优惠券
    public void getCoupon(){
        mModel.quickCoupon()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<OrderCouponEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<OrderCouponEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if(baseResponse.getData().getList() != null
                                    && baseResponse.getData().getList().size() > 0
                                    && baseResponse.getData().getList().get(0).getCouponStatus() == 1){
                                mRootView.setCouponLayout(baseResponse.getData().getList().get(0),false);
                            }else{
                                mRootView.setCouponLayout(null,false);
                            }
                        }else{
                            mRootView.setCouponLayout(null,false);
                        }
                    }
                });
    }

    public void getPrice(int couponId,double orderAmount){
        mModel.quickPay(couponId,orderAmount)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<QuickPayEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<QuickPayEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setPriceLayout(baseResponse.getData().getOrderAmount()
                                    ,baseResponse.getData().getDeductionAmount()
                                    ,baseResponse.getData().getPayment());
                            payMoney = baseResponse.getData().getPayment();
                        }
                    }
                });
    }

    //服务规范url
    public void tariffExplanationUrl() {
        mModel.tariffExplanationUrl()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AgreementEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AgreementEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            Bundle bundle = new Bundle();
                            bundle.clear();
                            bundle.putString(BundleTags.URL, baseResponse.getData().getTariffExplanationUrl());
                            bundle.putString(BundleTags.TITLE, "绿豆圈交易流程及服务规范");
                            bundle.putBoolean(BundleTags.IS_SHARE, false);
                            mRootView.launchActivity(new Intent(mApplication, WebActivity.class), bundle);

                        }
                    }
                });
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
