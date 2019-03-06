package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.mvp.contract.TradingListContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.TradingListEntity;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import java.util.HashMap;
import java.util.Map;

@ActivityScope
public class TradingListPresenter extends BasePresenter<TradingListContract.Model, TradingListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNum = 1;
    private int totalNum;

    @Inject
    public TradingListPresenter(TradingListContract.Model model, TradingListContract.View rootView) {
        super(model, rootView);
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void getTradingList(boolean isAdd) {
        UserInfoDetailsEntity entity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", entity.getMemberId());
        map.put("pageNum", pageNum);
        map.put("pageSize", 10);
        mModel.getTradingList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TradingListEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<TradingListEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();
                            mRootView.setAdapter(baseResponse.getData().getList(), isAdd);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
