package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;

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
import com.lex_mung.client_android.mvp.contract.HomePagerContract;
import com.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.BannerEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lex_mung.client_android.app.DataHelperTags.HOME_PAGE_BANNER;
import static com.lex_mung.client_android.app.DataHelperTags.HOME_PAGE_SOLUTION_TYPE;

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

    private List<BannerEntity.ListBean> bannerList = new ArrayList<>();

    @Inject
    public HomePagerPresenter(HomePagerContract.Model model, HomePagerContract.View rootView) {
        super(model, rootView);
    }

    public List<BannerEntity.ListBean> getBannerList() {
        return bannerList;
    }

    public void getBanner() {
        mModel.getBanner()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
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
                            String json = new Gson().toJson(bannerList);
                            DataHelper.setStringSF(mApplication, HOME_PAGE_BANNER, json);
                            getSolutionType();
                        }
                    }
                });
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
                            String json = new Gson().toJson(baseResponse.getData());
                            DataHelper.setStringSF(mApplication, HOME_PAGE_SOLUTION_TYPE, json);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
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
