package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.TimeFormat;
import cn.lex_mung.client_android.di.component.DaggerOrderDetailsEntrustComponent;
import cn.lex_mung.client_android.di.module.OrderDetailsEntrustModule;
import cn.lex_mung.client_android.mvp.contract.OrderDetailsEntrustContract;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustListEntity;
import cn.lex_mung.client_android.mvp.presenter.OrderDetailsEntrustPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.OrderDetailView;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DeviceUtils;
import me.zl.mvp.utils.StatusBarUtil;

public class OrderDetailsEntrustActivity extends BaseActivity<OrderDetailsEntrustPresenter> implements OrderDetailsEntrustContract.View {
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

    @BindView(R.id.ll_info_name)
    LinearLayout ll_info_name;
    @BindView(R.id.tv_info_name)
    TextView tv_info_name;

    @BindView(R.id.ll_info_area)
    LinearLayout ll_info_area;
    @BindView(R.id.tv_info_area)
    TextView tv_info_area;

    @BindView(R.id.ll_info_entrust_type)
    LinearLayout ll_info_entrust_type;
    @BindView(R.id.tv_info_entrust_type)
    TextView tv_info_entrust_type;

    @BindView(R.id.ll_info_model)
    LinearLayout ll_info_model;
    @BindView(R.id.tv_info_model)
    TextView tv_info_model;

    @BindView(R.id.ll_info_price_bidding)
    LinearLayout ll_info_price_bidding;
    @BindView(R.id.tv_info_price_bidding)
    TextView tv_info_price_bidding;

    @BindView(R.id.ll_info_price_agent)
    LinearLayout ll_info_price_agent;
    @BindView(R.id.tv_info_price_agent)
    TextView tv_info_price_agent;

    @BindView(R.id.ll_info_entrust_info)
    LinearLayout ll_info_entrust_info;
    @BindView(R.id.tv_info_entrust_info)
    TextView tv_info_entrust_info;

    private int id;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDetailsEntrustComponent
                .builder()
                .appComponent(appComponent)
                .orderDetailsEntrustModule(new OrderDetailsEntrustModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_details_entrust;
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

        String[] strs = {"发布委托", "免费案件诊断", "选定意向律师", "达成委托"};
        orderDetailView.initView(strs);

        mPresenter.caseorderDetail(id);
    }

    public void setOrderDetailView(int index) {
        if (index == -1) {
            orderDetailView.setProgress(0);
        } else {
            orderDetailView.setProgress(index);
        }
    }

    @Override
    public void setEntity(EntrustListEntity entity) {

        //订单编号
        if (!TextUtils.isEmpty(entity.getOrderNo())) {
            ll_info_no.setVisibility(View.VISIBLE);
            tv_info_no.setText(entity.getOrderNo());
        } else {
            ll_info_no.setVisibility(View.GONE);
        }

        //发布时间
        if (!TextUtils.isEmpty(entity.getPublishTime())) {
            ll_info_time.setVisibility(View.VISIBLE);
            tv_info_time.setText(entity.getPublishTime());
        } else {
            ll_info_time.setVisibility(View.GONE);
        }

        //服务名称
        ll_info_server_name.setVisibility(View.VISIBLE);
        tv_info_server_name.setText("案件委托");

        //订单状态
        if (!TextUtils.isEmpty(entity.getOrderStatusStr())) {
            ll_info_order_status.setVisibility(View.VISIBLE);
            tv_info_order_status.setText(entity.getOrderStatusStr());
        } else {
            ll_info_order_status.setVisibility(View.GONE);
        }

        //案件名称
        if (!TextUtils.isEmpty(entity.getTitle())) {
            ll_info_name.setVisibility(View.VISIBLE);
            tv_info_name.setText(entity.getTitle());
        } else {
            ll_info_name.setVisibility(View.GONE);
        }

        //服务地域
        if (!TextUtils.isEmpty(entity.getRname())) {
            ll_info_area.setVisibility(View.VISIBLE);
            tv_info_area.setText(entity.getRname());
        } else {
            ll_info_area.setVisibility(View.GONE);
        }

        //案件类型
        if (!TextUtils.isEmpty(entity.getCaseTypeName())) {
            ll_info_entrust_type.setVisibility(View.VISIBLE);
            tv_info_entrust_type.setText(entity.getCaseTypeName());
        } else {
            ll_info_entrust_type.setVisibility(View.GONE);
        }

        //代理模式
        if (!TextUtils.isEmpty(entity.getProcurationStr())) {
            ll_info_model.setVisibility(View.VISIBLE);
            tv_info_model.setText(entity.getProcurationStr());
        } else {
            ll_info_model.setVisibility(View.GONE);
        }

        //涉案金额
        if (!TextUtils.isEmpty(entity.getAmountStr2())) {
            ll_info_price_bidding.setVisibility(View.VISIBLE);
            tv_info_price_bidding.setText(entity.getAmountStr2());
        } else {
            ll_info_price_bidding.setVisibility(View.GONE);
        }

        //愿意支付的律师费
        if (!TextUtils.isEmpty(entity.getLawyerAmountStr2())) {
            ll_info_price_agent.setVisibility(View.VISIBLE);
            tv_info_price_agent.setText(entity.getLawyerAmountStr2());
        } else {
            ll_info_price_agent.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(entity.getDescription())) {
            ll_info_entrust_info.setVisibility(View.VISIBLE);
            tv_info_entrust_info.setText(entity.getDescription());
        } else {
            tv_info_entrust_info.setVisibility(View.GONE);
        }

        setStatus(entity);

        setLawyerLayout(entity.getLawyerMemberId(), entity.getLmemberName(), entity.getLMemeberName2(), entity.getLiconImage());
    }

    public void setStatus(EntrustListEntity entity) {
        //orderStatus	1待审核，2待发布，3未通过，4待确认，6已确认，7待打款，8已打款，9已完成，10已关闭，11已删除
        int orderStatus = entity.getOrderStatus();

        if(orderStatus == 2 || orderStatus == 4){
            setOrderDetailView(1);
        }else if(orderStatus == 6){
            setOrderDetailView(2);
        }else if(orderStatus == 7 || orderStatus == 8 || orderStatus == 9){
            setOrderDetailView(3);
        }else{
            setOrderDetailView(0);
        }
    }

    public void setLawyerLayout(int id, String name, String nameContent, String headUrl) {
        if (id > 0) {
            groupLawyer.setVisibility(View.VISIBLE);
            ivHeadTip.setVisibility(View.VISIBLE);
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
                bundle.clear();
                bundle.putInt(BundleTags.ID, id);
                launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
            });
        } else {
            groupLawyer.setVisibility(View.GONE);
            ivHeadTip.setVisibility(View.GONE);
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
