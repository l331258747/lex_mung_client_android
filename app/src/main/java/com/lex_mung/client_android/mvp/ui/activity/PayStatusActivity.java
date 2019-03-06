package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.module.PayStatusModule;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

import com.lex_mung.client_android.di.component.DaggerPayStatusComponent;
import com.lex_mung.client_android.mvp.contract.PayStatusContract;
import com.lex_mung.client_android.mvp.presenter.PayStatusPresenter;

import com.lex_mung.client_android.R;
import com.tencent.mm.opensdk.modelbase.BaseResp;

public class PayStatusActivity extends BaseActivity<PayStatusPresenter> implements PayStatusContract.View {
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayStatusComponent
                .builder()
                .appComponent(appComponent)
                .payStatusModule(new PayStatusModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay_status;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent.containsKey(BundleTags.WX)) {
            if (bundleIntent.getInt(BundleTags.WX) == BaseResp.ErrCode.ERR_USER_CANCEL) {//用户取消
                showFailLayout(AppUtils.getString(mActivity, R.string.text_pay_cancel));
                return;
            }
        }
        if (bundleIntent.containsKey(BundleTags.ZFB)) {
            if ("6001".equals(bundleIntent.getString(BundleTags.ZFB))) {//用户取消
                showFailLayout(AppUtils.getString(mActivity, R.string.text_pay_cancel));
                return;
            }
        }
        mPresenter.checkOrderStatus(bundleIntent.getString(BundleTags.ORDER_NO));
    }

    @Override
    public void showSuccessLayout() {
        ivIcon.setImageResource(R.drawable.ic_pay_success);
        switch (DataHelper.getIntergerSF(mActivity, "PAY_TYPE")) {
            case 1:
                tvStatus.setText(AppUtils.getString(mActivity, R.string.text_pay_success));
                break;
        }
        tvStatus.setTextColor(AppUtils.getColor(mActivity, R.color.c_06a66a));
    }

    @Override
    public void showFailLayout(String s) {
        ivIcon.setImageResource(R.drawable.ic_pay_failure);
        tvStatus.setText(s);
        tvStatus.setTextColor(AppUtils.getColor(mActivity, R.color.c_ea5514));
    }

    @OnClick(R.id.bt_back)
    public void onViewClicked() {
        if (isFastClick()) return;
        killMyself();
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
