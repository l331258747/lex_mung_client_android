package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.decoration.SpacesItemDecoration;
import cn.lex_mung.client_android.di.component.DaggerOrderCouponComponent;
import cn.lex_mung.client_android.di.module.OrderCouponModule;
import cn.lex_mung.client_android.mvp.contract.OrderCouponContract;
import cn.lex_mung.client_android.mvp.presenter.OrderCouponPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class OrderCouponActivity extends BaseActivity<OrderCouponPresenter> implements OrderCouponContract.View {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderCouponComponent
                .builder()
                .appComponent(appComponent)
                .orderCouponModule(new OrderCouponModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_coupon;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate(smartRefreshLayout);
    }

    @Override
    public void initRecyclerView(OrderCouponAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, AppUtils.dip2px(mActivity, AppUtils.getXmlDef(mActivity, R.dimen.qb_px_35))));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(OrderCouponAdapter adapter) {
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void showLoading(@NonNull String message) {
        loading = LoadingDialog.getInstance().init(mActivity, message, false);
        loading.show();
    }

    @Override
    public void hideLoading() {
        if (loading != null
                && loading.isShowing())
            loading.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        AppUtils.makeText(mActivity, message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        AppUtils.startActivity(intent);
    }

    @Override
    public void launchActivity(Intent intent, Bundle bundle) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launchActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
