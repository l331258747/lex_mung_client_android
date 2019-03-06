package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.MyAccountContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;

@ActivityScope
public class MyAccountModel extends BaseModel implements MyAccountContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyAccountModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BalanceEntity>> getUserBalance(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserBalance(id);
    }
}
