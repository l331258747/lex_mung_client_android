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

import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import com.lex_mung.client_android.di.component.DaggerLawsBusinessCardComponent;
import com.lex_mung.client_android.di.module.LawsBusinessCardModule;
import com.lex_mung.client_android.mvp.contract.LawsBusinessCardContract;
import com.lex_mung.client_android.mvp.presenter.LawsBusinessCardPresenter;

import com.lex_mung.client_android.R;

public class LawsBusinessCardFragment extends BaseFragment<LawsBusinessCardPresenter> implements LawsBusinessCardContract.View {

    public static LawsBusinessCardFragment newInstance(LawsHomePagerBaseEntity entity) {
        LawsBusinessCardFragment fragment = new LawsBusinessCardFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLawsBusinessCardComponent
                .builder()
                .appComponent(appComponent)
                .lawsBusinessCardModule(new LawsBusinessCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_laws_business_card, container, false);
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
