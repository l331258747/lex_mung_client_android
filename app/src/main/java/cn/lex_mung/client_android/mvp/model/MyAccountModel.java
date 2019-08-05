package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.MyAccountContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import okhttp3.RequestBody;

@ActivityScope
public class MyAccountModel extends BaseModel implements MyAccountContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyAccountModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BalanceEntity>> getUserBalance(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserBalance(id);
    }

    @Override
    public Observable<BaseResponse<PayEntity>> pay(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .pay(body);
    }

    @Override
    public Observable<BaseResponse<List<RechargeEntity>>> rechargeList(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .rechargeList(body);
    }

    @Override
    public Observable<BaseResponse<List<RechargeCouponEntity>>> rechargeCouponList(int voucherPackId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .rechargeCouponList(voucherPackId);
    }

    @Override
    public Observable<BaseResponse<Boolean>> withdrawVerify() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .withdrawVerify();
    }
}
