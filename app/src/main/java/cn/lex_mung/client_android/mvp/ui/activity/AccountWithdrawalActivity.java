package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.AccountWithdrawalModule;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.mvp.ui.dialog.SingleTextDialog;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.CharacterHandler;

import cn.lex_mung.client_android.di.component.DaggerAccountWithdrawalComponent;
import cn.lex_mung.client_android.mvp.contract.AccountWithdrawalContract;
import cn.lex_mung.client_android.mvp.presenter.AccountWithdrawalPresenter;

import cn.lex_mung.client_android.R;

public class AccountWithdrawalActivity extends BaseActivity<AccountWithdrawalPresenter> implements AccountWithdrawalContract.View {
    @BindView(R.id.tv_remaining_amount)
    TextView tvRemainingAmount;
    @BindView(R.id.tv_withdrawal_amount)
    TextView tvWithdrawalAmount;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountWithdrawalComponent
                .builder()
                .appComponent(appComponent)
                .accountWithdrawalModule(new AccountWithdrawalModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_withdrawal;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setBalance(bundleIntent.getDouble(BundleTags.BALANCE_REAL),bundleIntent.getDouble(BundleTags.BALANCE_GIVE));

        etAccount.setFilters(new InputFilter[]{CharacterHandler.emojiFilter});
        etName.setFilters(new InputFilter[]{CharacterHandler.emojiFilter});
    }


    @Override
    public void setBalance(String balanceReal, String balanceGive) {
        String str = "(可用余额 %1$s 元，赠送余额 %2$s 元)";
        tvRemainingAmount.setText(String.format(str, balanceReal, balanceGive));
        tvWithdrawalAmount.setText(String.format(AppUtils.getString(mActivity, R.string.text_yuan_money), balanceReal));
    }

    @Override
    public void showSuccessDialog(String string) {
        new SingleTextDialog(mActivity).setSubmitStr("关闭").setContent(string).setOnClickListener(() -> killMyself()).show();
    }

    @OnClick({R.id.bt_withdrawal})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.bt_withdrawal:
                mPresenter.withdrawal(etName.getText().toString()
                        , etAccount.getText().toString()
                        , webView.getSettings().getUserAgentString());
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
}
