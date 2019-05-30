package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStepLawyerContract;
import okhttp3.RequestBody;


@ActivityScope
public class HelpStepLawyerModel extends BaseModel implements HelpStepLawyerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HelpStepLawyerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<HelpStepLawyerEntity>> assistantRecommendLawyers(int regionId,
                                                                                    int solutionTypeId,
                                                                                    int amountId,
                                                                                    int requireTypeId,
                                                                                    int memberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("regionId",regionId);
        map.put("solutionTypeId",solutionTypeId);
        map.put("requireTypeId",requireTypeId);
        map.put("amountId",amountId);
        map.put("memberId",memberId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .assistantRecommendLawyers(body);
    }

    @Override
    public Observable<BaseResponse<ExpertPriceEntity>> expertPrice(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .expertPrice(id);
    }

    @Override
    public Observable<BaseResponse<AgreementEntity>> sendCall(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .sendCall(id);
    }
}
