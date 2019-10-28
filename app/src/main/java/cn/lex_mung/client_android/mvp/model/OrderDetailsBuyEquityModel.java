package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderComplaintEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderDetailEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsBuyEquityContract;
import okhttp3.RequestBody;


@ActivityScope
public class OrderDetailsBuyEquityModel extends BaseModel implements OrderDetailsBuyEquityContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderDetailsBuyEquityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<LegalAdviserOrderDetailEntity>> legalAdviserOrderDetail(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderDetail(body);
    }

    @Override
    public Observable<BaseResponse> legalAdviserOrderCancel(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderCancel(body);
    }

    @Override
    public Observable<BaseResponse> legalAdviserOrderComplete(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderComplete(body);
    }

    @Override
    public Observable<BaseResponse> legalAdviserOrderUnComplete(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderUnComplete(body);
    }

    @Override
    public Observable<BaseResponse<List<LegalAdviserOrderComplaintEntity>>> legalAdviserOrderComplaint(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderComplaint(body);
    }
}
