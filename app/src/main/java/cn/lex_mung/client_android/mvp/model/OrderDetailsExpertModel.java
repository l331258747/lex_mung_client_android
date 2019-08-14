package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsExpertContract;
import okhttp3.RequestBody;


@ActivityScope
public class OrderDetailsExpertModel extends BaseModel implements OrderDetailsExpertContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderDetailsExpertModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderDetailsEntity>>> getOrderDetail(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getOrderDetail(body);
    }

    @Override
    public Observable<BaseResponse> expertCancel(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .expertCancel(body);
    }

    @Override
    public Observable<BaseResponse> expertFinish(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .expertFinish(body);
    }
}
