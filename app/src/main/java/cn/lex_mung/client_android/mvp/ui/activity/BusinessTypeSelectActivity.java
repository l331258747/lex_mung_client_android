package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.BusinessTypeSelectModule;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeBean;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeChildBean;
import cn.lex_mung.client_android.mvp.ui.adapter.HelpStep2Adapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.HelpStep2View;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerBusinessTypeSelectComponent;
import cn.lex_mung.client_android.mvp.contract.BusinessTypeSelectContract;
import cn.lex_mung.client_android.mvp.presenter.BusinessTypeSelectPresenter;

import cn.lex_mung.client_android.R;

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_1;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_BUSINESS;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;

public class BusinessTypeSelectActivity extends BaseActivity<BusinessTypeSelectPresenter> implements BusinessTypeSelectContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.view_help_step_2)
    HelpStep2View helpStep2View;

    HelpStep2Adapter adapter;

    int typeId = -1;
    boolean flag;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessTypeSelectComponent
                .builder()
                .appComponent(appComponent)
                .businessTypeSelectModule(new BusinessTypeSelectModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_type_select;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setList((List<SolutionTypeBean>) bundleIntent.getSerializable(BundleTags.LIST));
        typeId = bundleIntent.getInt(BundleTags.ID, -1);
        flag = bundleIntent.getBoolean(BundleTags.FLAG, false);

        initAdapter();
        initRecyclerView();

        mPresenter.getTabPosition(0);

        helpStep2View.setItemOnClick(position -> {
            mPresenter.getTabPosition(position);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initAdapter() {
        adapter = new HelpStep2Adapter();
        adapter.setSelection(typeId);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;

            typeId = adapter.getItem(position).getSolutionTypeId();
            adapter.setSelection(typeId);
            if (flag) {
                AppUtils.post(LAWYER_LIST_SCREEN_INFO_1, LAWYER_LIST_SCREEN_INFO_BUSINESS, adapter.getItem(position));
            } else {
                AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_BUSINESS, adapter.getItem(position));
            }
            killMyself();
        });
    }

    @Override
    public void setAdapter(List<SolutionTypeChildBean> list) {
        adapter.setNewData(list);
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
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
