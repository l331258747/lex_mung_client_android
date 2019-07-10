package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CouponsEntity;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity2;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntityCoupon;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.ComponModeAdapter1;
import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.CouponModeCouponContract;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@FragmentScope
public class CouponModeCouponPresenter extends BasePresenter<CouponModeCouponContract.Model, CouponModeCouponContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    SmartRefreshLayout smartRefreshLayout;

    private int pageNum;
    private int totalNum;
    private ComponModeAdapter1 adapter;

    private int organizationLevId;
    private int memberId;
    private int lMemberId;

    private int couponId;

    @Inject
    public CouponModeCouponPresenter(CouponModeCouponContract.Model model, CouponModeCouponContract.View rootView) {
        super(model, rootView);
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setLMemberId(int lMemberId) {
        this.lMemberId = lMemberId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setOrganizationLevId(int organizationLevId) {
        this.organizationLevId = organizationLevId;
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getList(false);
    }

    private void initAdapter() {
        adapter = new ComponModeAdapter1();
        adapter.setCouponId(couponId);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            ReleaseDemandOrgMoneyEntityCoupon entity = adapter.getItem(position);
            if (entity == null) return;
            //TODO
            //AppUtils.postInt(REFRESH, REFRESH_DISCOUNT_WAY, entity.getOrganizationLevId());
            //mRootView.getCouponModeActivity().killMyself();
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

    public void getList(boolean isAdd) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("lmemberId", lMemberId);
        mModel.getOrgList2(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ReleaseDemandOrgMoneyEntity2>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ReleaseDemandOrgMoneyEntity2> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getCoupon().getPages();
                            pageNum = baseResponse.getData().getCoupon().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getCoupon().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                mRootView.setEmptyView(adapter);
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getCoupon().getList());
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
