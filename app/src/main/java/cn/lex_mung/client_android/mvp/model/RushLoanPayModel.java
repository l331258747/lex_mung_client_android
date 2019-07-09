package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementCreateEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.RushLoanPayContract;
import okhttp3.RequestBody;


@ActivityScope
public class RushLoanPayModel extends BaseModel implements RushLoanPayContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RushLoanPayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<PayEntity>> pay(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .pay(body);
    }

    @Override
    public Observable<BaseResponse<RequirementCreateEntity>> requirementCreate(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .requirementCreate(body);
    }

    @Override
    public Observable<BaseResponse<BalanceEntity>> getUserBalance(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserBalance(id);
    }

    @Override
    public Observable<BaseResponse<OrderStatusEntity>> releaseFastConsult(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .releaseFastConsult(body);
    }

    @Override
    public Observable<BaseResponse<QuickPayEntity>> quickPay(int couponId, double orderAmount) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponId", couponId);
        map.put("orderAmount", orderAmount);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .quickPay(body);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> quickCoupon() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .quickCoupon(body);
    }
}
