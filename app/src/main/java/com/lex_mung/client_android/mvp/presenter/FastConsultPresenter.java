package com.lex_mung.client_android.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.mvp.contract.FastConsultContract;
import com.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.PayEntity;
import com.lex_mung.client_android.mvp.model.entity.PayResultEntity;
import com.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import com.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private double money;
    private double balance;
    private int payType;
    private String mobile;
    private int sex;

    private boolean flag = false;

    @Inject
    public FastConsultPresenter(FastConsultContract.Model model, FastConsultContract.View rootView) {
        super(model, rootView);
    }

    public void setConsultType(String consultType) {
        this.consultType = consultType;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setMoney(int position) {
        money = solutionTypeEntityList.get(position).getPrice();
        mRootView.setOrderMoney(String.format(
                AppUtils.getString(mApplication, R.string.text_yuan_money)
                , AppUtils.formatAmount(mApplication, money)));
        mRootView.setMoney(String.format(
                AppUtils.getString(mApplication, R.string.text_yuan_money)
                , AppUtils.formatAmount(mApplication, money)));
    }

    public String getConsultType() {
        return consultType;
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
        for (SolutionTypeEntity entity : solutionTypeEntityList) {
            if (entity.getQuick() == 1) {
                solutionTypeStringList.add(entity.getTypeName());
            }
        }
        UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        mobile = userInfoDetailsEntity.getMobile();
        mRootView.setPhone(mobile);
        getUserBalance(userInfoDetailsEntity.getMemberId());
    }

    private void getUserBalance(int id) {
        mModel.getUserBalance(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
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

    public void setPayType(int payType) {
        this.payType = payType;
    }

    private void getPermission(String ua) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                pay(ua);
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

    public void pay(String ua) {
        if (money == 0) {
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
                    getPermission(ua);
                    return;
                }
                break;
            case 3:
                if (money > balance) {
                    mRootView.showLackOfBalanceDialog();
                    return;
                }
                break;
        }
        money = new BigDecimal(money).multiply(new BigDecimal(100)).intValue();
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);
        map.put("type", payType);
        map.put("source", 2);
        map.put("product", 2);
        map.put("ua", ua);
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
                                DataHelper.setIntergerSF(mApplication, "PAY_TYPE", 2);
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
                            } else {//余额
                                DataHelper.setIntergerSF(mApplication, "PAY_TYPE", 2);
                                Bundle bundle = new Bundle();
                                bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                                Intent intent = new Intent(mApplication, PayStatusActivity.class);
                                intent.putExtras(bundle);
                                mRootView.launchActivity(intent);
                                mRootView.killMyself();
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
                    DataHelper.setIntergerSF(mApplication, "PAY_TYPE", 2);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
