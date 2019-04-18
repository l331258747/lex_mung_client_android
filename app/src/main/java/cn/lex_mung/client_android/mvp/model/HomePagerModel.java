package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.entity.home.RequirementTypeV3Entity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HomePagerContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;

import java.util.List;

@FragmentScope
public class HomePagerModel extends BaseModel implements HomePagerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomePagerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BannerEntity>> getBanner() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getBanner();
    }

    @Override
    public Observable<BaseResponse<List<SolutionTypeEntity>>> getSolutionType(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSolutionType(body);
    }

    @Override
    public Observable<BaseResponse<RequirementTypeV3Entity>> getHomepageRequirementType() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getHomepageRequirementType();
    }

    @Override
    public Observable<BaseResponse<UnreadMessageCountEntity>> getUnreadCount() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUnreadCount();
    }
}
