package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.AboutContract;
import com.lex_mung.client_android.mvp.model.entity.AboutEntity;


@ActivityScope
public class AboutPresenter extends BasePresenter<AboutContract.Model, AboutContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private AboutEntity entity;

    @Inject
    public AboutPresenter(AboutContract.Model model, AboutContract.View rootView) {
        super(model, rootView);
    }

    public AboutEntity getEntity() {
        return entity;
    }

    public void setEntity(AboutEntity entity) {
        this.entity = entity;
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
