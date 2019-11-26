package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.BuyEquityEvaluateContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import io.reactivex.Observable;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;
import okhttp3.RequestBody;


@ActivityScope
public class BuyEquityEvaluateModel extends BaseModel implements BuyEquityEvaluateContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BuyEquityEvaluateModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> legalAdviserOrderEvaluate(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderEvaluate(body);
    }

    @Override
    public Observable<BaseResponse> privateLawyersEvaluateAdd(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .privateLawyersEvaluateAdd(body);
    }
}
