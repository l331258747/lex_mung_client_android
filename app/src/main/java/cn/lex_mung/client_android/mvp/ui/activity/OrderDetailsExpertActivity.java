package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.OrderDetailsExpertModule;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.dialog.SingleTextTwoBtnDialog;
import cn.lex_mung.client_android.mvp.ui.widget.OrderDetailExpertView;
import cn.lex_mung.client_android.mvp.ui.widget.OrderDetailView;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerOrderDetailsExpertComponent;
import cn.lex_mung.client_android.mvp.contract.OrderDetailsExpertContract;
import cn.lex_mung.client_android.mvp.presenter.OrderDetailsExpertPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.DeviceUtils;
import me.zl.mvp.utils.StatusBarUtil;

public class OrderDetailsExpertActivity extends BaseActivity<OrderDetailsExpertPresenter> implements OrderDetailsExpertContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.orderDetailExpertView)
    OrderDetailExpertView orderDetailView;

    @BindView(R.id.ll_info_lawyer)
    LinearLayout ll_info_lawyer;
    @BindView(R.id.tv_info_lawyer)
    TextView tv_info_lawyer;

    @BindView(R.id.ll_info_time)
    LinearLayout ll_info_time;
    @BindView(R.id.tv_info_time)
    TextView tv_info_time;

    @BindView(R.id.ll_info_price)
    LinearLayout ll_info_price;
    @BindView(R.id.tv_info_price)
    TextView tv_info_price;

    @BindView(R.id.ll_info_order_price)
    LinearLayout ll_info_order_price;
    @BindView(R.id.tv_info_order_price)
    TextView tv_info_order_price;

    @BindView(R.id.ll_info_coupon_type)
    LinearLayout ll_info_coupon_type;
    @BindView(R.id.tv_info_coupon_type)
    TextView tv_info_coupon_type;

    @BindView(R.id.ll_info_order_no)
    LinearLayout ll_info_order_no;
    @BindView(R.id.tv_info_order_no)
    TextView tv_info_order_no;

    @BindView(R.id.ll_info_talk_time)
    LinearLayout ll_info_talk_time;
    @BindView(R.id.tv_info_talk_time)
    TextView tv_info_talk_time;

    @BindView(R.id.ll_info_talk_record)
    LinearLayout ll_info_talk_record;
    @BindView(R.id.tv_info_talk_record)
    TextView tv_info_talk_record;

    @BindView(R.id.tv_tip_content)
    TextView tv_tip_content;

    @BindView(R.id.cl_btn)
    ConstraintLayout cl_btn;
    @BindView(R.id.tv_btn_tip)
    TextView tv_btn_tip;
    @BindView(R.id.tv_btn)
    TextView tv_btn;

    private int id = -1;
    private String orderNo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDetailsExpertComponent
                .builder()
                .appComponent(appComponent)
                .orderDetailsExpertModule(new OrderDetailsExpertModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_details_expert;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleView.getTitleTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_ff));
        StatusBarUtil.setTransparentForImageView(mActivity, null);

        int statusBarHeight = DeviceUtils.getStatusBarHeight(mActivity);
        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) titleView.getLayoutParams();
        lp1.topMargin = statusBarHeight;
        titleView.setLayoutParams(lp1);

        if (bundleIntent != null) {
            if (!TextUtils.isEmpty(bundleIntent.getString(BundleTags.TITLE)))
                titleView.setTitle(bundleIntent.getString(BundleTags.TITLE));

            mPresenter.setId(id = bundleIntent.getInt(BundleTags.ID));
            orderNo = bundleIntent.getString(BundleTags.ORDER_NO);

        }

        String[] strs = {"预约中", "进行中", "已结束"};
        orderDetailView.initView(strs);

        mPresenter.getOrderDetail(orderNo);
    }

    @Override
    public void setOrderDetailView(int index) {
        if (index != -1) {
            orderDetailView.setProgress(index);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setBottomStatus(boolean isLayoutShow, String btnTipName, boolean isBtnShow, String btnName, View.OnClickListener onClick) {
        if (isLayoutShow) {
            cl_btn.setVisibility(View.VISIBLE);
            tv_btn_tip.setText(btnTipName);
            tv_btn.setVisibility(isBtnShow ? View.VISIBLE : View.GONE);
            if (!TextUtils.isEmpty(btnName)) {
                tv_btn.setText(btnName);
            }
            tv_btn.setOnClickListener(onClick);
        } else {
            cl_btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCancelDialog(){
        new SingleTextTwoBtnDialog(mActivity)
                .setSubmitStr("确定")
                .setCancelStr("返回")
                .setContent("取消订单后律师将不能再联系您，是否继续？")
                .setSubmitOnClickListener(() -> {
                    mPresenter.expertCancel(orderNo);
                }).show();
    }

    @Override
    public void showFinishDialog(){
        new SingleTextTwoBtnDialog(mActivity)
                .setSubmitStr("确定")
                .setCancelStr("返回")
                .setContent("结束订单后律师将不能再联系您，是否继续？")
                .setSubmitOnClickListener(() -> {
                    mPresenter.expertFinish(orderNo);
                }).show();
    }

    @Override
    public void setLawyer(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_lawyer.setVisibility(View.GONE);
        } else {
            ll_info_lawyer.setVisibility(View.VISIBLE);
            tv_info_lawyer.setText(s);
        }
    }

    @Override
    public void setTime(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_time.setVisibility(View.GONE);
        } else {
            ll_info_time.setVisibility(View.VISIBLE);
            tv_info_time.setText(s);
        }
    }

    @Override
    public void setPrice(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_price.setVisibility(View.GONE);
        } else {
            ll_info_price.setVisibility(View.VISIBLE);
            tv_info_price.setText(s);
        }
    }

    @Override
    public void setOrderPrice(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_order_price.setVisibility(View.GONE);
        } else {
            ll_info_order_price.setVisibility(View.VISIBLE);
            tv_info_order_price.setText(s);
        }
    }

    @Override
    public void setCouponType(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_coupon_type.setVisibility(View.GONE);
        } else {
            ll_info_coupon_type.setVisibility(View.VISIBLE);
            tv_info_coupon_type.setText(s);
        }
    }

    @Override
    public void setOrderNo(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_order_no.setVisibility(View.GONE);
        } else {
            ll_info_order_no.setVisibility(View.VISIBLE);
            tv_info_order_no.setText(s);
        }
    }

    @Override
    public void setTalkTime(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_talk_time.setVisibility(View.GONE);
        } else {
            ll_info_talk_time.setVisibility(View.VISIBLE);
            tv_info_talk_time.setText(s);
        }
    }

    @Override
    public void setTalkRecord(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_talk_record.setVisibility(View.GONE);
        } else {
            ll_info_talk_record.setVisibility(View.VISIBLE);
            tv_info_talk_record.setText(s);
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
