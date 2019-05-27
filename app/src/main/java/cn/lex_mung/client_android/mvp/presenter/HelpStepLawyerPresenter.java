package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
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

import cn.lex_mung.client_android.mvp.contract.HelpStepLawyerContract;
import me.zl.mvp.utils.RxLifecycleUtils;


@ActivityScope
public class HelpStepLawyerPresenter extends BasePresenter<HelpStepLawyerContract.Model, HelpStepLawyerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HelpStepLawyerPresenter(HelpStepLawyerContract.Model model, HelpStepLawyerContract.View rootView) {
        super(model, rootView);
    }

    public void getData(int regionId,
                        int solutionTypeId,
                        int amountId,
                        int requireTypeId) {
        mModel.assistantRecommendLawyers(regionId, solutionTypeId, amountId, requireTypeId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HelpStepLawyerEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HelpStepLawyerEntity> baseResponse) {
                        mRootView.setData(baseResponse.getData());
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
