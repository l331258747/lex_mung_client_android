package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerRushOrdersComponent;
import cn.lex_mung.client_android.di.module.RushOrdersModule;
import cn.lex_mung.client_android.mvp.contract.RushOrdersContract;
import cn.lex_mung.client_android.mvp.model.entity.order.LawyerBean;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderBean;
import cn.lex_mung.client_android.mvp.presenter.RushOrdersPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.CompletedView;
import cn.lex_mung.client_android.mvp.ui.widget.RushOrdersView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StatusBarUtil;

/**
 * 抢单
 */
public class RushOrdersActivity extends BaseActivity<RushOrdersPresenter> implements RushOrdersContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.view_rush_orders)
    RushOrdersView rushOrdersView;
    @BindView(R.id.tasks_view)
    CompletedView completedView;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.cl_rush_rush)
    LinearLayout clRushRush;
    @BindView(R.id.cl_rush_error)
    LinearLayout clRushError;
    @BindView(R.id.cl_rush_reply)
    ConstraintLayout clRushReply;


    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_content_2)
    TextView tvContent2;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_fast_time)
    TextView tvOrderFastTime;
    @BindView(R.id.tv_order_score)
    TextView tvOrderScore;
    @BindView(R.id.tv_lawyer_call)
    TextView tvLawyerCall;
    @BindView(R.id.view_bg_down)
    View viewBgDown;

    String lawyerPhone;
    int requirementId;
    OrderBean orderBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRushOrdersComponent
                .builder()
                .appComponent(appComponent)
                .rushOrdersModule(new RushOrdersModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rush_orders;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_ff), 0);

        if (bundleIntent != null) {
            requirementId = bundleIntent.getInt(BundleTags.ID);
            mPresenter.onCreate(requirementId);
        }
    }

    @Override
    public void setTotalProgress(int totalProgress) {
        completedView.setTotalProgress(totalProgress);
    }

    @Override
    public void setCountdown(int position) {
        completedView.setProgress(position);
    }

    @OnClick({
            R.id.tv_custom_call,
            R.id.tv_lawyer_call,
            R.id.tv_go_orderDetail
    })
    public void onViewClicked(View view) {
        Intent dialIntent;
        switch (view.getId()) {
            case R.id.tv_custom_call:
                dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
                startActivity(dialIntent);
                break;
            case R.id.tv_lawyer_call:
                if (TextUtils.isEmpty(lawyerPhone)) return;
                dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lawyerPhone));
                startActivity(dialIntent);
                break;
            case R.id.tv_go_orderDetail:
                killMyself();
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
                if(orderBean == null) return;
                bundle.clear();
                bundle.putInt(BundleTags.ID, orderBean.getRequirementId());
                bundle.putString(BundleTags.TITLE,"需求详情");
                bundle.putInt(BundleTags.TYPE, 5);
                bundle.putString(BundleTags.ORDER_NO,orderBean.getOrderNo());
                bundle.putInt(BundleTags.IS_SHOW,1);
                bundle.putInt(BundleTags.STATE,orderBean.getStatus());
                launchActivity(new Intent(mActivity, OrderDetailsActivity.class), bundle);
                break;
        }
    }

    @Override
    public void setRushOrdersView(int i) {
        rushOrdersView.setProgress(i);
    }

    @Override
    public void stopFlipping() {
        if (viewFlipper != null) {
            viewFlipper.stopFlipping();
        }
    }

    @Override
    public void setStatusSuccess(LawyerBean lawyerBean) {
        if (!TextUtils.isEmpty(lawyerBean.getIcon_image())) {
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(lawyerBean.getIcon_image())
                            .isCircle(true)
                            .imageView(ivHead)
                            .build());
        }
        tvName.setText(lawyerBean.getMember_name());
        tvContent.setText(lawyerBean.getMember_position_name());
        tvContent2.setText(lawyerBean.getContent2());

        tvOrderNum.setText(lawyerBean.getContract_count());
        tvOrderTime.setText(lawyerBean.getResponse_time());
        tvOrderFastTime.setText(lawyerBean.getFinish_time());
        tvOrderScore.setText(lawyerBean.getSatisfaction_degree());
        tvLawyerCall.setText(lawyerBean.getMobile2());

        lawyerPhone = lawyerBean.getMobile();
    }

    @Override
    public void setOrderInfo(OrderBean orderInfo) {
        this.orderBean = orderInfo;
    }

    @Override
    public void setOrderStatus(int position) {
        switch (position) {
            case 0://初始
                clRushRush.setVisibility(View.GONE);
                clRushError.setVisibility(View.GONE);
                clRushReply.setVisibility(View.GONE);
                break;
            case 1://倒计时
                clRushRush.setVisibility(View.VISIBLE);
                clRushError.setVisibility(View.GONE);
                clRushReply.setVisibility(View.GONE);
                break;
            case 2://匹配到律师
                clRushRush.setVisibility(View.GONE);
                clRushError.setVisibility(View.GONE);
                clRushReply.setVisibility(View.VISIBLE);

                viewBgDown.setVisibility(View.VISIBLE);
                break;
            case 3://失败
                clRushRush.setVisibility(View.GONE);
                clRushError.setVisibility(View.VISIBLE);
                clRushReply.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void initTextBanner() {
        viewFlipper.setAutoStart(false);
        viewFlipper.setFlipInterval(2000); // ms
    }


    @Override
    protected void onDestroy() {
        mPresenter.setCountdownStop(true);
        mPresenter.setGetStatusStop(true);
        stopFlipping();
        super.onDestroy();
    }

    @Override
    public void removeAllViews() {
        viewFlipper.removeAllViews();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void addFlippingView(View view) {
        viewFlipper.addView(view);
    }

    @Override
    public void startFlipping() {
        viewFlipper.startFlipping();
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

    @Override
    public void onBackPressed() {
        if (mPresenter.getOrderStatus() == 2) {
            new DefaultDialog(mActivity, dialog -> {
                killMyself();
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
            },"您可以在我的-订单中查看服务律师和订单详情.","确定","取消").show();
        } else {
            new DefaultDialog(mActivity, dialog -> {
                killMyself();
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
            },"我们将继续为您优选律师，您可以在我的-订单中查看服务律师和订单详情.","确定","取消").show();
        }
    }
}
