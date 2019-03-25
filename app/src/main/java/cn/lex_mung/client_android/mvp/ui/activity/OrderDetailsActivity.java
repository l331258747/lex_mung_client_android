package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.View;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.OrderDetailsModule;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerOrderDetailsComponent;
import cn.lex_mung.client_android.mvp.contract.OrderDetailsContract;
import cn.lex_mung.client_android.mvp.presenter.OrderDetailsPresenter;

import cn.lex_mung.client_android.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class OrderDetailsActivity extends BaseActivity<OrderDetailsPresenter> implements OrderDetailsContract.View {
    @BindView(R.id.group_time)
    Group groupTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.tv_order_type_text)
    TextView tvOrderTypeText;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.group_time_1)
    Group groupTime1;
    @BindView(R.id.tv_order_customer)
    TextView tvOrderCustomer;
    @BindView(R.id.tv_order_time_start)
    TextView tvOrderTimeStart;
    @BindView(R.id.tv_order_time_end)
    TextView tvOrderTimeEnd;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.group_bottom)
    Group groupBottom;

    private MyCountDownTimer myCountDownTimer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDetailsComponent
                .builder()
                .appComponent(appComponent)
                .orderDetailsModule(new OrderDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setType(bundleIntent.getInt(BundleTags.TYPE));
            mPresenter.setId(bundleIntent.getInt(BundleTags.ID));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getOrderDetail();
    }

    @OnClick({R.id.ll_call_lawyer})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.ll_call_lawyer:
                mPresenter.getUserPhone();
                break;
        }
    }

    @Override
    public void call(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            launchActivity(intent);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void showLayout(int i) {
        switch (i) {
            case 1:
                groupTime.setVisibility(View.VISIBLE);
                groupTime1.setVisibility(View.VISIBLE);
                groupBottom.setVisibility(View.VISIBLE);
                break;
            case 2:
                groupTime1.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvTip.setText(AppUtils.getString(mActivity, R.string.text_order_details_tip_2));
                groupTime1.setVisibility(View.VISIBLE);
                tvOrderTypeText.setVisibility(View.GONE);
                tvOrderType.setVisibility(View.GONE);
                break;
        }
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
    public void setOrderType(String serviceType) {
        tvOrderType.setText(serviceType);
    }

    @Override
    public void setOrderAmount(String s) {
        tvOrderPrice.setText(s);
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
    public void setOrderRemain(int remain) {
        if (remain == 0) return;
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
            myCountDownTimer = null;
        }
        myCountDownTimer = new MyCountDownTimer(remain);
        myCountDownTimer.start();
    }

    private class MyCountDownTimer extends CountDownTimer {
        SimpleDateFormat sdf;

        @SuppressLint("SimpleDateFormat")
        MyCountDownTimer(long millisInFuture) {
            super(millisInFuture, 1000);
            if (millisInFuture > 1000 * 60 * 60 * 24) {
                sdf = new SimpleDateFormat("dd天 HH:mm:ss", Locale.getDefault());
            } else if (millisInFuture > 1000 * 60 * 60) {
                sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            } else {
                sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
            }
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        }

        @Override
        @SuppressLint({"SetTextI18n"})
        public void onTick(long l) {
            String s = sdf.format(new Date(l));
            if (tvTime != null) {
                tvTime.setText("剩余回电时间: " + s);
            }
        }

        @Override
        public void onFinish() {
            try {
//                mPresenter.getOrderDetails();
            } catch (Exception ignored) {
            }
        }
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
