package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HomeSolutionContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonSolutionEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.RxLifecycleUtils;


@ActivityScope
public class HomeSolutionPresenter extends BasePresenter<HomeSolutionContract.Model, HomeSolutionContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomeSolutionPresenter(HomeSolutionContract.Model model, HomeSolutionContract.View rootView) {
        super(model, rootView);
    }

    public void getDatas(){
        mModel.commonSolutionList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<CommonSolutionEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<CommonSolutionEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.initAdapter(baseResponse.getData().getList());
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
