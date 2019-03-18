package com.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lex_mung.client_android.app.decoration.SpacesItemDecoration;
import com.lex_mung.client_android.mvp.ui.adapter.DemandMessageAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import com.lex_mung.client_android.di.component.DaggerDemandMessageComponent;
import com.lex_mung.client_android.di.module.DemandMessageModule;
import com.lex_mung.client_android.mvp.contract.DemandMessageContract;
import com.lex_mung.client_android.mvp.presenter.DemandMessagePresenter;

import com.lex_mung.client_android.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import javax.inject.Inject;

public class DemandMessageFragment extends BaseFragment<DemandMessagePresenter> implements DemandMessageContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private boolean isLoad = false;

    public static DemandMessageFragment newInstance() {
        return new DemandMessageFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDemandMessageComponent
                .builder()
                .appComponent(appComponent)
                .demandMessageModule(new DemandMessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demand_message, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate();
        isLoad = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoad) {
            mPresenter.getDemandMessageList();
        }
    }

    @Override
    public void initRecyclerView(DemandMessageAdapter adapter) {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.getDemandMessageList());
        smartRefreshLayout.finishLoadMoreWithNoMoreData();
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, AppUtils.dip2px(mActivity, AppUtils.getXmlDef(mContext, R.dimen.qb_px_1)), AppUtils.getColor(mActivity, R.color.c_b5b5b5)));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setAdapter(DemandMessageAdapter adapter) {
        smartRefreshLayout.finishRefresh();
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
    }

    public void refreshData() {
        try {
            mPresenter.getDemandMessageList();
        } catch (Exception ignored) {
        }
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
