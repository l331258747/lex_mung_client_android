package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocUploadEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderContractContract;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@ActivityScope
public class OrderContractModel extends BaseModel implements OrderContractContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderContractModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<DocUploadEntity>> docUpload(int type, RequestBody order_no, MultipartBody.Part file) {
        if (type == 1) {
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .legalAdviserOrderDocUpload(order_no, file);
        } else {
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .docUpload(order_no, file);
        }
    }

    @Override
    public Observable<BaseResponse<DocGetEntity>> docGet(int type, String order_no, int pageNum) {
        if (type == 1) {
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .legalAdviserOrderDocGet(order_no, pageNum, 10);
        } else {
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .docGet(order_no, pageNum, 10);
        }
    }

    @Override
    public Observable<BaseResponse> docRead(int type, String repositoryId) {
        if (type == 1) {
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .legalAdviserOrderDocRead(repositoryId);
        } else {
            return mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .docRead(repositoryId);
        }

    }

    @Override
    public Observable<BaseResponse<RemainEntity>> legalAdviserOrderUserPhone(String orderNo) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .legalAdviserOrderUserPhone(orderNo);
    }
}
