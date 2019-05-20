package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.FreeConsultDetail1ListContract;


@ActivityScope
public class FreeConsultDetail1ListModel extends BaseModel implements FreeConsultDetail1ListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FreeConsultDetail1ListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<FreeConsultReplyEntity>> replyDetail(int consultationId, int lawyerId, int pageNum) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .replyDetail(consultationId, lawyerId,pageNum,10);
    }

    @Override
    public Observable<BaseResponse> deleteReply(int consultationReplyId) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .deleteReply(consultationReplyId);
    }
}
