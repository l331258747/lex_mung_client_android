package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.PublicLawyerContract;
import okhttp3.RequestBody;


@ActivityScope
public class PublicLawyerModel extends BaseModel implements PublicLawyerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PublicLawyerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails(int orgId, int levelId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getEquitiesDetails(orgId,levelId);
    }

    @Override
    public Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails1(int orgId, int levelId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getEquitiesDetails1(orgId,levelId);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList(int pageNum, RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLawyerList(pageNum,body);
    }
}
