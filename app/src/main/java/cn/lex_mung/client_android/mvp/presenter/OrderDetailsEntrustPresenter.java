package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsEntrustContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustListEntity;
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
import okhttp3.RequestBody;


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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<EntrustListEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<EntrustListEntity> baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData() != null && baseResponse.getData() != null)
                                mRootView.setEntity(baseResponse.getData());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void casesourceUpdate(int id,int lawyerMemberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("lawyerMemberId", lawyerMemberId);
        mModel.casesourceUpdate(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
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
                            caseorderDetail(id);
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void casesourceUserphone(int id,int lawyerId){
        mModel.casesourceUserphone(id,lawyerId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RemainEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RemainEntity> baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            mRootView.callUserPhone(baseResponse.getData().getPhone());
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
