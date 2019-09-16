package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustDetailEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsEntrustContract;
import me.zl.mvp.utils.RxLifecycleUtils;


@ActivityScope
public class OrderDetailsEntrustPresenter extends BasePresenter<OrderDetailsEntrustContract.Model, OrderDetailsEntrustContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OrderDetailsEntrustPresenter(OrderDetailsEntrustContract.Model model, OrderDetailsEntrustContract.View rootView) {
        super(model, rootView);
    }


    public void caseorderDetail(int id) {
        mModel.caseorderDetail(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<EntrustDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<EntrustDetailEntity> baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData() != null && baseResponse.getData().getOrder() != null)
                                mRootView.setEntity(baseResponse.getData().getOrder());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
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
