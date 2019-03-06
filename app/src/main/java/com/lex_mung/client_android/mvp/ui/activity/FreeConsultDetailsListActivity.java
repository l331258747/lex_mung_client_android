package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lex_mung.client_android.di.module.FreeConsultDetailsListModule;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import com.lex_mung.client_android.di.component.DaggerFreeConsultDetailsListComponent;
import com.lex_mung.client_android.mvp.contract.FreeConsultDetailsListContract;
import com.lex_mung.client_android.mvp.presenter.FreeConsultDetailsListPresenter;

import com.lex_mung.client_android.R;

public class FreeConsultDetailsListActivity extends BaseActivity<FreeConsultDetailsListPresenter> implements FreeConsultDetailsListContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultDetailsListComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultDetailsListModule(new FreeConsultDetailsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_details_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
