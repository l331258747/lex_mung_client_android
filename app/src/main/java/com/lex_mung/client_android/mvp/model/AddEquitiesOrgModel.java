package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.AddEquitiesOrgContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;


@ActivityScope
public class AddEquitiesOrgModel extends BaseModel implements AddEquitiesOrgContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AddEquitiesOrgModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> addEquitiesOrg(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .addEquitiesOrg(body);
    }
}
