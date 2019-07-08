package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.FreeTextBizinfoEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.FreeConsultMainContract;
import okhttp3.RequestBody;


@ActivityScope
public class FreeConsultMainModel extends BaseModel implements FreeConsultMainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FreeConsultMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<FreeTextBizinfoEntity>> freeTextBizinfo() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .freeTextBizinfo();
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<CommonFreeTextEntity>>> commonFreeText(RequestBody body, boolean isLogin) {
        if(isLogin){
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .lawyerFreeText(body);
        }
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .commonFreeText(body);
    }
}
