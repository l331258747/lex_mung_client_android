package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderCouponContract;
import okhttp3.RequestBody;


@ActivityScope
public class OrderCouponModel extends BaseModel implements OrderCouponContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderCouponModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> quickCoupon(int pageNum, double orderAmount) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", 10);
        map.put("orderAmount", orderAmount);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .quickCoupon(body);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> requireCoupon(int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", 10);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .requireCoupon(body);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> optimalRequireList(int pageNum, double orderAmount,int productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", 10);
        map.put("orderAmount", orderAmount);
        map.put("productId", productId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .optimalRequireList(body);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> legalAdviserServerCoupon(int pageNum,float priceTotal) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", 10);
        map.put("priceTotal", priceTotal);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserServerCoupon(body);
    }
}
