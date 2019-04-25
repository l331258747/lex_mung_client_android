package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.RushLoanPayModule;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerRushLoanPayComponent;
import cn.lex_mung.client_android.mvp.contract.RushLoanPayContract;
import cn.lex_mung.client_android.mvp.presenter.RushLoanPayPresenter;

import cn.lex_mung.client_android.R;

/**
 * 抢单需求
 */
public class RushLoanPayActivity extends BaseActivity<RushLoanPayPresenter> implements RushLoanPayContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;

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
    @BindView(R.id.web_view)
    WebView webView;

    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.tv_order_money_text)
    TextView tvOrderMoneyText;

    private DefaultDialog defaultDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRushLoanPayComponent
                .builder()
                .appComponent(appComponent)
                .rushLoanPayModule(new RushLoanPayModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rush_loan_pay;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setRequireTypeId(bundleIntent.getInt(BundleTags.ID));
            mPresenter.setPayMoney(bundleIntent.getInt(BundleTags.MONEY));
            mPresenter.setRequireTypeName(bundleIntent.getString(BundleTags.TITLE));

            titleView.setTitle(bundleIntent.getString(BundleTags.TITLE));
            tvOrderMoney.setText(bundleIntent.getInt(BundleTags.MONEY) + "");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserBalance();
    }


    @OnClick({R.id.tv_wx, R.id.tv_zfb, R.id.tv_balance, R.id.tv_club_card, R.id.bt_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.tv_club_card:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                ivSelectBalance.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(4);
                break;
            case R.id.bt_pay:
//                MobclickAgent.onEvent(mActivity, "w_y__shouye_jjfa_list_fbxqqr");
                mPresenter.releaseRequirement(webView.getSettings().getUserAgentString());
                break;
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
        new DefaultDialog(mActivity, dialog -> launchActivity(new Intent(mActivity, MyAccountActivity.class))
                , getString(R.string.text_lack_of_balance)
                , getString(R.string.text_leave_for_top_up)
                , getString(R.string.text_cancel))
                .show();
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
    public void showPayLayout() {
        btPay.setText(R.string.text_pay_order);
        tvOrderMoney.setVisibility(View.VISIBLE);
        tvOrderMoneyText.setText(getString(R.string.text_order_money));
        tvOrderMoneyText.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
    }
}
