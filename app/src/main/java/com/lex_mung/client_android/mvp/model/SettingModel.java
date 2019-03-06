package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.SettingContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.api.LoginService;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.VersionEntity;

@ActivityScope
public class SettingModel extends BaseModel implements SettingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SettingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> logout() {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .logout();
    }

    @Override
    public Observable<BaseResponse<VersionEntity>> checkVersion() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .checkVersion();
    }
}
