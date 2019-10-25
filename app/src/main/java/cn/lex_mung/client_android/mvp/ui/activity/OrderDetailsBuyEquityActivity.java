package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.OrderDetailsBuyEquityModule;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.EvaluateStarView;
import cn.lex_mung.client_android.mvp.ui.widget.OrderDetailView;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerOrderDetailsBuyEquityComponent;
import cn.lex_mung.client_android.mvp.contract.OrderDetailsBuyEquityContract;
import cn.lex_mung.client_android.mvp.presenter.OrderDetailsBuyEquityPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.DeviceUtils;
import me.zl.mvp.utils.StatusBarUtil;

public class OrderDetailsBuyEquityActivity extends BaseActivity<OrderDetailsBuyEquityPresenter> implements OrderDetailsBuyEquityContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.orderDetailView)
    OrderDetailView orderDetailView;

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

    @BindView(R.id.ll_info_no)
    LinearLayout ll_info_no;
    @BindView(R.id.tv_info_no)
    TextView tv_info_no;

    @BindView(R.id.ll_info_time)
    LinearLayout ll_info_time;
    @BindView(R.id.tv_info_time)
    TextView tv_info_time;

    @BindView(R.id.ll_info_server_name)
    LinearLayout ll_info_server_name;
    @BindView(R.id.tv_info_server_name)
    TextView tv_info_server_name;

    @BindView(R.id.ll_info_order_status)
    LinearLayout ll_info_order_status;
    @BindView(R.id.tv_info_order_status)
    TextView tv_info_order_status;

    @BindView(R.id.ll_info_release_user)
    LinearLayout ll_info_release_user;
    @BindView(R.id.tv_info_release_user)
    TextView tv_info_release_user;

    @BindView(R.id.ll_info_order_time)
    LinearLayout ll_info_order_time;
    @BindView(R.id.tv_info_order_time)
    TextView tv_info_order_time;

    @BindView(R.id.ll_info_server_type)
    LinearLayout ll_info_server_type;
    @BindView(R.id.tv_info_server_type)
    TextView tv_info_server_type;

    @BindView(R.id.ll_info_talk_time)
    LinearLayout ll_info_talk_time;
    @BindView(R.id.tv_info_talk_time)
    TextView tv_info_talk_time;

    @BindView(R.id.ll_info_talk_record)
    LinearLayout ll_info_talk_record;
    @BindView(R.id.tv_info_talk_record)
    TextView tv_info_talk_record;

    @BindView(R.id.ll_info_meet_time)
    LinearLayout ll_info_meet_time;
    @BindView(R.id.tv_info_meet_time)
    TextView tv_info_meet_time;

    @BindView(R.id.ll_info_meet_duration)
    LinearLayout ll_info_meet_duration;
    @BindView(R.id.tv_info_meet_duration)
    TextView tv_info_meet_duration;

    @BindView(R.id.ll_info_meet_place)
    LinearLayout ll_info_meet_place;
    @BindView(R.id.tv_info_meet_place)
    TextView tv_info_meet_place;

    @BindView(R.id.fl_evaluate_info)
    FrameLayout fl_evaluate_info;

    @BindView(R.id.evaluateStarView_major)
    EvaluateStarView evaluateStarView_major;
    @BindView(R.id.evaluateStarView_speed)
    EvaluateStarView evaluateStarView_speed;
    @BindView(R.id.evaluateStarView_attitude)
    EvaluateStarView evaluateStarView_attitude;
    @BindView(R.id.tv_evaluate_info_content)
    TextView tv_evaluate_info_content;
    @BindView(R.id.iv_evaluate_whole)
    ImageView iv_evaluate_whole;
    @BindView(R.id.tv_evaluate_whole)
    TextView tv_evaluate_whole;

    @BindView(R.id.fl_500)
    FrameLayout fl_500;

    private int id;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDetailsBuyEquityComponent
                .builder()
                .appComponent(appComponent)
                .orderDetailsBuyEquityModule(new OrderDetailsBuyEquityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_details_buy_equity;
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
            id = bundleIntent.getInt(BundleTags.ID);
        }

        String[] strs = {"预约律师", "平台优选律师", "律师主动回电", "匿名评价服务"};
        orderDetailView.initView(strs);

//        mPresenter.caseorderDetail(id);
    }

    public void setOrderDetailView(int index) {
        if (index == -1) {
            orderDetailView.setProgress(0);
        } else {
            orderDetailView.setProgress(index);
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
