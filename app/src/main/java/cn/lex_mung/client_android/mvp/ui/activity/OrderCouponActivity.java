package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerOrderCouponComponent;
import cn.lex_mung.client_android.di.module.OrderCouponModule;
import cn.lex_mung.client_android.mvp.contract.OrderCouponContract;
import cn.lex_mung.client_android.mvp.presenter.OrderCouponPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter2;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.SingleTextDialog;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import static cn.lex_mung.client_android.app.EventBusTags.ORDER_COUPON.ORDER_COUPON;
import static cn.lex_mung.client_android.app.EventBusTags.ORDER_COUPON.REFRESH_COUPON;

public class OrderCouponActivity extends BaseActivity<OrderCouponPresenter> implements OrderCouponContract.View {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_coupon)
    TextView tvNoCoupon;
    @BindView(R.id.titleView)
    TitleView titleView;

    int couponId;
    int type;//1我的优惠券，0快速咨询，2热门需求，3付费权益，4在线法律顾问,5年度企业会员

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
        type = bundleIntent.getInt(BundleTags.TYPE,0);

        couponId = bundleIntent.getInt(BundleTags.ID,-1);
        if(couponId == 0)
            couponId = -1;
        mPresenter.setType(bundleIntent.getInt(BundleTags.TYPE,0));
        mPresenter.setProductId(bundleIntent.getInt(BundleTags.REQUIREMENT_ID,0));
        mPresenter.setOrderAmount(bundleIntent.getDouble(BundleTags.MONEY,0));
        mPresenter.onCreate(smartRefreshLayout,couponId);

        setMyCouponLayout();
    }

    public void setMyCouponLayout(){
        if(type == 1){
            tvNoCoupon.setVisibility(View.GONE);
            titleView.setTitle("我的优惠券");
        }
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void initRecyclerView(OrderCouponAdapter2 adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void showDetailDialog(String str) {
        new SingleTextDialog(mActivity).setSubmitStr("关闭").setContent(str).show();
    }

    //tv_no_coupon
    @OnClick({
            R.id.tv_no_coupon
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_no_coupon:
                AppUtils.post(ORDER_COUPON, REFRESH_COUPON, null);
                killMyself();
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public void setEmptyView(OrderCouponAdapter2 adapter) {
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
