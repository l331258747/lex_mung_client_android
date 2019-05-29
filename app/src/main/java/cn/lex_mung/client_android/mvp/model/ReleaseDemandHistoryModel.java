package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HirstoryDemandEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.ReleaseDemandHistoryContract;
import okhttp3.RequestBody;


@ActivityScope
public class ReleaseDemandHistoryModel extends BaseModel implements ReleaseDemandHistoryContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ReleaseDemandHistoryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<GeneralEntity>> releaseRequirement2(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .releaseRequirement2(body);
    }

    @Override
    public Observable<BaseResponse<HirstoryDemandEntity>> clientRequirementOne(int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",10);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));

        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .clientRequirementOne(body);
    }
}
