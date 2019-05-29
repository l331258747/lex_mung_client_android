package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.RecommendLawyerContract;
import okhttp3.RequestBody;


@ActivityScope
public class RecommendLawyerModel extends BaseModel implements RecommendLawyerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RecommendLawyerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<HelpStepLawyerEntity>> assistantRecommendLawyersOther(int regionId,
                                                                                         int solutionTypeId,
                                                                                         int amountId,
                                                                                         int requireTypeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("regionId",regionId);
        map.put("solutionTypeId",solutionTypeId);
        map.put("requireTypeId",requireTypeId);
        map.put("amountId",amountId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .assistantRecommendLawyersOther(body);
    }

    @Override
    public Observable<BaseResponse<GeneralEntity>> releaseRequirement2(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .releaseRequirement2(body);
    }
}
