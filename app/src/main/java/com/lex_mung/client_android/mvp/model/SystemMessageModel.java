package com.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.SystemMessageContract;
import com.lex_mung.client_android.mvp.model.api.CommonService;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.MessageEntity;

@FragmentScope
public class SystemMessageModel extends BaseModel implements SystemMessageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SystemMessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> allSetRead(int type) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .allSetRead(type);
    }

    @Override
    public Observable<BaseResponse<MessageEntity>> getSystemMessageList(int pageNum) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSystemMessageList(pageNum);
    }

    @Override
    public Observable<BaseResponse> setRead(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .setRead(id);
    }
}
