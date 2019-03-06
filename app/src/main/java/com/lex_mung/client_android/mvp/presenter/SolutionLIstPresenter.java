package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.mvp.contract.SolutionLIstContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.SolutionListEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FragmentScope
public class SolutionLIstPresenter extends BasePresenter<SolutionLIstContract.Model, SolutionLIstContract.View> {
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

    private int id;

    private boolean isFlag = true;
    private List<SolutionListEntity.ListBean> listBeans = new ArrayList<>();

    @Inject
    public SolutionLIstPresenter(SolutionLIstContract.Model model, SolutionLIstContract.View rootView) {
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

    public void setId(int id) {
        this.id = id;
    }

    public void getSolutionList(boolean isAdd) {
        if (isFlag) {
            try {
                String json = DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_LIST + "_" + id);
                if (!TextUtils.isEmpty(json)) {
                    List<SolutionListEntity.ListBean> listBeans = new Gson().fromJson(json, new TypeToken<List<SolutionListEntity.ListBean>>() {
                    }.getType());
                    if (listBeans != null) {
                        mRootView.setAdapter(listBeans, false);
                    }
                }
            } catch (Exception ignored) {
            }
            isFlag = false;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", id);
        map.put("pageNum", pageNum);
        map.put("pageSize", 10);
        mModel.getSolutionList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<SolutionListEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<SolutionListEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();
                            mRootView.setAdapter(baseResponse.getData().getList(), isAdd);
                            if (pageNum == 1) {
                                listBeans.addAll(baseResponse.getData().getList());
                                DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_LIST + "_" + id, new Gson().toJson(listBeans));
                            }
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
        this.listBeans = null;
    }
}
