package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.TabOrderInfoContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementDetailEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.RxLifecycleUtils;


@FragmentScope
public class TabOrderInfoPresenter extends BasePresenter<TabOrderInfoContract.Model, TabOrderInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TabOrderInfoPresenter(TabOrderInfoContract.Model model, TabOrderInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getRequirementDetail(int anInt) {
        mModel.requirementDetail(anInt)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RequirementDetailEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RequirementDetailEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if(baseResponse.getData() == null || baseResponse.getData().size() < 1)
                                return;
                            mRootView.refreshOrderInfo(baseResponse.getData().get(0));
                        }
                    }
                });
    }
}
