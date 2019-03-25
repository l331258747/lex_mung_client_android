package cn.lex_mung.client_android.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import io.reactivex.Observable;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.mvp.BaseModel;

import me.zl.mvp.di.scope.FragmentScope;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderMessageContract;
import cn.lex_mung.client_android.mvp.model.api.CommonService;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.MessageEntity;


@FragmentScope
public class OrderMessageModel extends BaseModel implements OrderMessageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderMessageModel(IRepositoryManager repositoryManager) {
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
    public Observable<BaseResponse<MessageEntity>> getOrderMessageList(int pageNum) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getOrderMessageList(pageNum);
    }

    @Override
    public Observable<BaseResponse> setRead(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .setRead(id);
    }
}
