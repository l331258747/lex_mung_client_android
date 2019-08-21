package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.WebContract;
import okhttp3.RequestBody;


@ActivityScope
public class WebModel extends BaseModel implements WebContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WebModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserInfoDetail();
    }

    @Override
    public Observable<BaseResponse> clientReceive(int voucherPackId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .clientReceive(voucherPackId);
    }

    @Override
    public Observable<BaseResponse> clientCouponGain(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .clientCouponGain(body);
    }
}
