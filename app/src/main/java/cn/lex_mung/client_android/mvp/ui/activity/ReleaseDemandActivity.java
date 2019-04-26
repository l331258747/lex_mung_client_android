package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerReleaseDemandComponent;
import cn.lex_mung.client_android.di.module.ReleaseDemandModule;
import cn.lex_mung.client_android.mvp.contract.ReleaseDemandContract;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.presenter.ReleaseDemandPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandServiceTypeAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class ReleaseDemandActivity extends BaseActivity<ReleaseDemandPresenter> implements ReleaseDemandContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_lawyer_region)
    TextView tvLawyerRegion;
    @BindView(R.id.tv_lawyer_field)
    TextView tvLawyerField;
    @BindView(R.id.group_money)
    Group groupMoney;
    @BindView(R.id.et_max_money)
    EditText etMaxMoney;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.group_problem_description)
    Group groupProblemDescription;
    @BindView(R.id.group_pay)
    Group groupPay;
    @BindView(R.id.et_problem_description)
    EditText etProblemDescription;
    @BindView(R.id.iv_select_wx)
    ImageView ivSelectWx;
    @BindView(R.id.iv_select_zfb)
    ImageView ivSelectZfb;
    @BindView(R.id.tv_balance_count)
    TextView tvBalanceCount;
    @BindView(R.id.iv_select_balance)
    ImageView ivSelectBalance;
    @BindView(R.id.tv_club_card_count)
    TextView tvClubCardCount;
    @BindView(R.id.iv_select_club_card)
    ImageView ivSelectClubCard;
    @BindView(R.id.tv_discount_way)
    TextView tvDiscountWay;
    @BindView(R.id.tv_fast_consult_tip)
    TextView tvFastConsultTip;
    @BindView(R.id.tv_order_money_text)
    TextView tvOrderMoneyText;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_discount_money)
    TextView tvDiscountMoney;
    @BindView(R.id.loading_view)
    LinearLayout llLoading;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.cl_club_card)
    ConstraintLayout clClubCard;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.group_lawyer_field)
    Group groupLawyerField;

    private EasyDialog easyDialog;
    private DefaultDialog defaultDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerReleaseDemandComponent
                .builder()
                .appComponent(appComponent)
                .releaseDemandModule(new ReleaseDemandModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_release_demand;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setType(bundleIntent.getInt(BundleTags.TYPE));
            mPresenter.onCreate((LawsHomePagerBaseEntity) bundleIntent.getSerializable(BundleTags.ENTITY), bundleIntent.getInt(BundleTags.ID));
            tvTitle.setText(bundleIntent.getString(BundleTags.TITLE));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserBalance();
    }

    @Override
    public void initRecyclerView(ReleaseDemandServiceTypeAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.view_lawyer_field, R.id.tv_wx, R.id.tv_zfb, R.id.tv_balance, R.id.tv_club_card, R.id.view_discount_way, R.id.tv_fast_consult_tip, R.id.tv_fast_consult_tip_1, R.id.bt_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_lawyer_field:
                showSelectFieldDialog();
                break;
            case R.id.tv_wx:
                ivSelectWx.setImageResource(R.drawable.ic_show_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                ivSelectBalance.setImageResource(R.drawable.ic_hide_select);
                ivSelectClubCard.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(1);
                break;
            case R.id.tv_zfb:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_show_select);
                ivSelectBalance.setImageResource(R.drawable.ic_hide_select);
                ivSelectClubCard.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(2);
                break;
            case R.id.tv_balance:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                ivSelectBalance.setImageResource(R.drawable.ic_show_select);
                ivSelectClubCard.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(3);
                break;
            case R.id.tv_club_card:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                ivSelectBalance.setImageResource(R.drawable.ic_hide_select);
                ivSelectClubCard.setImageResource(R.drawable.ic_show_select);
                mPresenter.setPayType(4);
                break;
            case R.id.view_discount_way:
                bundle.clear();
                bundle.putInt(BundleTags.ID, mPresenter.getOrganizationLevId());
                bundle.putInt(BundleTags.MEMBER_ID, mPresenter.getUserInfoDetailsEntity().getMemberId());
                bundle.putInt(BundleTags.L_MEMBER_ID, mPresenter.getLawsHomePagerBaseEntity().getMemberId());
                launchActivity(new Intent(mActivity, DiscountWayActivity.class), bundle);
                break;
            case R.id.tv_fast_consult_tip:
            case R.id.tv_fast_consult_tip_1:
                mPresenter.tariffExplanationUrl();
                break;
            case R.id.bt_pay:
                MobclickAgent.onEvent(mActivity, "w_y__shouye_jjfa_list_fbxqqr");
                mPresenter.releaseRequirement(webView.getSettings().getUserAgentString()
                        , etMaxMoney.getText().toString()
                        , etProblemDescription.getText().toString());
                break;
        }
    }

    @Override
    public void hideFieldLayout() {
        groupLawyerField.setVisibility(View.GONE);
    }

    @Override
    public void setTip(String string) {
        tvFastConsultTip.setText(Html.fromHtml(string));
    }

    @Override
    public void setRegion(String region) {
        tvLawyerRegion.setText(region);
    }

    @Override
    public void showProblemDescriptionLayout() {
        btPay.setText(R.string.text_release_demand);
        groupMoney.setVisibility(View.VISIBLE);
        groupProblemDescription.setVisibility(View.VISIBLE);
        groupPay.setVisibility(View.GONE);
        tvOrderMoney.setVisibility(View.INVISIBLE);
        tvOrderMoneyText.setText(getString(R.string.text_price_negotiation));
        tvOrderMoneyText.setTextColor(AppUtils.getColor(mActivity, R.color.c_ea5514));
    }

    @Override
    public void showPayLayout() {
        btPay.setText(R.string.text_pay_order);
        groupMoney.setVisibility(View.GONE);
        groupProblemDescription.setVisibility(View.GONE);
        groupPay.setVisibility(View.VISIBLE);
        tvOrderMoney.setVisibility(View.VISIBLE);
        tvOrderMoneyText.setText(getString(R.string.text_order_money));
        tvOrderMoneyText.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
    }

    @Override
    public void hideLoadingLayout() {
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void setOrderMoney(String money) {
        tvOrderMoney.setText(money);
    }

    @Override
    public void setDiscountMoney(String money) {
        tvDiscountMoney.setText(money);
        tvDiscountMoney.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDiscountMoney() {
        tvDiscountMoney.setVisibility(View.GONE);
    }

    @Override
    public void setBalance(String balance) {
        tvBalanceCount.setText(balance);
    }

    @Override
    public void setDiscountWay(String organizationName) {
        tvDiscountWay.setText(organizationName);
    }

    @Override
    public void setClubCardBalance(String money) {
        tvClubCardCount.setText(money);
        clClubCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideClubCardBalance() {
        clClubCard.setVisibility(View.GONE);
    }

    /**
     * 选择问题类型
     */
    @SuppressLint("InflateParams")
    private void showSelectFieldDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> {
            mPresenter.setLawyerField(data.toString());
            mPresenter.setLawyerFieldPosition(position);
        });
        wpConsultType.setData(mPresenter.getFieldList());
        wpConsultType.setSelectedItemPosition(0);

        mPresenter.setLawyerFieldPosition(0);
        mPresenter.setLawyerField(mPresenter.getFieldList().get(0));

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvLawyerField.setText(mPresenter.getLawyerField());
            dismiss();
        });
    }

    /**
     * 初始化dialog
     */
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

    /**
     * 关闭dialog
     */
    private void dismiss() {
        if (easyDialog != null) {
            easyDialog.dismiss();
        }
    }

    @Override
    public void showLackOfBalanceDialog() {
        new DefaultDialog(mActivity, dialog -> launchActivity(new Intent(mActivity, MyAccountActivity.class))
                , getString(R.string.text_lack_of_balance)
                , getString(R.string.text_leave_for_top_up)
                , getString(R.string.text_cancel))
                .show();
    }

    @Override
    public void showToAppInfoDialog() {
        if (defaultDialog == null) {
            defaultDialog = new DefaultDialog(mActivity, dialog -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
                startActivity(intent);
                dialog.dismiss();
            }
                    , getString(R.string.text_manual_open_permissions)
                    , getString(R.string.text_to_open)
                    , getString(R.string.text_cancel));
        }
        if (!defaultDialog.isShowing()) {
            defaultDialog.show();
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

    @Override
    public Activity getActivity() {
        return this;
    }
}