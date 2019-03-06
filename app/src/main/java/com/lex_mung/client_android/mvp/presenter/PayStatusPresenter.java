package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.mvp.contract.PayStatusContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import java.util.HashMap;
import java.util.Map;


@ActivityScope
public class PayStatusPresenter extends BasePresenter<PayStatusContract.Model, PayStatusContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PayStatusPresenter(PayStatusContract.Model model, PayStatusContract.View rootView) {
        super(model, rootView);
    }

    public void checkOrderStatus(String orderNo) {
        UserInfoDetailsEntity entity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        Map<String, Object> map = new HashMap<>();
        map.put("orderno", orderNo);
        map.put("memberid", entity.getMemberId());
        long time = System.currentTimeMillis();
        map.put("timestamp", time);
        String sign = "memberid=" + entity.getMemberId() + "&orderno=" + orderNo + "&timestamp=" + time;
        map.put("sign", AppUtils.encodeToMD5(sign));
        mModel.checkOrderStatus(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading("正在查询支付状态..."))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OrderStatusEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OrderStatusEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            switch (baseResponse.getData().getStatus()) {
                                case 1:
                                    mRootView.showSuccessLayout();
                                    break;
                                default:
                                    if (DataHelper.getIntergerSF(mApplication, "PAY_TYPE") == 2) {
                                        mRootView.showFailLayout(AppUtils.getString(mApplication, R.string.text_pay_fail_1));
                                    } else {
                                        mRootView.showFailLayout(AppUtils.getString(mApplication, R.string.text_pay_fail));
                                    }
                                    break;
                            }
                        } else {
                            mRootView.killMyself();
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
