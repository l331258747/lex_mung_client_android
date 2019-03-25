package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.SolutionLIstContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;

@FragmentScope
public class SolutionLIstModel extends BaseModel implements SolutionLIstContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SolutionLIstModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<SolutionListEntity>> getSolutionList(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSolutionList(body);
    }
}
