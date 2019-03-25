package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.IntegrityContract;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;


@FragmentScope
public class IntegrityPresenter extends BasePresenter<IntegrityContract.Model, IntegrityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public IntegrityPresenter(IntegrityContract.Model model, IntegrityContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(LawsHomePagerBaseEntity entity) {
        if (entity == null
                || entity.getReliabilityInfo() == null) return;
        int num = 0;
        if (entity.getReliabilityInfo().getOrgTags() != null
                && entity.getReliabilityInfo().getOrgTags().size() > 0) {
            mRootView.setOrgAdapter2(entity.getReliabilityInfo().getOrgTags());
        } else {
            num++;
            mRootView.hideOrgLayout2();
        }
        if (entity.getReliabilityInfo().getSocialFunction() != null
                && entity.getReliabilityInfo().getSocialFunction().size() > 0) {
            StringBuilder text = new StringBuilder();
            for (String s : entity.getReliabilityInfo().getSocialFunction()) {
                text.append(s).append("\n");
            }
            text.delete(text.length() - 1, text.length());
            mRootView.setSocialFunction(text.toString());
        } else {
            num++;
            mRootView.hideSocialFunctionLayout();
        }
        if (entity.getReliabilityInfo().getHonor() != null
                && entity.getReliabilityInfo().getHonor().size() > 0) {
            StringBuilder text = new StringBuilder();
            for (String s : entity.getReliabilityInfo().getHonor()) {
                text.append(s).append("\n");
            }
            text.delete(text.length() - 1, text.length());
            mRootView.setPersonalHonor(text.toString());
        } else {
            num++;
            mRootView.hidePersonalHonorLayout();
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
