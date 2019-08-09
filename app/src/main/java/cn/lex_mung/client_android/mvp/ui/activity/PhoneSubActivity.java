package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerPhoneSubComponent;
import cn.lex_mung.client_android.di.module.PhoneSubModule;
import cn.lex_mung.client_android.mvp.contract.PhoneSubContract;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonMarkEntity;
import cn.lex_mung.client_android.mvp.presenter.PhoneSubPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeFreeAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.PhoneSubAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.SimpleFlowLayout;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
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

        tvTitleName.setText("律师姓名");
        tvTitleLocation.setText("长沙-岳麓区");
        ivTitleImg.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_lawyer_avatar));

        List<String> tables = new ArrayList<>();
        tables.add("法律速度快放假");
        tables.add("阿法狗");
        tables.add("阿斯蒂芬发");
        tables.add("按规定");
        initTableLayout(tables);

        String str2 = "%1$s，不足%2$s分钟按%3$s分钟计算，超过%4$s时按实际通话分钟数计算。";
        tvCostContent.setText(String.format(str2,"1.8元/分钟","30","30","30"));

        List<String> times = new ArrayList<>();
        times.add("未来24小时均可");
        times.add("今天09:00-13:00");
        times.add("今天13:00-17:00");
        times.add("今天17:00-21:00");
        initTimeAdapter(times);


        String str = "预计咨询时长：20分钟。如预计通话更长，" + "<font color=\"#D89B4B\">请点我修改</font>";
        StringUtils.setHtml(tvTimeBtn,str);

        String str3 = "1、预约咨询服务需提前预存咨询费用。\n2、您发起预约后将默认冻结%s分钟的咨询费用，通话过程中，实际咨询费用如超过冻结费用时，系统将自行中断通话，如您预计通话时间会更长，请在上方点击修改冻结费用。\n3、更多细则请查阅《绿豆圈专家咨询细则》";
        tvTipContent.setText(String.format(str3,"30"));

    }

    public void initTimeAdapter(List<String> datas) {
        PhoneSubAdapter adapter = new PhoneSubAdapter();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;

        });

        AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mActivity,2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        adapter.setNewData(datas);
    }

    public void initTableLayout(List<String> marks) {
        for (int i = 0; i < marks.size(); i++) {
            int pos = i;
            View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_table_phone_sub, null, false);
            TextView tvTitle = itemView.findViewById(R.id.item_tv_title);
            tvTitle.setText(marks.get(i));
            sflTable.addView(itemView, i);
        }
    }

    public void hideTableLayout() {
        groupTable.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_recharge, R.id.tv_call,R.id.tv_time_btn})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_recharge:
                showMessage("余额充值");
                break;
            case R.id.tv_call:
                showMessage("发起预约");
                break;
            case R.id.tv_time_btn:
                List<String> times2 = new ArrayList<>();
                times2.add("冻结20分钟咨询费用（60分钟）");
                times2.add("冻结30分钟咨询费用（90分钟）");
                times2.add("冻结40分钟咨询费用（120分钟）");
                times2.add("冻结50分钟咨询费用（150分钟）");
                showSelectTypeDialog(times2);
                break;
        }
    }


    //---------start 修改
    private EasyDialog easyDialog;
    private int time2Position;
    private String time2Name;
    @SuppressLint("InflateParams")
    private void showSelectTypeDialog(List<String> times2) {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> {
            time2Position = position;
            time2Name = times2.get(position);
            showMessage("position:" + position + ",times2:" + times2.get(position));
        });
        wpConsultType.setData(times2);
        wpConsultType.setSelectedItemPosition(0);

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            showMessage("position:" + time2Position + ",times2:" + time2Name);
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
