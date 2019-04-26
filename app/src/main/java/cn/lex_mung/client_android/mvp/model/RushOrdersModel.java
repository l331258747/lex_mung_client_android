package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.RushOrderLawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RushOrderStatusEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.RushOrdersContract;
import okhttp3.RequestBody;


@ActivityScope
public class RushOrdersModel extends BaseModel implements RushOrdersContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RushOrdersModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<RushOrderLawyerEntity>>> requirementGrablawyers(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .requirementGrablawyers(body);
    }

    @Override
    public Observable<BaseResponse<RushOrderStatusEntity>> requirementStatusCheck(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .requirementStatusCheck(body);
    }
}
