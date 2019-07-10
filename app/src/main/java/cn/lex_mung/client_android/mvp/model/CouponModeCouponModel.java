package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity2;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.CouponModeCouponContract;
import okhttp3.RequestBody;


@FragmentScope
public class CouponModeCouponModel extends BaseModel implements CouponModeCouponContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CouponModeCouponModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<ReleaseDemandOrgMoneyEntity2>> getOrgList2(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getOrgList2(body);
    }
}
