package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocUploadEntity;
import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.TabOrderContractContract;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@FragmentScope
public class TabOrderContractModel extends BaseModel implements TabOrderContractContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TabOrderContractModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<DocUploadEntity>> docUpload(RequestBody order_no, MultipartBody.Part file) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .docUpload(order_no, file);
    }

    @Override
    public Observable<BaseResponse<DocGetEntity>> docGet(String order_no,int pageNum) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .docGet(order_no,pageNum,10);
    }
}
