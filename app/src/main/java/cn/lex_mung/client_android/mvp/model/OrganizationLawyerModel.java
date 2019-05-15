package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrganizationLawyerContract;
import okhttp3.RequestBody;


@ActivityScope
public class OrganizationLawyerModel extends BaseModel implements OrganizationLawyerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrganizationLawyerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLawyerList(pageNum, body);
    }

    @Override
    public Observable<LawyerEntity> getLawyerList1(int pageNum, RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLawyerList1(pageNum, body);
    }
}
