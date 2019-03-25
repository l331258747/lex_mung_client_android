package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.MeContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.AboutEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

@FragmentScope
public class MeModel extends BaseModel implements MeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserInfoDetail();
    }

    @Override
    public Observable<BaseResponse<AboutEntity>> getAbout() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getAbout();
    }
}
