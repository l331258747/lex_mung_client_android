package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.List;

import cn.lex_mung.client_android.mvp.ui.adapter.MoreSocialPositionAdapter;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.MoreSocialPositionContract;


@ActivityScope
public class MoreSocialPositionPresenter extends BasePresenter<MoreSocialPositionContract.Model, MoreSocialPositionContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MoreSocialPositionPresenter(MoreSocialPositionContract.Model model, MoreSocialPositionContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(List<String> list) {
        MoreSocialPositionAdapter socialPositionAdapter = new MoreSocialPositionAdapter(list);
        mRootView.initRecyclerView(socialPositionAdapter);
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
