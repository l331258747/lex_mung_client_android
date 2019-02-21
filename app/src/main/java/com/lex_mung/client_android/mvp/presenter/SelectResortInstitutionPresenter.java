package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.lex_mung.client_android.mvp.contract.SelectResortInstitutionContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.InstitutionEntity;
import com.lex_mung.client_android.mvp.model.entity.RegionEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ActivityScope
public class SelectResortInstitutionPresenter extends BasePresenter<SelectResortInstitutionContract.Model, SelectResortInstitutionContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNum;
    private int totalNum;

    @Inject
    public SelectResortInstitutionPresenter(SelectResortInstitutionContract.Model model, SelectResortInstitutionContract.View rootView) {
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

    public void getCourt(String ketWord, int regionId, int level, boolean isAdd) {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(ketWord)) {
            map.put("ketwords", ketWord);
        }
        map.put("regionId", regionId);
        map.put("pageNum", pageNum);
        String json = new Gson().toJson(map);
        mModel.getCourt(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<InstitutionEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<InstitutionEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (level == 0 || level == 1) {
                                getRegion(regionId, baseResponse.getData(), isAdd);
                            } else {
                                totalNum = baseResponse.getData().getResult().getPages();
                                pageNum = baseResponse.getData().getResult().getPageNum();
                                mRootView.setAdapter(null, baseResponse.getData().getTopList(), baseResponse.getData().getResult().getList(), isAdd);
                            }
                        }
                    }
                });
    }

    public void getProcuratorate(String ketWord, int regionId, int level, boolean isAdd) {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(ketWord)) {
            map.put("ketwords", ketWord);
        }
        map.put("regionId", regionId);
        map.put("pageNum", pageNum);
        String json = new Gson().toJson(map);
        mModel.getProcuratorate(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<InstitutionEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<InstitutionEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (level == 0 || level == 1) {
                                getRegion(regionId, baseResponse.getData(), isAdd);
                            } else {
                                totalNum = baseResponse.getData().getResult().getPages();
                                pageNum = baseResponse.getData().getResult().getPageNum();
                                mRootView.setAdapter(null, baseResponse.getData().getTopList(), baseResponse.getData().getResult().getList(), isAdd);
                            }
                        }
                    }
                });
    }

    private void getRegion(int parentId, InstitutionEntity entity, boolean isAdd) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        String json = new Gson().toJson(map);
        mModel.getRegion(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RegionEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RegionEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setAdapter(baseResponse.getData(), entity.getTopList(), entity.getResult().getList(), isAdd);
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
