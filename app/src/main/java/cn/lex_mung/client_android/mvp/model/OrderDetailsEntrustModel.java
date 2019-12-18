package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustListEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsEntrustContract;
import okhttp3.RequestBody;


@ActivityScope
public class OrderDetailsEntrustModel extends BaseModel implements OrderDetailsEntrustContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderDetailsEntrustModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<EntrustListEntity>> caseorderDetail(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .caseorderDetail(id);
    }

    @Override
    public Observable<BaseResponse> casesourceUpdate(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .casesourceUpdate(body);
    }

    @Override
    public Observable<BaseResponse<RemainEntity>> casesourceUserphone(int id, int lawyerId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .casesourceUserphone(id,lawyerId);
    }
}
