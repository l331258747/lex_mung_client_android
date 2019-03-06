package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.LaunchContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;


@ActivityScope
public class LaunchPresenter extends BasePresenter<LaunchContract.Model, LaunchContract.View> {
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

    @Inject
    public LaunchPresenter(LaunchContract.Model model, LaunchContract.View rootView) {
        super(model, rootView);
    }

    public void getPermission() {
        getAllDepreg();
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.launch();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.launch();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.launch();
            }
        }, mRxPermissions, mErrorHandler);
    }

    private void getAllDepreg() {
        mModel.appStartUp()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
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
