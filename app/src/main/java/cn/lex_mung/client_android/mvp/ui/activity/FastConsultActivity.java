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
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.di.component.DaggerFastConsultComponent;
import cn.lex_mung.client_android.di.module.FastConsultModule;
import cn.lex_mung.client_android.mvp.contract.FastConsultContract;
import cn.lex_mung.client_android.mvp.presenter.FastConsultPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DeviceUtils;

public class FastConsultActivity extends BaseActivity<FastConsultPresenter> implements FastConsultContract.View {

    @BindView(R.id.tv_consult_type)
    TextView tvConsultType;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.et_contact_phone)
    EditText etContactPhone;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.iv_mr)
    ImageView ivMr;
    @BindView(R.id.iv_ms)
    ImageView ivMs;
    @BindView(R.id.iv_select_wx)
    ImageView ivSelectWx;
    @BindView(R.id.iv_select_zfb)
    ImageView ivSelectZfb;
    @BindView(R.id.tv_balance_count)
    TextView tvBalanceCount;
    @BindView(R.id.iv_select_balance)
    ImageView ivSelectBalance;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_change_phone_number)
    TextView tvChangePhoneNumber;
    @BindView(R.id.tv_fast_consult_tip)
    TextView tvFastConsultTip;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.group_count)
    Group groupCount;
    @BindView(R.id.tv_count_1)
    TextView tvCount1;
    @BindView(R.id.tv_count_2)
    TextView tvCount2;
    @BindView(R.id.tv_count_3)
    TextView tvCount3;
    @BindView(R.id.tv_count_4)
    TextView tvCount4;
    @BindView(R.id.tv_count_5)
    TextView tvCount5;
    @BindView(R.id.tv_count_6)
    TextView tvCount6;

    private DefaultDialog defaultDialog;
    private EasyDialog easyDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFastConsultComponent
                .builder()
                .appComponent(appComponent)
                .fastConsultModule(new FastConsultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fast_consult;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("w_y_shouye_kszx_detail");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("w_y_shouye_kszx_detail");
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate();
        tvOrderMoney.setText(String.format(
                AppUtils.getString(mActivity, R.string.text_yuan_money)
                , AppUtils.formatAmount(mActivity, 0)));
        String string = getString(R.string.text_fast_consult_tip_1)
                + "<font color=\"#1EC88C\">"
                + getString(R.string.text_lex_transaction_process)
                + "</font>";
        tvFastConsultTip.setText(Html.fromHtml(string));
        groupCount.setVisibility(View.GONE);
    }

    @Override
    public void setPhone(String mobile) {
        etContactPhone.setText(mobile);
    }

    @Override
    public void setBalance(String balance) {
        tvBalanceCount.setText(balance);
    }

    @Override
    public void setOrderMoney(String money) {
        tvOrderMoney.setText(money);
    }

    @Override
    public void setMoney(String money) {
        tvMoney.setText(money);
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
    public void showLackOfBalanceDialog() {
        new DefaultDialog(mActivity, dialog -> {
            MobclickAgent.onEvent(mActivity, "w_y_shouye_kszx_detail_chongzhi");
            launchActivity(new Intent(mActivity, MyAccountActivity.class));
        }
                , getString(R.string.text_lack_of_balance)
                , getString(R.string.text_leave_for_top_up)
                , getString(R.string.text_cancel))
                .show();
    }

    @OnClick({
            R.id.view_consult_type
            , R.id.tv_wx
            , R.id.tv_zfb
            , R.id.tv_balance
            , R.id.bt_pay
            , R.id.tv_change_phone_number
            , R.id.tv_fast_consult_tip
            , R.id.tv_fast_consult_tip_1
            , R.id.tv_ms
            , R.id.tv_mr
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fast_consult_tip:
            case R.id.tv_fast_consult_tip_1:
                mPresenter.tariffExplanationUrl();
                break;
            case R.id.view_consult_type:
                MobclickAgent.onEvent(mActivity, "w_y_shouye_kszx_detail_fenleixuanze");
                showSelectTypeDialog();
                break;
            case R.id.tv_mr:
                ivMr.setImageResource(R.drawable.ic_show_select);
                ivMs.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setSex(1);
                break;
            case R.id.tv_ms:
                ivMr.setImageResource(R.drawable.ic_hide_select);
                ivMs.setImageResource(R.drawable.ic_show_select);
                mPresenter.setSex(2);
                break;
            case R.id.tv_wx:
                ivSelectWx.setImageResource(R.drawable.ic_show_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                ivSelectBalance.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(1);
                break;
            case R.id.tv_zfb:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_show_select);
                ivSelectBalance.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(2);
                break;
            case R.id.tv_balance:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                ivSelectBalance.setImageResource(R.drawable.ic_show_select);
                mPresenter.setPayType(3);
                break;
            case R.id.bt_pay:
                MobclickAgent.onEvent(mActivity, "w_y_shouye_kszx_detail_kszxtj");
                mPresenter.pay(etUserName.getText().toString(), webView.getSettings().getUserAgentString());
                break;
            case R.id.tv_change_phone_number:
                if (getString(R.string.text_change_phone_number).contentEquals(tvChangePhoneNumber.getText())) {
                    etContactPhone.setText("");
                    etContactPhone.requestFocus();
                    DeviceUtils.openSoftKeyboard(etContactPhone);
                    tvChangePhoneNumber.setText(R.string.text_registered_phone_number);
                } else {
                    etContactPhone.setText(mPresenter.getMobile());
                    DeviceUtils.hideSoftKeyboard(etContactPhone);
                    tvChangePhoneNumber.setText(R.string.text_change_phone_number);
                }
                break;
        }
    }

    /**
     * 选择问题类型
     */
    @SuppressLint("InflateParams")
    private void showSelectTypeDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> {
            mPresenter.setConsultType(data.toString());
            mPresenter.setConsultTypePos(position);
            mPresenter.setMoney(position);
        });
        wpConsultType.setData(mPresenter.getSolutionTypeStringList());
        wpConsultType.setSelectedItemPosition(0);
        mPresenter.setMoney(0);
        mPresenter.setConsultType(mPresenter.getSolutionTypeStringList().get(0));
        mPresenter.setConsultTypePos(0);
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvConsultType.setText(mPresenter.getConsultType());
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public void killMyself() {
        finish();
    }
}
