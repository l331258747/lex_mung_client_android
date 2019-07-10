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
import android.support.v4.content.ContextCompat;
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
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerReleaseDemandComponent;
import cn.lex_mung.client_android.di.module.ReleaseDemandModule;
import cn.lex_mung.client_android.mvp.contract.ReleaseDemandContract;
import cn.lex_mung.client_android.mvp.model.entity.OrgAmountEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.PayTypeEntity;
import cn.lex_mung.client_android.mvp.presenter.ReleaseDemandPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandServiceTypeAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.PayTypeView2;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class ReleaseDemandActivity extends BaseActivity<ReleaseDemandPresenter> implements ReleaseDemandContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.tv_lawyer_region)
    TextView tvLawyerRegion;
    //    @BindView(R.id.tv_lawyer_field)
//    TextView tvLawyerField;
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

    @BindView(R.id.payTypeView2)
    PayTypeView2 payTypeView2;

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
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.bt_pay)
    Button btPay;
//    @BindView(R.id.group_lawyer_field)
//    Group groupLawyerField;

    private EasyDialog easyDialog;
    private DefaultDialog defaultDialog;

    private int regionId;
    private int lawyerId;

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
        return R.layout.activity_release_demand2;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setType(bundleIntent.getInt(BundleTags.TYPE));
            mPresenter.onCreate(
                    lawyerId = bundleIntent.getInt(BundleTags.MEMBER_ID),
                    bundleIntent.getString(BundleTags.REGION),
                    regionId = bundleIntent.getInt(BundleTags.REGION_ID),
                    bundleIntent.getInt(BundleTags.ID),
                    bundleIntent.getString(BundleTags.TITLE));
            titleView.setTitle(bundleIntent.getString(BundleTags.TITLE));
            if(bundleIntent.getInt(BundleTags.TYPE) == 2){
                titleView.getRightTv().setVisibility(View.VISIBLE);
                titleView.getRightTv().setText("历史需求");
                titleView.getRightTv().setTextColor(ContextCompat.getColor(mActivity,R.color.c_1EC78A));
                titleView.getRightTv().setOnClickListener(v -> {
                    bundle.clear();
                    bundle.putInt(BundleTags.MEMBER_ID,lawyerId);
                    bundle.putInt(BundleTags.REGION_ID,regionId);
                    launchActivity(new Intent(mActivity,ReleaseDemandHistoryActivity.class),bundle);
                });
            }
        }

        mPresenter.getUserBalance();
        mPresenter.getGroupBalance();

        payTypeView2.setItemOnClick((type,type6Id) -> {
            mPresenter.setPayType(type,type6Id);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenter.getUserBalance();
    }

    @Override
    public void initRecyclerView(ReleaseDemandServiceTypeAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    //    @OnClick({R.id.view_lawyer_field, R.id.tv_wx, R.id.tv_zfb, R.id.tv_balance, R.id.tv_club_card, R.id.view_discount_way, R.id.tv_fast_consult_tip, R.id.tv_fast_consult_tip_1, R.id.bt_pay})
    @OnClick({R.id.view_discount_way, R.id.tv_fast_consult_tip, R.id.tv_fast_consult_tip_1, R.id.bt_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_discount_way:
                bundle.clear();
                bundle.putInt(BundleTags.ID, mPresenter.getOrganizationLevId());
                bundle.putInt(BundleTags.MEMBER_ID, mPresenter.getUserInfoDetailsEntity().getMemberId());
                bundle.putInt(BundleTags.L_MEMBER_ID, mPresenter.getLawsHomePagerBaseEntityId());

//                launchActivity(new Intent(mActivity, DiscountWayActivity.class), bundle);
                bundle.putInt(BundleTags.L_MEMBER_ID, mPresenter.getCouponId());
                launchActivity(new Intent(mActivity, CouponModeActivity.class), bundle);
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
//        groupLawyerField.setVisibility(View.GONE);
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
    public void setBalance(double balance) {
//        tvBalanceCount.setText(balance);

        PayTypeEntity entity = new PayTypeEntity();
        entity.setIcon(R.drawable.ic_pay_balance2);
        entity.setTitle("账户余额");
        entity.setType(3);
        entity.setSelected(false);
        entity.setBalance(balance);
        payTypeView2.addPayTypeData(entity);
    }

    @Override
    public void setDiscountWay(String organizationName) {
        tvDiscountWay.setText(organizationName);
    }

    boolean isClubCardBalance;
    @Override
    public void setClubCardBalance(double money) {
        if(isClubCardBalance) return;

        PayTypeEntity entity = new PayTypeEntity();
        entity.setIcon(R.drawable.ic_pay_club_card);
        entity.setTitle("会员卡");
        entity.setType(4);
        entity.setSelected(false);
        entity.setBalance(money);
        payTypeView2.addPayTypeData(entity);

        isClubCardBalance = true;
    }

    @Override
    public void setGroupBalance(List<OrgAmountEntity> list) {
        if(list == null || list.size() == 0)
            return;
        for (int i=0;i<list.size();i++){
            PayTypeEntity entity = new PayTypeEntity();
            entity.setIcon(R.drawable.ic_pay_group);
            entity.setTitle("集团卡余额");
            entity.setType(6);
            entity.setSelected(false);
            entity.setBalance(list.get(i).getAmount());
            entity.setGroupId(list.get(i).getCouponId());
            payTypeView2.addPayTypeData(entity);
        }
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
//            mPresenter.setLawyerField(data.toString());
//            mPresenter.setLawyerFieldPosition(position);
        });
//        wpConsultType.setData(mPresenter.getFieldList());
        wpConsultType.setSelectedItemPosition(0);

//        mPresenter.setLawyerFieldPosition(0);
//        mPresenter.setLawyerField(mPresenter.getFieldList().get(0));

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
//            tvLawyerField.setText(mPresenter.getLawyerField());
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
