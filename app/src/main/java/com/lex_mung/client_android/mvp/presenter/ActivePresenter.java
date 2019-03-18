package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.ActiveContract;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

@FragmentScope
public class ActivePresenter extends BasePresenter<ActiveContract.Model, ActiveContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ActivePresenter(ActiveContract.Model model, ActiveContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(LawsHomePagerBaseEntity entity) {
        if (entity == null || entity.getActivityInfo() == null
                || entity.getActivityInfo().size() == 0) {
            mRootView.noDataLayout();
            return;
        }
        mRootView.setAdapter(entity.getActivityInfo());
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
