package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;

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
import me.zl.mvp.utils.RegexUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.LoginContract;
import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.TokenEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;

import java.util.HashMap;
import java.util.Map;

import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int code;
    private MyCountDownTimer myCountDownTimer;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    public void initTimer() {
        myCountDownTimer = new MyCountDownTimer(60000, 1000);
    }

    private class MyCountDownTimer extends CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mRootView.setCodeButtonStatus(false, "重新获取 (" + l / 1000 + ")", AppUtils.getColor(mApplication, R.color.c_b5b5b5));

        }

        @Override
        public void onFinish() {
            mRootView.setCodeButtonStatus(true, "获取验证码", AppUtils.getColor(mApplication, R.color.c_06a66a));
        }
    }

    public void getCode(String phone) {
        if (TextUtils.isEmpty(phone)) {
            mRootView.showMessage("请输入手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            mRootView.showMessage("请输入正确的手机号码");
            return;
        }
        code = (int) ((Math.random() * 9 + 1) * 100000);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        mModel.getCode(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showMessage("验证码发送成功，请注意查收");
                            myCountDownTimer.start();
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void getCode1(String phone) {
        if (TextUtils.isEmpty(phone)) {
            mRootView.showMessage("请输入手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            mRootView.showMessage("请输入正确的手机号码");
            return;
        }
        code = (int) ((Math.random() * 9 + 1) * 100000);
        mModel.getCode(phone)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TokenEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<TokenEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showCode(baseResponse.getData().getCode());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void login(String phone, String verifyCode, String jPushId) {
        if (TextUtils.isEmpty(phone)) {
            mRootView.showMessage("请输入手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            mRootView.showMessage("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            mRootView.showMessage("请输入验证码");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("verifyCode", verifyCode);
        map.put("code", code);
        if (!TextUtils.isEmpty(jPushId)) {
            map.put("jpushId", jPushId);
        }
        String json = new Gson().toJson(map);
        mModel.login(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TokenEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<TokenEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            DataHelper.setStringSF(mApplication, DataHelperTags.TOKEN, baseResponse.getData().getToken());
                            DataHelper.setBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS, true);
                            getUserInfoDetail();
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    private void getUserInfoDetail() {
        mModel.getUserInfoDetail()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoDetailsEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            String json = new Gson().toJson(baseResponse.getData());
                            DataHelper.setStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL, json);
                            AppUtils.post(LOGIN_INFO, LOGIN);
                            mRootView.killMyself();
                        }
                    }
                });
    }

    public void getAgreement() {
        mModel.lawyerRegisterAgreementUrl()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
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
                            bundle.putString(BundleTags.URL, baseResponse.getData().getUserRegisterAgreenmentUrl());
                            bundle.putString(BundleTags.TITLE, "用户服务协议");
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
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
            myCountDownTimer = null;
        }
    }
}
