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
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.PayStatusTags;
import cn.lex_mung.client_android.mvp.contract.RushLoanPayContract;
import cn.lex_mung.client_android.mvp.model.entity.AmountBalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayResultEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.CommodityContentEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementCreateEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderConfirmChildEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderConfirmEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderPayEntity;
import cn.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;
import cn.lex_mung.client_android.mvp.ui.activity.RushOrdersActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.utils.DecimalUtil;
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
import me.zl.mvp.utils.StringUtils;
import okhttp3.RequestBody;

import static cn.lex_mung.client_android.app.EventBusTags.BUY_EQUITY_500_INFO.BUY_EQUITY_500;
import static cn.lex_mung.client_android.app.EventBusTags.BUY_EQUITY_500_INFO.BUY_EQUITY_500_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.PAY_INFO.PAY_CONFIRM;
import static cn.lex_mung.client_android.app.EventBusTags.PAY_INFO.PAY_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_WX_PAY;


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
    private int payTypeGroup;//支付方式为6 带的集团id（因为集团卡有多个）

    private float payMoney;//实付金额
    private int type;//1 快速咨询，2 热门需求，3付费权益

    private int meetNum;//线下见面次数
    private List<String> legalAdviserIds;//子项目ids

    private String lawsuiId;

    private boolean flag = false;

    public void setMeetNum(int meetNum) {
        this.meetNum = meetNum;
    }

    public void setLawsuiId(String lawsuiId) {
        this.lawsuiId = lawsuiId;
    }

    public void setLegalAdviserIds(List<String> legalAdviserIds) {
        this.legalAdviserIds = legalAdviserIds;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public void setPayType(int payType, int payTypeGroup) {
        this.payType = payType;
        this.payTypeGroup = payTypeGroup;
    }

    public void setPayMoney(float payMoney) {
        this.payMoney = payMoney;
    }

    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }

    //获取余额

    public void setType(int type) {
        this.type = type;
    }
    public void getAllBalance() {
        Map<String, Object> map = new HashMap<>();
        mModel.amountBalance(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AmountBalanceEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AmountBalanceEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData().getAmount() != null) {
                                balance = baseResponse.getData().getAmount().getAllBalanceAmount();
                            }
                            mRootView.setAllBalance(baseResponse.getData());

                            mRootView.setPayTypeViewSelect(payMoney);
                        }
                    }
                });
    }

    //热门需求 start------------
    public void releaseRequirementCreate(String ua) {
        if (requireTypeId == -1) {
            mRootView.showMessage("请选择服务类型");
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
                    releaseRequirementPermission(ua);
                    return;
                }
                break;
            case 3://余额支付
                if (payMoney > mRootView.getTypeBalance(3, 0)) {
                    mRootView.showLackOfBalanceDialog();
                    return;
                }
                break;
            case 6://集团卡支付
                if (payMoney > mRootView.getTypeBalance(6, payTypeGroup)) {
                    mRootView.showMessage("集团卡余额不足");
                    return;
                }
                break;
        }

        if (payMoney == 0) {
            mRootView.showMessage("金额错误");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("requirementTypeId", requireTypeId);
        map.put("requirementTypeName", requireTypeName);
        mModel.requirementCreate(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RequirementCreateEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RequirementCreateEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            releaseRequirementPay(ua, requirementId = Integer.valueOf(baseResponse.getData().getRequirementId()));
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    private void releaseRequirementPermission(String ua) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                releaseRequirementCreate(ua);
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

    private void releaseRequirementPay(String ua, int id) {
        long money = (long) DecimalUtil.multiply(payMoney, 100);
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);//金额
        map.put("type", payType);//支付类型 1微信 2支付宝 3余额支付 4会员卡支付 5百度 6集团卡

        map.put("source", 2);//来源 2app
        map.put("product", 5);//订单类型 5发需求
        map.put("ua", ua);//ua

        if (mRootView.getCouponPrice() > 0) {
            long moneyCoupon = (long) DecimalUtil.multiply(mRootView.getCouponPrice(), 100);
            map.put("deduction", moneyCoupon);//优惠金额
            map.put("useCoupon", 1);//使用优惠券

            if (payType == 6) {
                map.put("other", "{\"requirementId\":" + id + ",\"couponId\":" + mRootView.getCouponId() + ",\"groupCardId\":" + payTypeGroup + "}");
            } else {
                map.put("other", "{\"requirementId\":" + id + ",\"couponId\":" + mRootView.getCouponId() + "}");
            }
        } else {
            map.put("useCoupon", 0);//不使用优惠券
            if (payType == 6) {
                map.put("other", "{\"requirementId\":" + id + ",\"groupCardId\":" + payTypeGroup + "}");
            } else {
                map.put("other", "{\"requirementId\":" + id + "}");
            }
        }

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
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_TYPE, requireTypeName);
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_MONEY, String.format(mApplication.getString(R.string.text_yuan_money), StringUtils.getStringNum(payMoney)));
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
                mRootView.launchActivity(new Intent(mApplication, RushOrdersActivity.class), bundle);
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

    //热门需求 end------------

    //快速咨询-------------start
    private String mobile;
    private String payOrderNo;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    //支付
    public void quickPay(String name, String ua) {
        if (payMoney == 0) {
            mRootView.showMessage("请选择问题类型");
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
                    quickPermission(name, ua);
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
        long money = (long) DecimalUtil.multiply(payMoney, 100);
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);
        map.put("type", payType);

        if (mRootView.getCouponPrice() > 0) {
            long moneyCoupon = (long) DecimalUtil.multiply(mRootView.getCouponPrice(), 100);
            map.put("deduction", moneyCoupon);//优惠金额
            map.put("useCoupon", 1);//使用优惠券

            if (payType == 6) {
                map.put("other", "{\"couponId\":" + mRootView.getCouponId() + ",\"groupCardId\":" + payTypeGroup + "}");
            } else {
                map.put("other", "{\"couponId\":" + mRootView.getCouponId() + "}");
            }
        } else {
            map.put("useCoupon", 0);//使用优惠券
            if (payType == 6) {
                map.put("other", "{\"groupCardId\":" + payTypeGroup + "}");
            }
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
                                    mHandler2.sendMessage(msg);
                                };
                                new Thread(payRunnable).start();
                            } else {//余额
                                quickCreate();
                            }
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    private void quickPermission(String name, String ua) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                quickPay(name, ua);
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

    //发送快速咨询（需要先支付才可以调用）
    private void quickCreate() {
        Map<String, Object> map = new HashMap<>();
        map.put("payOrderNo", payOrderNo);
        map.put("payType", payType);
        map.put("payAmount", payMoney);
        map.put("phone", mobile);
        map.put("sex", 1);
        map.put("name", "name");
        map.put("typeId", requireTypeId);
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
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_TYPE, requireTypeName);
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_MONEY, String.format(mApplication.getString(R.string.text_yuan_money), StringUtils.getStringNum(payMoney)));

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
                            AppManager.getAppManager().killActivity(WebActivity.class);//用来关闭快速咨询h5页面
                        }
                    }
                });
    }

    /**
     * 快速咨询 付款后 创建订单
     */
    @Subscriber(tag = REFRESH)
    private void refresh(Message message) {
        switch (message.what) {
            case REFRESH_WX_PAY:
                quickCreate();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler2 = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResultEntity payResult = new PayResultEntity((Map<String, String>) msg.obj);
                    if ("9000".equals(payResult.getResultStatus())) {
                        quickCreate();
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

    //快速咨询-------------end

    //付费权益  诉讼无忧保服务-------start
    //支付
    public void buyEquity500Pay(String ua) {
        if (payMoney == 0) {
            mRootView.showMessage("价格为0无法支付");
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
                    buyEquity500Permission(ua);
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
        long money = (long) DecimalUtil.multiply(payMoney, 100);
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);
        map.put("type", payType);
        map.put("useCoupon", 0);//不使用优惠券

        map.put("other", "{\"lawsuiId\":\"" + lawsuiId + "\",\"requireTypeId\":"+ requireTypeId + "}");

        map.put("source", 2);
        map.put("product", 7);
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
                            DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.ONLINE_LAWYER_500);
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
                                    mHandler3.sendMessage(msg);
                                };
                                new Thread(payRunnable).start();
                            } else {//余额
                                AppUtils.post(BUY_EQUITY_500_INFO,BUY_EQUITY_500,payOrderNo);
                                mRootView.killMyself();
                            }
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    private void buyEquity500Permission(String ua) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                buyEquity500Pay(ua);
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler3 = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResultEntity payResult = new PayResultEntity((Map<String, String>) msg.obj);
                    if ("9000".equals(payResult.getResultStatus())) {
                        AppUtils.post(BUY_EQUITY_500_INFO,BUY_EQUITY_500,payOrderNo);
                        mRootView.killMyself();
                        return;
                    } else {
                        DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.FAST_CONSULT);
                        Bundle bundle = new Bundle();
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
    //付费权益  诉讼无忧保服务-------end


    //付费权益-------start
    public void legalAdviserOrderConfirm(double orderPrice) {
        Map<String, Object> map = new HashMap<>();
        map.put("requireTypeId", requireTypeId);
        map.put("legalAdviserIds", legalAdviserIds);
        map.put("priceTotal", orderPrice);
        map.put("meetNum", meetNum);
        mModel.legalAdviserOrderConfirm(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LegalAdviserOrderConfirmEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LegalAdviserOrderConfirmEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            List<CommodityContentEntity> lists = new ArrayList<>();
                            List<LegalAdviserOrderConfirmChildEntity> data = baseResponse.getData().getServerDetails();
                            for (int i = 0; i < data.size(); i++) {
                                CommodityContentEntity item = new CommodityContentEntity();
                                item.setPrice(data.get(i).getPriceTotal());
                                item.setTitle(data.get(i).getTypeName());
                                lists.add(item);
                            }
                            mRootView.setCommodityContent(lists);
                        }
                    }
                });
    }

    public void buyEquityCreate(String ua) {
        if (requireTypeId == -1) {
            mRootView.showMessage("请选择服务类型");
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
                    buyEquityPermission(ua);
                    return;
                }
                break;
            case 3://余额支付
                if (payMoney > mRootView.getTypeBalance(3, 0)) {
                    mRootView.showLackOfBalanceDialog();
                    return;
                }
                break;
            case 6://集团卡支付
                if (payMoney > mRootView.getTypeBalance(6, payTypeGroup)) {
                    mRootView.showMessage("集团卡余额不足");
                    return;
                }
                break;
        }

        if (payMoney == 0) {
            mRootView.showMessage("金额错误");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("requireTypeId", requireTypeId);
        map.put("legalAdviserIds", legalAdviserIds);
        map.put("priceTotal", mRootView.getOrderPrice());
        map.put("meetNum", meetNum);
        if (mRootView.getCouponPrice() > 0){
            map.put("couponId", mRootView.getCouponId());
        }
        map.put("type", payType);
        mModel.legalAdviserOrderPay(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LegalAdviserOrderPayEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LegalAdviserOrderPayEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            buyEquityPay(ua, baseResponse.getData().getLegalAdviserOrderNo());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    private void buyEquityPermission(String ua) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                buyEquityCreate(ua);
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

    private void buyEquityPay(String ua, String orderNo) {
        long money = (long) DecimalUtil.multiply(payMoney, 100);
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);//金额
        map.put("type", payType);//支付类型 1微信 2支付宝 3余额支付 4会员卡支付 5百度 6集团卡

        map.put("source", 2);//来源 2app
        map.put("product", 7);//订单类型 在线法律顾问
        map.put("ua", ua);//ua

        orderNo = "\"" + orderNo + "\"";
        if (mRootView.getCouponPrice() > 0) {
            long moneyCoupon = (long) DecimalUtil.multiply(mRootView.getCouponPrice(), 100);
            map.put("deduction", moneyCoupon);//优惠金额
            map.put("useCoupon", 1);//使用优惠券

            if (payType == 6) {
                map.put("other", "{\"legalAdviserOrderNo\":" + orderNo + ",\"couponId\":" + mRootView.getCouponId() + ",\"groupCardId\":" + payTypeGroup + "}");
            } else {
                map.put("other", "{\"legalAdviserOrderNo\":" + orderNo + ",\"couponId\":" + mRootView.getCouponId() + "}");
            }
        } else {
            map.put("useCoupon", 0);//不使用优惠券
            if (payType == 6) {
                map.put("other", "{\"legalAdviserOrderNo\":" + orderNo + ",\"groupCardId\":" + payTypeGroup + "}");
            } else {
                map.put("other", "{\"legalAdviserOrderNo\":" + orderNo + "}");
            }
        }

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
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_TYPE, requireTypeName);
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_MONEY, String.format(mApplication.getString(R.string.text_yuan_money), StringUtils.getStringNum(payMoney)));
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
                                DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.ONLINE_LAWYER);
                            } else if (payType == 2) {//支付宝
                                Runnable payRunnable = () -> {
                                    PayTask payTask = new PayTask(mRootView.getActivity());
                                    Map<String, String> result = payTask.payV2(baseResponse.getData().getOrderInfo(), true);

                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = result;
                                    buyEquityHandler.sendMessage(msg);
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {//余额or会员卡支付
                                DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.ONLINE_LAWYER);
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

    @SuppressLint("HandlerLeak")
    private Handler buyEquityHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.ONLINE_LAWYER);
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

    //付费权益-------end

    //获取优惠券
    public void getCoupon(float orderAmount, int productId) {
        if (type == 2) {
            mModel.legalAdviserServerCoupon(orderAmount)
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
                                if (baseResponse.getData().getList() != null
                                        && baseResponse.getData().getList().size() > 0
                                        && baseResponse.getData().getList().get(0).getCouponStatus() == 1) {
                                    mRootView.setCouponLayout(baseResponse.getData().getList().get(0), false);
                                } else {
                                    mRootView.setCouponLayout(null, false);
                                }
                            } else {
                                mRootView.setCouponLayout(null, false);
                            }
                        }
                    });
        } else if (type == 1) {
            mModel.quickCoupon(orderAmount)
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
                                if (baseResponse.getData().getList() != null
                                        && baseResponse.getData().getList().size() > 0
                                        && baseResponse.getData().getList().get(0).getCouponStatus() == 1) {
                                    mRootView.setCouponLayout(baseResponse.getData().getList().get(0), false);
                                } else {
                                    mRootView.setCouponLayout(null, false);
                                }
                            } else {
                                mRootView.setCouponLayout(null, false);
                            }
                        }
                    });
        } else {// 热门需求
            mModel.optimalRequireList(orderAmount, productId)
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
                                if (baseResponse.getData().getList() != null
                                        && baseResponse.getData().getList().size() > 0
                                        && baseResponse.getData().getList().get(0).getCouponStatus() == 1) {
                                    mRootView.setCouponLayout(baseResponse.getData().getList().get(0), false);
                                } else {
                                    mRootView.setCouponLayout(null, false);
                                }
                            } else {
                                mRootView.setCouponLayout(null, false);
                            }
                        }
                    });
        }

    }

    //计算实付优惠金额
    public void getPrice(int couponId, double orderAmount, int productId) {
        if (type == 2) {
            mModel.legalAdviserOrderAmount(couponId, orderAmount)
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
                                mRootView.setPriceLayout(baseResponse.getData().getDeductionAmount()
                                        , baseResponse.getData().getPayment());
                                payMoney = baseResponse.getData().getPayment();

                                mRootView.setPayTypeViewSelect(payMoney);
                            }
                        }
                    });
        } else if (type == 1) {
            mModel.quickPay(couponId, orderAmount)
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
                                mRootView.setPriceLayout(baseResponse.getData().getDeductionAmount()
                                        , baseResponse.getData().getPayment());
                                payMoney = baseResponse.getData().getPayment();

                                mRootView.setPayTypeViewSelect(payMoney);
                            }
                        }
                    });
        } else {
            mModel.optimalRequire(couponId, orderAmount, productId)
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
                                mRootView.setPriceLayout(baseResponse.getData().getDeductionAmount()
                                        , baseResponse.getData().getPayment());
                                payMoney = baseResponse.getData().getPayment();

                                mRootView.setPayTypeViewSelect(payMoney);
                            }
                        }
                    });
        }

    }

    //优惠券数量
    public void getCouponCount(double orderAmount, int productId) {
        Map<String, Object> map = new HashMap<>();
        if (type == 2) {
            map.put("productId", 9998);
        } else if (type == 1) {
            map.put("productId", 9999);
        } else {
            map.put("productId", productId);
        }
        map.put("orderAmount", orderAmount);
        mModel.couponCount(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Integer>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Integer> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData() > 0)
                                mRootView.setCouponCountLayout(baseResponse.getData());
                        }
                    }
                });
    }

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
