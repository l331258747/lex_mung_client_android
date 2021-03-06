package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.entity.other.LaunchLocationEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.LaunchContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;


@ActivityScope
public class LaunchModel extends BaseModel implements LaunchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LaunchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> appStartUp() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .appStartUp();
    }

    @Override
    public Observable<LaunchLocationEntity> cityjson() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .cityjson();
    }
}
