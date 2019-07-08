package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.FreeConsultDetail1Contract;
import okhttp3.RequestBody;


@ActivityScope
public class FreeConsultDetail1Model extends BaseModel implements FreeConsultDetail1Contract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FreeConsultDetail1Model(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<FreeConsultEntity>> commonFreeText(int id,boolean isLogin) {
        if(isLogin){
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .lawyerFreeText(id);
        }
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .commonFreeText(id);
    }

    @Override
    public Observable<BaseResponse<BaseListEntity<FreeConsultReplyListEntity>>> lawyerFreeText(int consultationId, int pageNum, int pageSize) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .lawyerFreeText(consultationId,pageNum,pageSize);
    }

    @Override
    public Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLawyerList(pageNum,body);
    }
}
