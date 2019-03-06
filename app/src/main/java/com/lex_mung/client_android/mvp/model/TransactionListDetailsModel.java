package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.TradingListDetailsContract;


@ActivityScope
public class TransactionListDetailsModel extends BaseModel implements TradingListDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TransactionListDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}
