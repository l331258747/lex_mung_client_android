package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.View;
import android.widget.TextView;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.module.TradingListDetailsModule;
import com.lex_mung.client_android.mvp.contract.TradingListDetailsContract;
import com.lex_mung.client_android.mvp.model.entity.TradingListEntity;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import com.lex_mung.client_android.di.component.DaggerTradingListDetailsComponent;
import com.lex_mung.client_android.mvp.presenter.TradingListDetailsPresenter;

import com.lex_mung.client_android.R;

public class TradingListDetailsActivity extends BaseActivity<TradingListDetailsPresenter> implements TradingListDetailsContract.View {

    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.group_time)
    Group groupTime;
    @BindView(R.id.tv_order_customer)
    TextView tvOrderCustomer;
    @BindView(R.id.tv_order_time_start)
    TextView tvOrderTimeStart;
    @BindView(R.id.tv_order_time_end)
    TextView tvOrderTimeEnd;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTradingListDetailsComponent
                .builder()
                .appComponent(appComponent)
                .tradingListDetailsModule(new TradingListDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_trading_list_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setBean((TradingListEntity.ListBean) bundleIntent.getSerializable(BundleTags.ENTITY));
    }

    @Override
    public void setOrderNo(String orderNo) {
        tvOrderNumber.setText(orderNo);
    }

    @Override
    public void setOrderTime(String conversationStart) {
        tvOrderDate.setText(conversationStart);

    }

    @Override
    public void setOrderName(String businessType) {
        tvOrderName.setText(businessType);
    }

    @Override
    public void setOrderAmount(String s, String s1, int color, String money) {
        tvOrderPrice.setText(s);
        tvPayPrice.setText(money);
        tvPayPrice.setTextColor(color);
        tvPayType.setText(s1);
    }

    @Override
    public void setOrderStatus(String statusName) {
        tvOrderStatus.setText(statusName);
    }

    @Override
    public void setOrderCustomer(String memberName) {
        tvOrderCustomer.setText(memberName);
    }

    @Override
    public void setOrderStartTime(String conversationStart) {
        tvOrderTimeStart.setText(conversationStart);
    }

    @Override
    public void setOrderEndTime(String conversationEnd) {
        tvOrderTimeEnd.setText(conversationEnd);
    }

    @Override
    public void setOrderTotal(String duration) {
        tvOrderTotal.setText(duration);
    }

    @Override
    public void showLayout() {
        groupTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOrderStatusColor(int color) {
        tvOrderStatus.setTextColor(color);
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
