package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.SearchResortInstitutionContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.InstitutionEntity;


@ActivityScope
public class SearchResortInstitutionModel extends BaseModel implements SearchResortInstitutionContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SearchResortInstitutionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<InstitutionEntity>> getCourt(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getCourt(body);
    }

    @Override
    public Observable<BaseResponse<InstitutionEntity>> getProcuratorate(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getProcuratorate(body);
    }
}
