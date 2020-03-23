package cn.lex_mung.client_android.mvp.presenter;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import javax.inject.Inject;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.OrderCouponContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.ui.activity.MainActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter2;
import cn.lex_mung.client_android.utils.GsonUtil;
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
import me.zl.mvp.utils.DataHelper;
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

    private OrderCouponAdapter2 adapter;
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

    public void onCreate(SmartRefreshLayout smartRefreshLayout, int couponId) {
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
        adapter = new OrderCouponAdapter2(mRootView.getActivity(), type);
        adapter.setCouponId(couponId);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            OrderCouponEntity entity = adapter.getItem(position);
            if (entity == null) return;
            if (entity.getCouponStatus() == 2) return;//不可用

            if (mRootView.getType() == 1) {
                Bundle bundle = new Bundle();
                switch (entity.getPageType()) {//pageType（1首页、2专项起草合同、3律师搜索页、4快速咨询、5在线法律顾问、6私人律师团，7年度企业会员）
                    case 1:
                        mRootView.killMyself();
                        AppManager.getAppManager().killAllNotClass(MainActivity.class);
                        ((MainActivity) AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(0);
                        break;
                    case 2:
                        String str2 = DataHelper.getStringSF(mApplication, DataHelperTags.HTSCQC_URL);
                        HomeChildEntity entity2 = GsonUtil.convertString2Object(str2, HomeChildEntity.class);
                        if (!TextUtils.isEmpty(str2) && entity2 != null) {
                            bundle.clear();
                            bundle.putString(BundleTags.URL, entity2.getJumpurl());
                            bundle.putString(BundleTags.TITLE, entity2.getTitle());
                            mRootView.launchActivity(new Intent(mApplication, WebActivity.class), bundle);
                        }
                        break;
                    case 3:
                        mRootView.killMyself();
                        AppManager.getAppManager().killAllNotClass(MainActivity.class);
                        ((MainActivity) AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(2);
                        break;
                    case 4:
                        String str4 = DataHelper.getStringSF(mApplication, DataHelperTags.QUICK_URL);
                        HomeChildEntity entity4 = GsonUtil.convertString2Object(str4, HomeChildEntity.class);
                        if (!TextUtils.isEmpty(str4) && entity != null) {
                            bundle.clear();
                            bundle.putString(BundleTags.URL, entity4.getJumpurl());
                            bundle.putString(BundleTags.TITLE, entity4.getTitle());
                            if (entity4.getShowShare() == 1) {
                                bundle.putBoolean(BundleTags.IS_SHARE, true);
                                bundle.putString(BundleTags.SHARE_URL, entity4.getShareUrl());
                                bundle.putString(BundleTags.SHARE_TITLE, entity4.getShareTitle());
                                bundle.putString(BundleTags.SHARE_DES, entity4.getShareDescription());
                                bundle.putString(BundleTags.SHARE_IMAGE, entity4.getShareImg());
                            }
                            mRootView.launchActivity(new Intent(mApplication, WebActivity.class), bundle);
                        }
                        break;
                    case 5:
                        String str5 = DataHelper.getStringSF(mApplication, DataHelperTags.ONLINE_LAWYER_URL);
                        HomeChildEntity entity5 = GsonUtil.convertString2Object(str5, HomeChildEntity.class);
                        if (!TextUtils.isEmpty(str5) && entity5 != null) {
                            bundle.clear();
                            bundle.putString(BundleTags.URL, entity5.getJumpurl());
                            bundle.putString(BundleTags.TITLE, entity5.getTitle());
                            mRootView.launchActivity(new Intent(mApplication, WebActivity.class), bundle);
                        }
                        break;
                    case 6:
                        String str6 = DataHelper.getStringSF(mApplication, DataHelperTags.PRIVATE_LAWYER_URL);
                        HomeChildEntity entity6 = GsonUtil.convertString2Object(str6, HomeChildEntity.class);
                        if (!TextUtils.isEmpty(str6) && entity6 != null) {
                            bundle.clear();
                            bundle.putString(BundleTags.URL, entity6.getJumpurl());
                            bundle.putString(BundleTags.TITLE, entity6.getTitle());
                            mRootView.launchActivity(new Intent(mApplication, WebActivity.class), bundle);
                        }
                        break;
                    case 7:
                        String str7 = DataHelper.getStringSF(mApplication, DataHelperTags.ANNUAL_URL);
                        HomeChildEntity entity7 = GsonUtil.convertString2Object(str7, HomeChildEntity.class);
                        if (!TextUtils.isEmpty(str7) && entity7 != null) {
                            bundle.clear();
                            bundle.putString(BundleTags.URL, entity7.getJumpurl());
                            bundle.putString(BundleTags.TITLE, entity7.getTitle());
                            mRootView.launchActivity(new Intent(mApplication, WebActivity.class), bundle);
                        }
                        break;
                }
                return;
            }

            AppUtils.post(ORDER_COUPON, REFRESH_COUPON, entity);
            mRootView.killMyself();
        });

        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            OrderCouponEntity entity = adapter.getItem(position);
            if (entity == null) return;
            if (mRootView.getType() != 1) return;

            mRootView.showDetailDialog(entity.getPreferentialContent());

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


    private void getList(boolean isAdd) {
        if(type == 5){
            corporateCoupon(isAdd);
        }else if (type == 4) {
            getCouponsList2(isAdd, 128);
        } else if (type == 3) {
            getBuyEquityCouponsList(isAdd);
        } else if (type == 2) {
            getCouponsList2(isAdd);//热门需求
        } else if (type == 1) {
            getMeCouponsList(isAdd);//我的优惠券
        } else {
            getCouponsList(isAdd);//快速咨询
        }
    }


    public void corporateCoupon(boolean isAdd){
        mModel.corporateCoupon(pageNum, orderAmount)
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

    private void getBuyEquityCouponsList(boolean isAdd) {
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
        this.getCouponsList2(isAdd, productId);
    }

    private void getCouponsList2(boolean isAdd, int productId) {
        mModel.optimalRequireList(pageNum, orderAmount, productId)
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

    private void getMeCouponsList(boolean isAdd) {
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
