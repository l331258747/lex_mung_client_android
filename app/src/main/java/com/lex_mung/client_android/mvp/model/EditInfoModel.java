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

import com.lex_mung.client_android.mvp.contract.EditInfoContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.IndustryEntity;
import com.lex_mung.client_android.mvp.model.entity.UploadImageEntity;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import java.util.List;

@ActivityScope
public class EditInfoModel extends BaseModel implements EditInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EditInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<UploadImageEntity>> uploadImage(RequestBody body, MultipartBody.Part file) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .uploadImage(body, file);
    }

    @Override
    public Observable<BaseResponse<List<IndustryEntity>>> getIndustryList(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getIndustryList(body);
    }

    @Override
    public Observable<BaseResponse> updateUserInfo(RequestBody body) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .updateUserInfo(body);
    }

    @Override
    public Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail() {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserInfoDetail();

    }
}
