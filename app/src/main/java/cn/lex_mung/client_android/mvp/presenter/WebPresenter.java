package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.WebContract;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;

import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;


@ActivityScope
public class WebPresenter extends BasePresenter<WebContract.Model, WebContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private boolean isLogin = false;//是否登录

    @Inject
    public WebPresenter(WebContract.Model model, WebContract.View rootView) {
        super(model, rootView);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void onResume() {
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
    }

    public void getUserInfoDetail() {
        mModel.getUserInfoDetail()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
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
    }
}
