package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;
import okhttp3.RequestBody;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.FreeConsultDetailsListContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;

@ActivityScope
public class FreeConsultDetailsListModel extends BaseModel implements FreeConsultDetailsListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FreeConsultDetailsListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<FreeConsultReplyListEntity>>> getFreeConsultReplyDetailList(int consultationId, int lawyerId, int pageNum) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getFreeConsultReplyList(consultationId, lawyerId, pageNum);
    }

    @Override
    public Observable<BaseResponse<FreeConsultReplyListEntity>> lawyerReply(int consultationId, RequestBody body) {
        return  mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .userReplyFreeConsult(consultationId, body);
    }

    @Override
    public Observable<BaseResponse> deleteReply(int consultationReplyId) {
        return  mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .deleteReply(consultationReplyId);
    }
}
