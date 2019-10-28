package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.BuyEquityEvaluateContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_BUY_EQUITY_DETAIL;


@ActivityScope
public class BuyEquityEvaluatePresenter extends BasePresenter<BuyEquityEvaluateContract.Model, BuyEquityEvaluateContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BuyEquityEvaluatePresenter(BuyEquityEvaluateContract.Model model, BuyEquityEvaluateContract.View rootView) {
        super(model, rootView);
    }

    public void legalAdviserOrderEvaluate(String orderId, int generalEvaluation, int professionalKnowledge, int responseSpeed, int serviceAttitude, String evaluationContent) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("generalEvaluation", generalEvaluation);
        map.put("professionalKnowledge", professionalKnowledge);
        map.put("responseSpeed", responseSpeed);
        map.put("serviceAttitude", serviceAttitude);
        map.put("evaluationContent", evaluationContent);
        mModel.legalAdviserOrderEvaluate(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            mRootView.killMyself();
                            AppUtils.post(REFRESH, REFRESH_BUY_EQUITY_DETAIL);
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
