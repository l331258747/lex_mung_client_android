package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.AmountBalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrgAmountEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.corporate.CorporateBuyEntity;
import cn.lex_mung.client_android.mvp.model.entity.corporate.CorporatePayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementCreateEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderConfirmEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.PrivategroupBuyEntity;
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
    public Observable<BaseResponse<LegalAdviserOrderConfirmEntity>> legalAdviserOrderConfirm(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderConfirm(body);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> legalAdviserServerCoupon(float priceTotal) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        map.put("priceTotal", priceTotal);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserServerCoupon(body);
    }


    @Override
    public Observable<BaseResponse<LegalAdviserOrderPayEntity>> legalAdviserOrderPay(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderPay(body);
    }

    @Override
    public Observable<BaseResponse<QuickPayEntity>> legalAdviserOrderAmount(int couponId, double priceTotal) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponId", couponId);
        map.put("priceTotal", priceTotal);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderAmount(body);
    }

    @Override
    public Observable<BaseResponse<PrivategroupBuyEntity>> privategroupBuy(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .privategroupBuy(body);
    }

    @Override
    public Observable<BaseResponse<CorporatePayEntity>> getCorporatePay(int corporateServerId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getCorporatePay(corporateServerId);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> corporateCoupon(float priceTotal) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        map.put("priceTotal", priceTotal);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .corporateCoupon(body);
    }

    @Override
    public Observable<BaseResponse<QuickPayEntity>> corporateAmount(int couponId, double orderAmount) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponId", couponId);
        map.put("priceTotal", orderAmount);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .corporateAmount(body);
    }

    @Override
    public Observable<BaseResponse<CorporateBuyEntity>> corporateBuy(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .corporateBuy(body);
    }

    @Override
    public Observable<BaseResponse<RequirementCreateEntity>> requirementCreate(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .requirementCreate(body);
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
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> quickCoupon(float orderAmount) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        map.put("orderAmount", orderAmount);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .quickCoupon(body);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> optimalRequireList(float orderAmount,int productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        map.put("orderAmount", orderAmount);
        map.put("productId", productId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .optimalRequireList(body);
    }

    @Override
    public Observable<BaseResponse<QuickPayEntity>> optimalRequire(int couponId, double orderAmount,int productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponId", couponId);
        map.put("orderAmount", orderAmount);
        map.put("productId", productId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .optimalRequire(body);
    }

    @Override
    public Observable<BaseResponse<AmountBalanceEntity>> amountBalance(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .amountBalance(body);
    }

    @Override
    public Observable<BaseResponse<Integer>> couponCount(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .couponCount(body);
    }
}
