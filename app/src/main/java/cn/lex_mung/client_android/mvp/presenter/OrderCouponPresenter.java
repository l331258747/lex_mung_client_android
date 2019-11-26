package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderCouponContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
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

import static cn.lex_mung.client_android.app.EventBusTags.ORDER_COUPON.ORDER_COUPON;
import static cn.lex_mung.client_android.app.EventBusTags.ORDER_COUPON.REFRESH_COUPON;


@ActivityScope
public class OrderCouponPresenter extends BasePresenter<OrderCouponContract.Model, OrderCouponContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNum;
    private int totalNum;

    private OrderCouponAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    private int couponId;
    private int type;
    private int productId;

    private double orderAmount;

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Inject
    public OrderCouponPresenter(OrderCouponContract.Model model, OrderCouponContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout,int couponId) {
        this.smartRefreshLayout = smartRefreshLayout;
        this.couponId = couponId;
        initAdapter();
        getList(false);
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setType(int type) {
        this.type = type;
    }

    private void initAdapter() {
        adapter = new OrderCouponAdapter(mRootView.getActivity(),type);
        adapter.setCouponId(couponId);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if(mRootView.getType() == 1) return;
            if (isFastClick()) return;
            OrderCouponEntity entity = adapter.getItem(position);
            if (entity == null) return;
            if(entity.getCouponStatus() == 2) return;//不可用

            AppUtils.post(ORDER_COUPON, REFRESH_COUPON, entity);
            mRootView.killMyself();
        });

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNum < totalNum) {
                    pageNum = pageNum + 1;
                    getList(true);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getList(false);
            }
        });
        mRootView.initRecyclerView(adapter);
    }


    private void getList(boolean isAdd){
        if(type == 4){
            getCouponsList2(isAdd,128);
        }else if(type == 3){
            getBuyEquityCouponsList(isAdd);
        }else if(type == 2){
            getCouponsList2(isAdd);//热门需求
        }else if(type == 1){
            getMeCouponsList(isAdd);//我的优惠券
        }else{
            getCouponsList(isAdd);//快速咨询
        }
    }

    private void getBuyEquityCouponsList(boolean isAdd){
        mModel.legalAdviserServerCoupon(pageNum, orderAmount)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<OrderCouponEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<OrderCouponEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                mRootView.setEmptyView(adapter);
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getList());
                                if (totalNum == pageNum) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            }
                        }
                    }
                });
    }

    private void getCouponsList(boolean isAdd) {
        mModel.quickCoupon(pageNum, orderAmount)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<OrderCouponEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<OrderCouponEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                mRootView.setEmptyView(adapter);
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getList());
                                if (totalNum == pageNum) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            }
                        }
                    }
                });
    }

    private void getCouponsList2(boolean isAdd) {
        this.getCouponsList2(isAdd,productId);
    }

    private void getCouponsList2(boolean isAdd,int productId) {
        mModel.optimalRequireList(pageNum, orderAmount,productId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<OrderCouponEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<OrderCouponEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                mRootView.setEmptyView(adapter);
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getList());
                                if (totalNum == pageNum) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            }
                        }
                    }
                });
    }

    private void getMeCouponsList(boolean isAdd){
        mModel.requireCoupon(pageNum)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<OrderCouponEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<OrderCouponEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                mRootView.setEmptyView(adapter);
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getList());
                                if (totalNum == pageNum) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
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
