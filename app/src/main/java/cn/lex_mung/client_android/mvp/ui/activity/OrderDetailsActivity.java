//package cn.lex_mung.client_android.mvp.ui.activity;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.constraint.Group;
//import android.view.View;
//import android.widget.TextView;
//
//import cn.lex_mung.client_android.app.BundleTags;
//import cn.lex_mung.client_android.app.TimeFormat;
//import cn.lex_mung.client_android.di.module.OrderDetailsModule;
//import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import me.zl.mvp.base.BaseActivity;
//import me.zl.mvp.di.component.AppComponent;
//import me.zl.mvp.utils.AppUtils;
//
//import cn.lex_mung.client_android.di.component.DaggerOrderDetailsComponent;
//import cn.lex_mung.client_android.mvp.contract.OrderDetailsContract;
//import cn.lex_mung.client_android.mvp.presenter.OrderDetailsPresenter;
//
//import cn.lex_mung.client_android.R;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TimeZone;
//
//public class OrderDetailsActivity extends BaseActivity<OrderDetailsPresenter> implements OrderDetailsContract.View {
//    @BindView(R.id.group_time)
//    Group groupTime;
//    @BindView(R.id.group_coupon)
//    Group groupCoupon;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.tv_order_number)
//    TextView tvOrderNumber;
//    @BindView(R.id.tv_order_date)
//    TextView tvOrderDate;
//    @BindView(R.id.tv_order_name)
//    TextView tvOrderName;
//    @BindView(R.id.tv_order_type_text)
//    TextView tvOrderTypeText;
//    @BindView(R.id.tv_order_type)
//    TextView tvOrderType;
//    @BindView(R.id.tv_order_price)
//    TextView tvOrderPrice;
//    @BindView(R.id.tv_order_coupon_price)
//    TextView tvOrderCouponPrice;
//    @BindView(R.id.tv_order_amount_price)
//    TextView tvOrderAmountPrice;
//    @BindView(R.id.tv_order_status)
//    TextView tvOrderStatus;
//    @BindView(R.id.group_time_1)
//    Group groupTime1;
//    @BindView(R.id.tv_order_customer)
//    TextView tvOrderCustomer;
//    @BindView(R.id.tv_order_time_start)
//    TextView tvOrderTimeStart;
//    @BindView(R.id.tv_order_time_end)
//    TextView tvOrderTimeEnd;
//    @BindView(R.id.tv_order_total)
//    TextView tvOrderTotal;
//    @BindView(R.id.tv_tip)
//    TextView tvTip;
//    @BindView(R.id.group_bottom)
//    Group groupBottom;
//
//    private MyCountDownTimer myCountDownTimer;
//
//    @Override
//    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerOrderDetailsComponent
//                .builder()
//                .appComponent(appComponent)
//                .orderDetailsModule(new OrderDetailsModule(this))
//                .build()
//                .inject(this);
//    }
//
//    @Override
//    public int initView(@Nullable Bundle savedInstanceState) {
//        return R.layout.activity_order_details;
//    }
//
//    @Override
//    public void initData(@Nullable Bundle savedInstanceState) {
//        if (bundleIntent != null) {
//            mPresenter.setType(bundleIntent.getInt(BundleTags.TYPE));
//            mPresenter.setId(bundleIntent.getInt(BundleTags.ID));
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mPresenter.getOrderDetail();
//    }
//
//    @OnClick({R.id.ll_call_lawyer})
//    public void onViewClicked(View view) {
//        if (isFastClick()) return;
//        switch (view.getId()) {
//            case R.id.ll_call_lawyer:
//                mPresenter.getUserPhone();
//                break;
//        }
//    }
//
//    @Override
//    public void call(String phone) {
//        try {
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//            Uri data = Uri.parse("tel:" + phone);
//            intent.setData(data);
//            launchActivity(intent);
//        } catch (Exception ignored) {
//        }
//    }
//
//    @Override
//    public void setCouponLayout(String couponPrice, String amountPrice) {
//        groupCoupon.setVisibility(View.VISIBLE);
//        tvOrderCouponPrice.setText(couponPrice);
//        tvOrderAmountPrice.setText(amountPrice);
//    }
//
//    @Override
//    public void showLayout(int i) {
//        switch (i) {
//            case 1:
//                groupTime.setVisibility(View.VISIBLE);
//                groupTime1.setVisibility(View.VISIBLE);
//                groupBottom.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                groupTime1.setVisibility(View.VISIBLE);
//                break;
//            case 3:
//                tvTip.setText(AppUtils.getString(mActivity, R.string.text_order_details_tip_2));
//                groupTime1.setVisibility(View.VISIBLE);
//                tvOrderTypeText.setVisibility(View.GONE);
//                tvOrderType.setVisibility(View.GONE);
//                break;
//        }
//    }
//
//    @Override
//    public void setOrderNo(String orderNo) {
//        tvOrderNumber.setText(orderNo);
//    }
//
//    @Override
//    public void setOrderTime(String conversationStart) {
//        tvOrderDate.setText(conversationStart);
//    }
//
//    @Override
//    public void setOrderName(String businessType) {
//        tvOrderName.setText(businessType);
//    }
//
//    @Override
//    public void setOrderType(String serviceType) {
//        tvOrderType.setText(serviceType);
//    }
//
//    @Override
//    public void setOrderAmount(String s) {
//        tvOrderPrice.setText(s);
//    }
//
//    @Override
//    public void setOrderStatus(String statusName) {
//        tvOrderStatus.setText(statusName);
//    }
//
//    @Override
//    public void setOrderCustomer(String memberName) {
//        tvOrderCustomer.setText(memberName);
//    }
//
//    @Override
//    public void setOrderStartTime(String conversationStart) {
//        tvOrderTimeStart.setText(conversationStart);
//    }
//
//    @Override
//    public void setOrderEndTime(String conversationEnd) {
//        tvOrderTimeEnd.setText(conversationEnd);
//    }
//
//    @Override
//    public void setOrderTotal(String duration) {
//        tvOrderTotal.setText(duration);
//    }
//
//    @Override
//    public void setOrderRemain(int remain) {
//        if (remain == 0) return;
//        if (myCountDownTimer != null) {
//            myCountDownTimer.cancel();
//            myCountDownTimer = null;
//        }
//        myCountDownTimer = new MyCountDownTimer(remain);
//        myCountDownTimer.start();
//    }
//
//    private class MyCountDownTimer extends CountDownTimer {
//        SimpleDateFormat sdf;
//
//        @SuppressLint("SimpleDateFormat")
//        MyCountDownTimer(long millisInFuture) {
//            super(millisInFuture, 1000);
////            if (millisInFuture > 1000 * 60 * 60 * 24) {
////                sdf = new SimpleDateFormat("dd天 HH:mm:ss", Locale.getDefault());
////            } else if (millisInFuture > 1000 * 60 * 60) {
////                sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
////            } else {
////                sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
////            }
////            sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
//        }
//
//        @Override
//        @SuppressLint({"SetTextI18n"})
//        public void onTick(long l) {
////            String s = sdf.format(new Date(l));
////            if (tvTime != null) {
////                tvTime.setText("剩余回电时间: " + s);
////            }
//            if (tvTime != null)
//                tvTime.setText("剩余回电时间: " + TimeFormat.countDownToStr(l,0));
//        }
//
//        @Override
//        public void onFinish() {
//            try {
////                mPresenter.getOrderDetails();
//            } catch (Exception ignored) {
//            }
//        }
//    }
//
//    @Override
//    public void showLoading(@NonNull String message) {
//        loading = LoadingDialog.getInstance().init(mActivity, message, false);
//        loading.show();
//    }
//
//    @Override
//    public void hideLoading() {
//        if (loading != null
//                && loading.isShowing())
//            loading.dismiss();
//    }
//
//    @Override
//    public void showMessage(@NonNull String message) {
//        AppUtils.makeText(mActivity, message);
//    }
//
//    @Override
//    public void launchActivity(@NonNull Intent intent) {
//        AppUtils.startActivity(intent);
//    }
//
//    @Override
//    public void launchActivity(Intent intent, Bundle bundle) {
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        launchActivity(intent);
//    }
//
//    @Override
//    public void killMyself() {
//        finish();
//    }
//}

package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.TimeFormat;
import cn.lex_mung.client_android.di.component.DaggerOrderDetailsComponent;
import cn.lex_mung.client_android.di.module.OrderDetailsModule;
import cn.lex_mung.client_android.mvp.contract.OrderDetailsContract;
import cn.lex_mung.client_android.mvp.presenter.OrderDetailsPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.OrderDetailView;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DeviceUtils;
import me.zl.mvp.utils.StatusBarUtil;

public class OrderDetailsActivity extends BaseActivity<OrderDetailsPresenter> implements OrderDetailsContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.ll_count_down)
    LinearLayout llCountDown;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.orderDetailView)
    OrderDetailView orderDetailView;
    @BindView(R.id.view_title)
    RelativeLayout viewTitle;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.group_lawyer)
    Group groupLawyer;
    @BindView(R.id.view_lawyer)
    View viewLawyer;
    @BindView(R.id.iv_head_tip)
    ImageView ivHeadTip;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_content)
    TextView tvNameContent;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    @BindView(R.id.ll_info_no)
    LinearLayout ll_info_no;
    @BindView(R.id.tv_info_no)
    TextView tv_info_no;

    @BindView(R.id.ll_info_talk_time)
    LinearLayout ll_info_talk_time;
    @BindView(R.id.tv_info_talk_time)
    TextView tv_info_talk_time;

    @BindView(R.id.ll_info_talk_record)
    LinearLayout ll_info_talk_record;
    @BindView(R.id.tv_info_talk_record)
    TextView tv_info_talk_record;

    @BindView(R.id.ll_info_time)
    LinearLayout ll_info_time;
    @BindView(R.id.tv_info_time)
    TextView tv_info_time;

    @BindView(R.id.ll_info_name)
    LinearLayout ll_info_name;
    @BindView(R.id.tv_info_name)
    TextView tv_info_name;

    @BindView(R.id.ll_info_status)
    LinearLayout ll_info_status;
    @BindView(R.id.tv_info_status)
    TextView tv_info_status;

    @BindView(R.id.ll_info_order_price)
    LinearLayout ll_info_order_price;
    @BindView(R.id.tv_info_order_price)
    TextView tv_info_order_price;

    @BindView(R.id.ll_info_coupon_type)
    LinearLayout ll_info_coupon_type;
    @BindView(R.id.tv_info_coupon_type)
    TextView tv_info_coupon_type;

    @BindView(R.id.ll_info_coupon_price)
    LinearLayout ll_info_coupon_price;
    @BindView(R.id.tv_info_coupon_price)
    TextView tv_info_coupon_price;

    @BindView(R.id.ll_info_pay_type)
    LinearLayout ll_info_pay_type;
    @BindView(R.id.tv_info_pay_type)
    TextView tv_info_pay_type;

    @BindView(R.id.ll_info_pay_price)
    LinearLayout ll_info_pay_price;
    @BindView(R.id.tv_info_pay_price)
    TextView tv_info_pay_price;

    @BindView(R.id.ll_info_content)
    LinearLayout ll_info_content;
    @BindView(R.id.tv_info_content)
    TextView tv_info_content;

    private MyCountDownTimer myCountDownTimer;
    private boolean isLawyerClick = false;
    private int typeId = -1;
    private int isHot = -1;
    private int id = -1;
    private String orderNo;

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
        return R.layout.activity_order_details2;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            if(!TextUtils.isEmpty(bundleIntent.getString(BundleTags.TITLE)))
                titleView.setTitle(bundleIntent.getString(BundleTags.TITLE));

            mPresenter.setType(typeId = bundleIntent.getInt(BundleTags.TYPE));
            mPresenter.setId(id = bundleIntent.getInt(BundleTags.ID));
            mPresenter.setIsHot(isHot = bundleIntent.getInt(BundleTags.IS_SHOW, -1));
            mPresenter.setOrderNo(orderNo = bundleIntent.getString(BundleTags.ORDER_NO));
            if (isHot == 1) {
                titleView.setRightTv("合同");
                titleView.getRightTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_ff));
                titleView.getRightTv().setOnClickListener(v -> {

                });
            }

            if(typeId == 3 || (typeId == 5 && isHot != 1)){
                titleView.getTitleLlayout().setBackgroundColor(ContextCompat.getColor(mActivity,R.color.c_ff));
                titleView.getTitleTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_323232));
                titleView.getLeftIv().setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_back2));

                StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_ff), 0);

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                lp.topMargin = AppUtils.getDimens(mActivity,R.dimen.qb_px_100);
                scrollView.setLayoutParams(lp);

                viewTitle.setVisibility(View.GONE);
            }else{
                titleView.getTitleTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_ff));

                StatusBarUtil.setTransparentForImageView(mActivity, null);

                int statusBarHeight = DeviceUtils.getStatusBarHeight(mActivity);
                RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) titleView.getLayoutParams();
                lp1.topMargin = statusBarHeight;
                titleView.setLayoutParams(lp1);

                viewTitle.setVisibility(View.VISIBLE);
            }
        }

        String[] strs = {"支付未成功", "优选律师", "律师回电", "交易完成"};
        orderDetailView.initView(strs);

        if(typeId == 5){
            mPresenter.getRequirementDetail(id,orderNo);
        }else{
            mPresenter.getOrderDetail();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenter.getOrderDetail();
    }

    @OnClick({R.id.ll_call_lawyer,R.id.iv_call})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.ll_call_lawyer:
                mPresenter.getUserPhone();
                break;
            case R.id.iv_call:
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
                startActivity(dialIntent);
                break;
        }
    }



    @Override
    public void setOrderDetailView(int index) {
        if (index != -1) {
            if(index > 0){
                String[] strs = {"支付成功", "优选律师", "律师回电", "交易完成"};
                orderDetailView.initView(strs);
            }
            orderDetailView.setProgress(index);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setBottomStatus(String btnName, int textColor, int bgColor, android.view.View.OnClickListener isClick) {
        setBottomStatus(btnName,textColor,bgColor,isClick,true);
    }

    @Override
    public void setBottomStatus(String btnName, int textColor, int bgColor, android.view.View.OnClickListener isClick, boolean isHide) {
        tvBtn.setText(btnName);
        tvBtn.setTextColor(ContextCompat.getColor(mActivity, textColor));
        tvBtn.setBackgroundColor(ContextCompat.getColor(mActivity, bgColor));
        tvBtn.setOnClickListener(isClick);

        if(!isHide){
            tvBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void setInfoContent(String s) {
        if (TextUtils.isEmpty(s)) {
            ll_info_content.setVisibility(View.GONE);
        } else {
            ll_info_content.setVisibility(View.VISIBLE);
            tv_info_content.setText(s);
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
    public void setCouponLayout(int useCoupon, String couponPrice, String couponType) {
        if (useCoupon == 1) {
            ll_info_coupon_price.setVisibility(View.VISIBLE);
            tv_info_coupon_price.setText(couponPrice);
            ll_info_coupon_type.setVisibility(View.VISIBLE);
            tv_info_coupon_type.setText(couponType);
        } else {
            ll_info_coupon_price.setVisibility(View.GONE);
            ll_info_coupon_type.setVisibility(View.GONE);
        }
    }

    @Override
    public void setLawyerClick(boolean isClick) {
        this.isLawyerClick = isClick;
        if(isClick){
            ivHeadTip.setVisibility(View.VISIBLE);
        }else{
            ivHeadTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void setLawyerLayout(int id, String name, String nameContent, String headUrl) {
        if (id > 0) {
            groupLawyer.setVisibility(View.VISIBLE);
            tvName.setText(name);
            tvNameContent.setText(nameContent);
            if (!TextUtils.isEmpty(headUrl)) {
                mImageLoader.loadImage(mActivity
                        , ImageConfigImpl
                                .builder()
                                .url(headUrl)
                                .imageRadius(AppUtils.dip2px(mActivity, 5))
                                .imageView(ivHead)
                                .build());
            } else {
                ivHead.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_lawyer_avatar));
            }

            viewLawyer.setOnClickListener(v -> {
                if(!isLawyerClick) return;
                bundle.clear();
                bundle.putInt(BundleTags.ID, id);
                launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
            });
        } else {
            groupLawyer.setVisibility(View.GONE);
        }
    }

    @Override
    public void setOrderNo(String orderNo) {
        if(TextUtils.isEmpty(orderNo)){
            ll_info_no.setVisibility(View.GONE);
        }else{
            ll_info_no.setVisibility(View.VISIBLE);
            tv_info_no.setText(orderNo);
        }
    }

    @Override
    public void setOrderTime(String conversationStart) {
        if(TextUtils.isEmpty(orderNo)){
            ll_info_time.setVisibility(View.GONE);
        }else{
            ll_info_time.setVisibility(View.VISIBLE);
            tv_info_time.setText(conversationStart);
        }
    }

    @Override
    public void setOrderName(String businessType) {
        if(TextUtils.isEmpty(orderNo)){
            ll_info_name.setVisibility(View.GONE);
        }else{
            ll_info_name.setVisibility(View.VISIBLE);
            tv_info_name.setText(businessType);
        }
    }

    @Override
    public void setOrderType(String serviceType) {
        if(TextUtils.isEmpty(orderNo)){
            ll_info_name.setVisibility(View.GONE);
        }else{
            ll_info_name.setVisibility(View.VISIBLE);
            tv_info_name.setText(serviceType);//咨询类型
        }
    }

    @Override
    public void setOrderAmount(String s) {
        if(TextUtils.isEmpty(s)){
            ll_info_order_price.setVisibility(View.GONE);
        }else{
            ll_info_order_price.setVisibility(View.VISIBLE);
            tv_info_order_price.setText(s);
        }
    }

    @Override
    public void setOrderStatus(String statusName) {
        if(TextUtils.isEmpty(statusName)){
            ll_info_status.setVisibility(View.GONE);
        }else{
            ll_info_status.setVisibility(View.VISIBLE);
            tv_info_status.setText(statusName);
        }
    }

    @Override
    public void setPayType(String s) {
        if(TextUtils.isEmpty(s)){
            ll_info_pay_type.setVisibility(View.GONE);
        }else{
            ll_info_pay_type.setVisibility(View.VISIBLE);
            tv_info_pay_type.setText(s);
        }
    }

    @Override
    public void setOrderPayPrice(String s) {
        if(TextUtils.isEmpty(s)){
            ll_info_pay_price.setVisibility(View.GONE);
        }else{
            ll_info_pay_price.setVisibility(View.VISIBLE);
            tv_info_pay_price.setText(s);
        }
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
        llCountDown.setVisibility(View.VISIBLE);
    }

    private class MyCountDownTimer extends CountDownTimer {
        SimpleDateFormat sdf;

        @SuppressLint("SimpleDateFormat")
        MyCountDownTimer(long millisInFuture) {
            super(millisInFuture, 1000);
        }

        @Override
        @SuppressLint({"SetTextI18n"})
        public void onTick(long l) {
            if (tvCountDown != null)
                tvCountDown.setText("剩余回电时间: " + TimeFormat.countDownToStr(l, 0));
        }

        @Override
        public void onFinish() {
            if (tvCountDown != null)
                llCountDown.setVisibility(View.GONE);
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
