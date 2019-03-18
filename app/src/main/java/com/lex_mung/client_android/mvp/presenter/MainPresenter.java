package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.LogUtils;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.mvp.contract.MainContract;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import com.lex_mung.client_android.mvp.ui.activity.EditInfoActivity;

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

    public void onResume() {
        if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            UserInfoDetailsEntity entity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
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
