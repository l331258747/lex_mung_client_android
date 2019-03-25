package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.FreeConsultDetailContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyEntity;


@ActivityScope
public class FreeConsultDetailModel extends BaseModel implements FreeConsultDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FreeConsultDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<FreeConsultEntity>> getFreeConsultDetail(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getFreeConsultDetail(id);
    }

    @Override
    public Observable<BaseResponse<FreeConsultReplyEntity>> getFreeConsultReplyList(int consultationId, int pageNum) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getConsultReplyList(consultationId,pageNum);
    }
}
