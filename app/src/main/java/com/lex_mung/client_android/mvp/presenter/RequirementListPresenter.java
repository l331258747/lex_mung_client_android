package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.RequirementListContract;
import com.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;
import com.lex_mung.client_android.mvp.ui.adapter.RequirementAdapter;

import java.util.List;


@ActivityScope
public class RequirementListPresenter extends BasePresenter<RequirementListContract.Model, RequirementListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RequirementListPresenter(RequirementListContract.Model model, RequirementListContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(List<RequirementStatusEntity> list) {
        RequirementAdapter adapter = new RequirementAdapter(list);
        mRootView.initRecyclerView(adapter);
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
