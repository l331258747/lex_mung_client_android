package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.google.gson.Gson;

import javax.inject.Inject;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.MainContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.VersionEntity;
import cn.lex_mung.client_android.mvp.ui.activity.EditInfoActivity;
import cn.lex_mung.client_android.utils.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.RxLifecycleUtils;

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void checkVersion() {
        mModel.checkVersion()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {})
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<VersionEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<VersionEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData().getHasUpdate() == 1) {//有更新
                                mRootView.startDownload(baseResponse.getData());
                                return;
                            }
                            LogUtil.e("您当前是最新版本!");
                            setHelpDialog();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        setHelpDialog();
                    }
                });
    }

    public void setHelpDialog(){
        if (!DataHelper.getBooleanSF(mRootView.getActivity(), DataHelperTags.IS_ONE_IN)) {
            DataHelper.setBooleanSF(mRootView.getActivity(), DataHelperTags.IS_ONE_IN, true);
            mRootView.showHelpDialog();
        }
    }

    public void onResume() {
        if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            UserInfoDetailsEntity entity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
            if(entity == null){
                DataHelper.setBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS,false);
                return;
            }
            String userId = "lex" + entity.getMemberId();
            String password = AppUtils.encodeToMD5(entity.getMobile());
            JMessageClient.login(userId, password, new BasicCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage) {
                    if (responseCode == 0) {
                        LogUtils.debugInfo("IM登录成功");
                    } else {
                        LogUtils.debugInfo("IM登录失败" + responseCode + "  " + responseMessage);
                    }
                }
            });
            switch (DataHelper.getIntergerSF(mApplication, DataHelperTags.LOGIN_TYPE)) {
                case 1:
                    mRootView.launchActivity(new Intent(mApplication, EditInfoActivity.class));
                    break;
            }
            DataHelper.setIntergerSF(mApplication, DataHelperTags.LOGIN_TYPE, -1);
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
