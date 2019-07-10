package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrgAmountEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.ReleaseDemandContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity;

import java.util.List;

@ActivityScope
public class ReleaseDemandModel extends BaseModel implements ReleaseDemandContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ReleaseDemandModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<BusinessEntity>>> releaseDemandList(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .releaseDemandList(body);
    }

    @Override
    public Observable<BaseResponse<ReleaseDemandOrgMoneyEntity>> getReleaseDemandOrgMoney(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getReleaseDemandOrgMoney(body);
    }

    @Override
    public Observable<BaseResponse<BalanceEntity>> getUserBalance(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserBalance(id);
    }

    @Override
    public Observable<BaseResponse<GeneralEntity>> releaseRequirement(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .releaseRequirement(body);
    }

    @Override
    public Observable<BaseResponse<PayEntity>> pay(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .pay(body);
    }

    @Override
    public Observable<BaseResponse<AgreementEntity>> tariffExplanationUrl() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .tariffExplanationUrl();
    }

    @Override
    public Observable<BaseResponse<List<OrgAmountEntity>>> clientOrgAmount() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .clientOrgAmount();
    }
}
