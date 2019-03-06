package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.ActivityScope;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.FeedbackContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.FeedbackTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.UploadImageEntity;

import java.util.List;

@ActivityScope
public class FeedbackModel extends BaseModel implements FeedbackContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FeedbackModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<FeedbackTypeEntity>>> getFeedbackType() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getFeedbackType();
    }

    @Override
    public Observable<BaseResponse<UploadImageEntity>> uploadImage(RequestBody body, MultipartBody.Part file) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .uploadImage(body, file);
    }

    @Override
    public Observable<BaseResponse> uploadFeedback(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .uploadFeedback(body);
    }
}
