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
import com.lex_mung.client_android.mvp.contract.HomePagerContract;
import com.lex_mung.client_android.mvp.model.entity.RequirementTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.BannerEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FragmentScope
public class HomePagerPresenter extends BasePresenter<HomePagerContract.Model, HomePagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private boolean isLogin = false;

    private List<BannerEntity.ListBean> bannerList = new ArrayList<>();
    private boolean isFlag = true;

    @Inject
    public HomePagerPresenter(HomePagerContract.Model model, HomePagerContract.View rootView) {
        super(model, rootView);
    }

    public void onResume() {
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public List<BannerEntity.ListBean> getBannerList() {
        return bannerList;
    }

    public void getBanner() {
        if (isFlag) {
            try {
                String bannerJson = DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_BANNER);
                if (!TextUtils.isEmpty(bannerJson)) {
                    List<BannerEntity.ListBean> listBeans = new Gson().fromJson(bannerJson, new TypeToken<List<BannerEntity.ListBean>>() {
                    }.getType());
                    if (listBeans != null) {
                        bannerList.clear();
                        bannerList.addAll(listBeans);
                        mRootView.setBannerAdapter(bannerList);
                    }
                }
                String solutionTypeJson = DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_TYPE);
                if (!TextUtils.isEmpty(solutionTypeJson)) {
                    List<SolutionTypeEntity> listBeans = new Gson().fromJson(solutionTypeJson, new TypeToken<List<SolutionTypeEntity>>() {
                    }.getType());
                    if (listBeans != null) {
                        mRootView.setSolutionType(listBeans);
                    }
                }
                String requirementTypeJson = DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_REQUIREMENT_TYPE);
                if (!TextUtils.isEmpty(requirementTypeJson)) {
                    List<RequirementTypeEntity> listBeans = new Gson().fromJson(requirementTypeJson, new TypeToken<List<RequirementTypeEntity>>() {
                    }.getType());
                    if (listBeans != null) {
                        mRootView.setRequirementTypeAdapter(listBeans);
                    }
                }
            } catch (Exception ignored) {
            }
            isFlag = false;
        }
        mModel.getBanner()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BannerEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BannerEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            bannerList.clear();
                            bannerList.addAll(baseResponse.getData().getList());
                            mRootView.setBannerAdapter(bannerList);
                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_BANNER, new Gson().toJson(bannerList));
                            getSolutionType();
                        }
                    }
                });
        getHomepageRequirementType();
    }

    private void getSolutionType() {
        Map<String, Object> map = new HashMap<>();
        mModel.getSolutionType(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<SolutionTypeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<SolutionTypeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setSolutionType(baseResponse.getData());
                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_TYPE, new Gson().toJson(baseResponse.getData()));
                        }
                    }
                });
    }

    private void getHomepageRequirementType() {
        mModel.getHomepageRequirementType()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RequirementTypeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RequirementTypeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setRequirementTypeAdapter(baseResponse.getData());
                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_REQUIREMENT_TYPE, new Gson().toJson(baseResponse.getData()));
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
