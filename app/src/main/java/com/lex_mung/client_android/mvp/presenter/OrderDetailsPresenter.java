package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

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
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.lex_mung.client_android.mvp.contract.OrderDetailsContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import com.lex_mung.client_android.mvp.model.entity.RemainEntity;

import java.util.HashMap;
import java.util.Map;

@ActivityScope
public class OrderDetailsPresenter extends BasePresenter<OrderDetailsContract.Model, OrderDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int type;
    private int id;
    private String orderNo;
    private OrderDetailsEntity.ListBean bean;

    @Inject
    public OrderDetailsPresenter(OrderDetailsContract.Model model, OrderDetailsContract.View rootView) {
        super(model, rootView);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void getUserPhone() {
        mModel.getUserPhone(orderNo)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RemainEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RemainEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.call(baseResponse.getData().getPhone());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void getOrderDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", type);
        map.put("id", id);
        mModel.getOrderDetail(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OrderDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OrderDetailsEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            bean = baseResponse.getData().getList().get(0);
                            orderNo = bean.getOrderNo();
                            if (!TextUtils.isEmpty(bean.getOrderNo())) {
                                mRootView.setOrderNo(bean.getOrderNo());
                            }
                            if (!TextUtils.isEmpty(bean.getCreateDate())) {
                                mRootView.setOrderTime(bean.getCreateDate());
                            }
                            if (!TextUtils.isEmpty(bean.getOrderType())) {
                                mRootView.setOrderName(bean.getOrderType());
                            }
                            if (!TextUtils.isEmpty(bean.getOrderType())) {
                                mRootView.setOrderType(bean.getOrderType());
                            }
                            mRootView.setOrderAmount(AppUtils.formatAmount(mApplication, bean.getBuyerPayAmount()) + "元");
                            if (!TextUtils.isEmpty(bean.getStatusValue())) {
                                mRootView.setOrderStatus(bean.getStatusValue());
                            }
                            if (bean.getTypeId() == 4) {//快速抢单
                                if (bean.getOrderStatus() == 5) {//已接单
                                    if (!TextUtils.isEmpty(bean.getLmemberName())) {
                                        mRootView.setOrderCustomer(bean.getLmemberName());
                                    }
                                    mRootView.setOrderRemain(bean.getRemainingTime() * 1000);
                                    mRootView.showLayout(1);
                                } else if (bean.getOrderStatus() > 5) {//已结束
                                    if (!TextUtils.isEmpty(bean.getLmemberName())) {
                                        mRootView.setOrderCustomer(bean.getLmemberName());
                                    }
                                    if (!TextUtils.isEmpty(bean.getBeginTime())
                                            && !bean.getBeginTime().contains("1970")) {
                                        mRootView.setOrderStartTime(bean.getBeginTime() + "  开始");
                                    }
                                    if (!TextUtils.isEmpty(bean.getEndTime())
                                            && !bean.getEndTime().contains("1970")) {
                                        mRootView.setOrderEndTime(bean.getEndTime() + "  结束");
                                    }
                                    mRootView.setOrderTotal(bean.getTalkTime());
                                    mRootView.showLayout(2);
                                }
                            } else if (bean.getTypeId() == 3) {//专家咨询
                                if (!TextUtils.isEmpty(bean.getLmemberName())) {
                                    mRootView.setOrderCustomer(bean.getLmemberName());
                                }
                                if (!TextUtils.isEmpty(bean.getBeginTime())
                                        && !bean.getBeginTime().contains("1970")) {
                                    mRootView.setOrderStartTime(bean.getBeginTime() + "  开始");
                                }
                                if (!TextUtils.isEmpty(bean.getEndTime())
                                        && !bean.getEndTime().contains("1970")) {
                                    mRootView.setOrderEndTime(bean.getEndTime() + "  结束");
                                }
                                mRootView.setOrderTotal(bean.getTalkTime());
                                mRootView.showLayout(3);
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
    }
}
