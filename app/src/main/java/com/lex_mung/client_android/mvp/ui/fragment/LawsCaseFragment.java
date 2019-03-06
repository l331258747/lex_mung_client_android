package com.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import com.lex_mung.client_android.di.component.DaggerLawsCaseComponent;
import com.lex_mung.client_android.di.module.LawsCaseModule;
import com.lex_mung.client_android.mvp.contract.LawsCaseContract;
import com.lex_mung.client_android.mvp.presenter.LawsCasePresenter;

import com.lex_mung.client_android.R;

public class LawsCaseFragment extends BaseFragment<LawsCasePresenter> implements LawsCaseContract.View {

    public static LawsCaseFragment newInstance(int memberId, String institutionName) {
        LawsCaseFragment fragment = new LawsCaseFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLawsCaseComponent
                .builder()
                .appComponent(appComponent)
                .lawsCaseModule(new LawsCaseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_laws_case, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

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

    }
}
