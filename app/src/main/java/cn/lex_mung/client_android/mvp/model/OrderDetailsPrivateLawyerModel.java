package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderComplaintEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.OrderPrivateLawyersDetailEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsPrivateLawyerContract;
import okhttp3.RequestBody;


@ActivityScope
public class OrderDetailsPrivateLawyerModel extends BaseModel implements OrderDetailsPrivateLawyerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderDetailsPrivateLawyerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<OrderPrivateLawyersDetailEntity>> orderPrivateLawyersDetail(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .orderPrivateLawyersDetail(id);
    }

    @Override
    public Observable<BaseResponse<RemainEntity>> privateLawyersUserphone(String orderNo) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .privateLawyersUserphone(orderNo);
    }

    @Override
    public Observable<BaseResponse<List<LegalAdviserOrderComplaintEntity>>> legalAdviserOrderComplaint(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderComplaint(body);
    }

    @Override
    public Observable<BaseResponse> privateLawyersOrderCancel(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .privateLawyersOrderCancel(body);
    }

    @Override
    public Observable<BaseResponse> privateLawyersOrderUpdate(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .privateLawyersOrderUpdate(body);
    }
}
