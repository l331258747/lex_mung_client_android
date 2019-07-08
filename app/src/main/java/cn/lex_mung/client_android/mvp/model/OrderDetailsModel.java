package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementDetailEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;

@ActivityScope
public class OrderDetailsModel extends BaseModel implements OrderDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderDetailsModel(IRepositoryManager repositoryManager) {
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
    public Observable<BaseResponse<RemainEntity>> getUserPhone(String orderNo) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserPhone(orderNo);
    }

    @Override
    public Observable<BaseResponse<List<RequirementDetailEntity>>> requirementDetail(int requirementId,String orderNo) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .requirementDetail(requirementId,orderNo);
    }
}
