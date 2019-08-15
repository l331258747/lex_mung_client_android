package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerPhoneSubComponent;
import cn.lex_mung.client_android.di.module.PhoneSubModule;
import cn.lex_mung.client_android.mvp.contract.PhoneSubContract;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceSolutionEntity;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceTimeEntity;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertReserveEntity;
import cn.lex_mung.client_android.mvp.presenter.PhoneSubPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.PhoneSubAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.CallFieldDialog5;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.FieldDialog2;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.SingleTextDialog;
import cn.lex_mung.client_android.mvp.ui.widget.SimpleFlowLayout;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StatusBarUtil;
import me.zl.mvp.utils.StringUtils;

public class PhoneSubActivity extends BaseActivity<PhoneSubPresenter> implements PhoneSubContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_title_location)
    TextView tvTitleLocation;
    @BindView(R.id.iv_title_img)
    ImageView ivTitleImg;

    @BindView(R.id.sfl_table)
    SimpleFlowLayout sflTable;
    @BindView(R.id.group_table)
    Group groupTable;

    @BindView(R.id.tv_cost_content)
    TextView tvCostContent;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time_btn)
    TextView tvTimeBtn;

    @BindView(R.id.tv_tip_content)
    TextView tvTipContent;

    @BindView(R.id.view_bottom)
    View viewBottom;

    int timePosition = 0;
    PhoneSubAdapter adapter;

    ExpertPriceEntity entity;
    int time2sSubtimPosition = 0;//底部弹窗position

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPhoneSubComponent
                .builder()
                .appComponent(appComponent)
                .phoneSubModule(new PhoneSubModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_phone_sub;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_1EC88B), 0);
        titleView.getTitleTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_ff));

        entity = (ExpertPriceEntity) bundleIntent.getSerializable(BundleTags.ENTITY);
        if (entity == null) return;

        initViewData();
    }

    public void initViewData() {
        entity.setTalkTimes();

        tvTitleName.setText(entity.getLawyerName());
        tvTitleLocation.setText(entity.getCity());

        if (!TextUtils.isEmpty(entity.getIcon())) {
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(entity.getIcon())
                            .isCircle(true)
                            .imageView(ivTitleImg)
                            .build());

        } else {
            ivTitleImg.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_lawyer_avatar));
        }


        //擅长领域
        if (entity.getSolution() != null && entity.getSolution().size() > 0) {
            initTableLayout(entity.getSolution());
        } else {
            hideTableLayout();
        }

        tvCostContent.setText(entity.getPriceStr2());

        initTimeAdapter(entity.getTimeSection());

        setTvTimeBtn(entity.getMinimumDuration());

        String str3 = "1、预约咨询服务需提前预存咨询费用。<br>2、您发起预约后将默认冻结%s分钟的咨询费用，通话过程中，实际咨询费用如超过冻结费用时，系统将自行中断通话，如您预计通话时间会更长，请在上方点击修改冻结费用。<br>3、更多细则请查阅<font color=\"#27CB90\">《绿豆圈专家咨询细则》</font>";
        StringUtils.setHtml(tvTipContent,String.format(str3, entity.getMinimumDurationStr()));
    }

    public void setTvTimeBtn(int time) {
        String str = "预计咨询时长：" + time + "分钟。如预计通话更长，" + "<font color=\"#D89B4B\">请点我修改</font>";
        StringUtils.setHtml(tvTimeBtn, str);
    }

    public void initTimeAdapter(List<ExpertPriceTimeEntity> datas) {
        PhoneSubAdapter adapter = new PhoneSubAdapter();
        adapter.setPosition(timePosition);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            ExpertPriceTimeEntity entity = datas.get(position);
            if (entity == null) return;
            adapter.setPosition(timePosition = position);
        });

        AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mActivity, 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        adapter.setNewData(datas);
    }

    public void initTableLayout(List<ExpertPriceSolutionEntity> marks) {
        if (sflTable.getChildCount() > 0) {
            sflTable.removeViews(0, sflTable.getChildCount());
        }

        for (int i = 0; i < marks.size(); i++) {
            int pos = i;
            View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_table_phone_sub, null, false);
            TextView tvTitle = itemView.findViewById(R.id.item_tv_title);
            tvTitle.setText(marks.get(i).getSolutionTypeName());
            itemView.setOnClickListener(v -> {
                if (isFastClick()) return;
                ExpertPriceSolutionEntity bean = marks.get(pos);
                if (bean == null) return;
                new FieldDialog2(mActivity, bean, mImageLoader).show();
            });
            sflTable.addView(itemView, i);
        }
    }

    public void hideTableLayout() {
        groupTable.setVisibility(View.GONE);
    }


    @OnClick({R.id.tv_recharge, R.id.tv_call, R.id.tv_time_btn, R.id.tv_tip_content})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_recharge:
                bundle.clear();
                bundle.putBoolean(BundleTags.IS_EXPERT, true);
                launchActivity(new Intent(mActivity, MyAccountActivity.class), bundle);
                break;
            case R.id.tv_call:
                mPresenter.expertReserve(entity.getLawyerId(),
                        entity.getTimeSection().get(timePosition).getStart(),
                        entity.getTimeSection().get(timePosition).getEnd(),
                        entity.getTalkTimes().get(time2sSubtimPosition).getTime());
                break;
            case R.id.tv_time_btn:
                showSelectTypeDialog(entity.getTalkTimeStrs());
                break;
            case R.id.tv_tip_content:
                bundle.clear();
                bundle.putString(BundleTags.URL, entity.getAgreementUrl());
                bundle.putString(BundleTags.TITLE, "绿豆圈专家咨询细则");
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                break;
        }
    }


    //---------start 修改
    private EasyDialog easyDialog;
    private int time2SelectPosition = 0;//因为在选择的时候也会记录position ， 所以确定的时候还要一个变量来存选择的position

    @SuppressLint("InflateParams")
    private void showSelectTypeDialog(List<String> times2) {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> {
            time2SelectPosition = position;
        });
        wpConsultType.setData(times2);
        wpConsultType.setSelectedItemPosition(0);
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            setTvTimeBtn(entity.getTalkTimes().get(time2sSubtimPosition = time2SelectPosition).getTime());
            dismiss();
        });
    }

    private void initDialog(View layout) {
        easyDialog = new EasyDialog(this)
                .setLayout(layout)
                .setVisibility(View.GONE)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setBackgroundColor(AppUtils.getColor(mActivity, R.color.c_00))
                .setLocationByAttachedView(viewBottom)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 200, 1000, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 1000)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setMarginLeftAndRight(0, 0)
                .setOutsideColor(AppUtils.getColor(mActivity, R.color.c_50))
                .show();
    }

    private void dismiss() {
        if (easyDialog != null) {
            easyDialog.dismiss();
        }
    }

    @Override
    public void showToErrorDialog(String s) {
        new CallFieldDialog5(mActivity, dialog -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
            startActivity(dialIntent);
            dialog.dismiss();

        }, s, "联系客服").show();
    }

    //查询余额不足
    @Override
    public void showBalanceNoDialog() {
        new SingleTextDialog(mActivity)
                .setContent("咨询余额不足，请至少预存" + entity.getMinimumDuration() + "分钟中的咨询费用。")
                .setOnClickListener(() -> {
                    bundle.clear();
                    bundle.putBoolean(BundleTags.IS_EXPERT, true);
                    launchActivity(new Intent(mActivity, MyAccountActivity.class), bundle);
                })
                .setSubmitStr("去充值").show();
    }

    //查看余额充足
    @Override
    public void showBalanceYesDialog(ExpertReserveEntity entity) {
        new SingleTextDialog(mActivity)
                .setContentHtmlStr("预约成功，律师一般会在15分钟内确认订单，您可以进入<font color=\"#1EC88B\">我的-我的订单</font>页查看订单状态。")
                .setTextOnClickListener(() -> {
                    killMyself();
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getOrderId());
                    bundle.putString(BundleTags.TITLE,"专家咨询详情");
                    bundle.putString(BundleTags.ORDER_NO,entity.getOrderNo());
                    launchActivity(new Intent(mActivity,OrderDetailsExpertActivity.class),bundle);
                })
                .setOnClickListener(() -> {
                    killMyself();
                })
                .setSubmitStr("我知道了！").show();
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
