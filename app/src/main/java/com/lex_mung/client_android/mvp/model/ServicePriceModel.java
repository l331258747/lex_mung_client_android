package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.ServicePriceContract;


@FragmentScope
public class ServicePriceModel extends BaseModel implements ServicePriceContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ServicePriceModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}
