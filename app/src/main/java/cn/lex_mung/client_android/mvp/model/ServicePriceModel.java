package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.entity.ExpertCallEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.ServicePriceContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceEntity;

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

    @Override
    public Observable<BaseResponse<ExpertPriceEntity>> expertPrice(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .expertPrice(id);
    }

    @Override
    public Observable<BaseResponse<ExpertCallEntity>> sendCall(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .sendCall(id);
    }
}
