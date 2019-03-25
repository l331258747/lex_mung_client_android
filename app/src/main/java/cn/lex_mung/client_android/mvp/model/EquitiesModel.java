package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.EquitiesContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;

import java.util.List;


@FragmentScope
public class EquitiesModel extends BaseModel implements EquitiesContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquitiesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<EquitiesListEntity>>> getEquitiesList() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getEquitiesList();
    }

    @Override
    public Observable<BaseResponse<List<EquitiesListEntity>>> getEquitiesList_1() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getEquitiesList_1();
    }

    @Override
    public Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails(int orgId, int levelId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getEquitiesDetails(orgId, levelId);
    }

    @Override
    public Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLawyerList1(pageNum, body);
    }
}
