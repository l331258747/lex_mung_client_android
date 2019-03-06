package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.FindLawyerContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerEntity;

import java.util.List;

@FragmentScope
public class FindLawyerModel extends BaseModel implements FindLawyerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FindLawyerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
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
}
