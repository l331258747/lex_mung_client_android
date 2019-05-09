package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrderCouponContract;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;


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

    private OrderCouponAdapter adapter;

    @Inject
    public OrderCouponPresenter(OrderCouponContract.Model model, OrderCouponContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate() {
        initAdapter();
        getCouponsList();
    }

    private void initAdapter() {
        adapter = new OrderCouponAdapter();
        mRootView.initRecyclerView(adapter);
    }

    private void getCouponsList() {

        List<OrderCouponEntity> entities = new ArrayList<>();
        OrderCouponEntity entity = new OrderCouponEntity();
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);

        mRootView.setEmptyView(adapter);
        adapter.setNewData(entities);

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
