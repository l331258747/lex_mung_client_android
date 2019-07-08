package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.PracticeExperienceContract;
import okhttp3.RequestBody;


@FragmentScope
public class PracticeExperienceModel extends BaseModel implements PracticeExperienceContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PracticeExperienceModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<CaseListEntity>>> getCaseList(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getCaseList(body);
    }
}
