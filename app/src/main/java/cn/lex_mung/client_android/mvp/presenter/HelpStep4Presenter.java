package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStep4Contract;
import cn.lex_mung.client_android.mvp.model.entity.help.RequireTypeBean;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;


@FragmentScope
public class HelpStep4Presenter extends BasePresenter<HelpStep4Contract.Model, HelpStep4Contract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HelpStep4Presenter(HelpStep4Contract.Model model, HelpStep4Contract.View rootView) {
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

    public void setList(List<RequireTypeBean> datas) {
        mRootView.setAdapter(datas);
    }
}
