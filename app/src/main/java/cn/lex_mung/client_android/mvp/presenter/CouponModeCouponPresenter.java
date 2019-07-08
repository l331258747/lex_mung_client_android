package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.CouponsEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.CouponModeCouponContract;


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
    private OrderCouponAdapter adapter;

    @Inject
    public CouponModeCouponPresenter(CouponModeCouponContract.Model model, CouponModeCouponContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getList(false);
    }

    private void initAdapter() {
        adapter = new OrderCouponAdapter(mRootView.getFragment().getActivity());
        adapter.setOnItemClickListener((adapter1, view, position) -> {

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

    public void getList(boolean isAdd){
        List<OrderCouponEntity> list = new ArrayList();
        OrderCouponEntity entity = new OrderCouponEntity();
        entity.setCouponName("setCouponName");
        entity.setPreferentialContent("setPreferentialContent");
        entity.setEndTime("setEndTime");
        entity.setStartTime("setStartTime");
        entity.setCouponStatus(1);
        entity.setPreferentialWay(2);


        list.add(entity);
        list.add(entity);
        list.add(entity);
        list.add(entity);
        list.add(entity);


        mRootView.setEmptyView(adapter);
        smartRefreshLayout.finishRefresh();
        adapter.setNewData(list);
        if (totalNum == pageNum) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        }
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
