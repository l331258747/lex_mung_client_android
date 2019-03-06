package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.LawyerListContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerEntity;

import java.util.List;

@ActivityScope
public class LawyerListModel extends BaseModel implements LawyerListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LawyerListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<List<BusinessTypeEntity>>> getBusinessType() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getBusinessType();
    }

    @Override
    public Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLawyerList(pageNum, body);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}
