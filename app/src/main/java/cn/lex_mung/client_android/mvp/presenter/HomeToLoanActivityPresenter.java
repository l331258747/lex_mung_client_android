package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import cn.lex_mung.client_android.app.DataHelperTags;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HomeToLoanActivityContract;
import me.zl.mvp.utils.DataHelper;


@ActivityScope
public class HomeToLoanActivityPresenter extends BasePresenter<HomeToLoanActivityContract.Model, HomeToLoanActivityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private boolean isLogin;

    @Inject
    public HomeToLoanActivityPresenter(HomeToLoanActivityContract.Model model, HomeToLoanActivityContract.View rootView) {
        super(model, rootView);
    }

    public void onResume() {
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
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
