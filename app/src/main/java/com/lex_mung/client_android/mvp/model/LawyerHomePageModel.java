package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.LawyerHomePageContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;


@ActivityScope
public class LawyerHomePageModel extends BaseModel implements LawyerHomePageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LawyerHomePageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLawsHomePagerBase(id);
    }

    @Override
    public Observable<BaseResponse> follow(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .follow(id);
    }

    @Override
    public Observable<BaseResponse> unFollow(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .unFollow(id);
    }
}
