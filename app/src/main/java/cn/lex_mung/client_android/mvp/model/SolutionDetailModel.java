package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonMarkEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.ActivityEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.SolutionDetailContract;
import okhttp3.RequestBody;


@ActivityScope
public class SolutionDetailModel extends BaseModel implements SolutionDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SolutionDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerHomeList(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLawyerHomeList(1,5,body);
    }

    @Override
    public Observable<BaseResponse<List<CommonMarkEntity>>> commonMarks(int solutionId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .commonMarks(solutionId);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<CommonFreeTextEntity>>> commonFreeText(RequestBody body, boolean isLogin) {
        if(isLogin){
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .lawyerFreeText(body);
        }
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .commonFreeText(body);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<SolutionListEntity>>> getSolutionList(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSolutionList(body);
    }

    @Override
    public Observable<BaseResponse<List<CommonPageContractsEntity>>> commonPageContracts() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .commonPageContracts();
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<ActivityEntity>>> popupList(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .popupList(body);
    }
}
