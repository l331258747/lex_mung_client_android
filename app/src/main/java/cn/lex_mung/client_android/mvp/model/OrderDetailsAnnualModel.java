package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.corporate.CorporateDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderComplaintEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsAnnualContract;
import okhttp3.RequestBody;


@ActivityScope
public class OrderDetailsAnnualModel extends BaseModel implements OrderDetailsAnnualContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderDetailsAnnualModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<CorporateDetailEntity>> corporateDetail(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeId",9);
        map.put("id",id);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));

        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .corporateDetail(body);
    }

    @Override
    public Observable<BaseResponse<RemainEntity>> corporateUserphone(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .corporateUserphone(id);
    }

    @Override
    public Observable<BaseResponse<List<LegalAdviserOrderComplaintEntity>>> legalAdviserOrderComplaint(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderComplaint(body);
    }

    @Override
    public Observable<BaseResponse> corporateUncomplete(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .corporateUncomplete(body);
    }

    @Override
    public Observable<BaseResponse> corporateComplete(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .corporateComplete(body);
    }


    @Override
    public Observable<BaseResponse> corporateCancel(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .corporateCancel(body);
    }
}
