package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.SocialResourcesContract;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

@FragmentScope
public class SocialResourcesPresenter extends BasePresenter<SocialResourcesContract.Model, SocialResourcesContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SocialResourcesPresenter(SocialResourcesContract.Model model, SocialResourcesContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(LawsHomePagerBaseEntity entity) {
        if (entity == null
                || entity.getSocialInfo() == null) return;
        int num = 0;
        int num1 = 0;
        if (entity.getSocialInfo().getSocialFunction() != null
                && entity.getSocialInfo().getSocialFunction().size() > 0) {
            StringBuilder text = new StringBuilder();
            for (String s : entity.getSocialInfo().getSocialFunction()) {
                text.append(s).append("\n");
            }
            text.delete(text.length() - 1, text.length());
            mRootView.setSocialFunction(text.toString());
        } else {
            num++;
            mRootView.hideSocialFunctionLayout();
        }
        if (entity.getSocialInfo().getCourt() != null
                && entity.getSocialInfo().getCourt().size() > 0) {
            StringBuilder text = new StringBuilder();
            for (String s : entity.getSocialInfo().getCourt()) {
                text.append(s).append("\n");
            }
            text.delete(text.length() - 1, text.length());
            mRootView.setCourt(text.toString());
        } else {
            num++;
            num1++;
            mRootView.hideCourtLayout();
        }
        if (entity.getSocialInfo().getProcuratorate() != null
                && entity.getSocialInfo().getProcuratorate().size() > 0) {
            StringBuilder text = new StringBuilder();
            for (String s : entity.getSocialInfo().getProcuratorate()) {
                text.append(s).append("\n");
            }
            text.delete(text.length() - 1, text.length());
            mRootView.setP(text.toString());
        } else {
            num++;
            num1++;
            mRootView.hidePLayout();
        }
        if (num1 == 2) {
            mRootView.showNoDataLayout1();
        }
        if (num == 3) {
            mRootView.showNoDataLayout();
        }
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
