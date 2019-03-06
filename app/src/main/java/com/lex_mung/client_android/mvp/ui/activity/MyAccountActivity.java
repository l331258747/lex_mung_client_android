package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.component.DaggerMyAccountComponent;
import com.lex_mung.client_android.di.module.MyAccountModule;
import com.lex_mung.client_android.mvp.contract.MyAccountContract;
import com.lex_mung.client_android.mvp.presenter.MyAccountPresenter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class MyAccountActivity extends BaseActivity<MyAccountPresenter> implements MyAccountContract.View {

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyAccountComponent
                .builder()
                .appComponent(appComponent)
                .myAccountModule(new MyAccountModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_account;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.TransactionListActivity));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserBalance();
    }

    @Override
    public void setBalance(String balance) {
        tvBalance.setText(balance);
    }


    @OnClick({R.id.tv_right,R.id.bt_pay, R.id.bt_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                launchActivity(new Intent(mActivity, TradingListActivity.class));
                break;
            case R.id.bt_pay:
                launchActivity(new Intent(mActivity, AccountPayActivity.class));
                break;
            case R.id.bt_withdrawal:
                if (mPresenter.getBalance() > 0) {
                    bundle.clear();
                    bundle.putDouble(BundleTags.BALANCE, mPresenter.getBalance());
                    launchActivity(new Intent(mActivity, AccountWithdrawalActivity.class), bundle);
                } else {
                    showMessage(getString(R.string.text_no_withdrawal_amount));
                }
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
