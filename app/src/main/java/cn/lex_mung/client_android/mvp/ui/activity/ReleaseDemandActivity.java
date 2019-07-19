package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerReleaseDemandComponent;
import cn.lex_mung.client_android.di.module.ReleaseDemandModule;
import cn.lex_mung.client_android.mvp.contract.ReleaseDemandContract;
import cn.lex_mung.client_android.mvp.model.entity.AmountBalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrgAmountEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.PayTypeEntity;
import cn.lex_mung.client_android.mvp.presenter.ReleaseDemandPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandServiceTypeAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.PayTypeView2;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class ReleaseDemandActivity extends BaseActivity<ReleaseDemandPresenter> implements ReleaseDemandContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.tv_lawyer_region)
    TextView tvLawyerRegion;
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

    private EasyDialog easyDialog;
    private DefaultDialog defaultDialog;

    private int regionId;
    private int lawyerId;
    private int requireTypeId;

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
                    requireTypeId = bundleIntent.getInt(BundleTags.ID),
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

        mPresenter.getAllBalance();

        payTypeView2.setItemOnClick((type,type6Id) -> {
            mPresenter.setPayType(type,type6Id);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initRecyclerView(ReleaseDemandServiceTypeAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.view_discount_way, R.id.tv_fast_consult_tip, R.id.tv_fast_consult_tip_1, R.id.bt_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_discount_way:
                if(mPresenter.getPayMoney() <= 0){
                    showMessage("请选择服务类型");
                    return;
                }

                bundle.clear();
                bundle.putInt(BundleTags.MEMBER_ID, mPresenter.getUserInfoDetailsEntity().getMemberId());
                bundle.putInt(BundleTags.L_MEMBER_ID, mPresenter.getLawsHomePagerBaseEntityId());

                bundle.putInt(BundleTags.COUPON_TYPE,mPresenter.getCouponType());
                bundle.putInt(BundleTags.ORG_ID, mPresenter.getOrganizationId());
                bundle.putInt(BundleTags.COUPON_ID, mPresenter.getCouponId());
                bundle.putInt(BundleTags.REQUIRE_TYPE_ID,mPresenter.getRequireTypeId());
                launchActivity(new Intent(mActivity, CouponModeActivity.class), bundle);
                break;
            case R.id.tv_fast_consult_tip:
            case R.id.tv_fast_consult_tip_1:
                mPresenter.tariffExplanationUrl();
                break;
            case R.id.bt_pay:

                switch (requireTypeId){
                    case 9:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_offline_page","meeting_offline_page_pay_click");
                        break;
                    case 2:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_page","litigation_arbitration_page_pay_click");
                        break;
                    case 6:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "legal_counsel_page","legal_counsel_page_pay_click");
                        break;
                    case 3:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "draw_contract_page","draw_contract_page_pay_click");
                        break;
                    case 4:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "lawyers_letter_page","lawyers_letter_page_pay_click");
                        break;
                    case 30:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "review_of_contracts_page","review_of_contracts_page_pay_click");
                        break;
                }

                mPresenter.releaseRequirement(webView.getSettings().getUserAgentString()
                        , etMaxMoney.getText().toString()
                        , etProblemDescription.getText().toString());
                break;
        }
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
    public void setDiscountWay(String organizationName) {
        tvDiscountWay.setText(organizationName);
    }

    @Override
    public void setAllBalance(AmountBalanceEntity balanceEntity) {
        List<PayTypeEntity> list = new ArrayList<>();
        if (balanceEntity.getAmount() != null) {
            PayTypeEntity entity = new PayTypeEntity();
            entity.setIcon(R.drawable.ic_pay_balance2);
            entity.setTitle("账户余额");
            entity.setType(3);
            entity.setSelected(false);
            entity.setBalance(balanceEntity.getAmount().getBalanceAmount());
            list.add(entity);
        }

        if (balanceEntity.getMemberCard() != null) {
            PayTypeEntity payTypeEntity = new PayTypeEntity();
            payTypeEntity.setIcon(R.drawable.ic_pay_club_card);
            payTypeEntity.setTitle("会员卡");
            payTypeEntity.setType(4);
            payTypeEntity.setSelected(false);
            payTypeEntity.setBalance(balanceEntity.getMemberCard().getAmountNew());
            list.add(payTypeEntity);
        }

        if (balanceEntity.getOrgAmounts() != null && balanceEntity.getOrgAmounts().size() > 0){
            for (int i = 0; i < balanceEntity.getOrgAmounts().size(); i++) {
                PayTypeEntity entity = new PayTypeEntity();
                entity.setIcon(R.drawable.ic_pay_group);
                entity.setTitle(balanceEntity.getOrgAmounts().get(i).getCouponName());
                entity.setType(6);
                entity.setSelected(false);
                entity.setBalance(balanceEntity.getOrgAmounts().get(i).getAmount());
                entity.setGroupId(balanceEntity.getOrgAmounts().get(i).getCouponId());
                list.add(entity);
            }
        }
        payTypeView2.setPayTYpeData(list);
        setPayTypeViewSelect(mPresenter.getPayMoney());
    }

    @Override
    public void setPayTypeViewSelect(double money) {
        payTypeView2.setSelect(money);
    }

    public double getTypeBalance(int payType,int payTypeGroup){
        return payTypeView2.getTypeBalance(payType,payTypeGroup);
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
        });
        wpConsultType.setSelectedItemPosition(0);


        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
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
