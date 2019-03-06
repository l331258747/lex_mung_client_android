package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.LoginContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.api.LoginService;
import com.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.TokenEntity;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> getCode(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .getCode(body);
    }

    @Override
    public Observable<BaseResponse<TokenEntity>> login(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .login(body);
    }

    @Override
    public Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserInfoDetail();
    }

    @Override
    public Observable<BaseResponse<TokenEntity>> getCode(String phone) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .getCode(phone);
    }

    @Override
    public Observable<BaseResponse<AgreementEntity>> lawyerRegisterAgreementUrl() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .userRegisterAgreenmentUrl();
    }
}
