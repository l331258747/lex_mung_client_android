package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.component.DaggerTradingListComponent;
import com.lex_mung.client_android.di.module.TradingListModule;
import com.lex_mung.client_android.mvp.contract.TradingListContract;
import com.lex_mung.client_android.mvp.model.entity.TradingListEntity;
import com.lex_mung.client_android.mvp.ui.adapter.TradingListAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import com.lex_mung.client_android.mvp.presenter.TradingListPresenter;

import com.lex_mung.client_android.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class TradingListActivity extends BaseActivity<TradingListPresenter> implements TradingListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private TradingListAdapter tradingListAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTradingListComponent
                .builder()
                .appComponent(appComponent)
                .tradingListModule(new TradingListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_trading_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getTradingList(false);
        initAdapter();
        initRecyclerView();
        tradingListAdapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    private void initAdapter() {
        tradingListAdapter = new TradingListAdapter();
        tradingListAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            TradingListEntity.ListBean bean = tradingListAdapter.getItem(position);
            if (bean == null) return;
            bundle.clear();
            bundle.putSerializable(BundleTags.ENTITY, bean);
            launchActivity(new Intent(mActivity, TradingListDetailsActivity.class), bundle);
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                    mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                    mPresenter.getTradingList(true);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.setPageNum(1);
                mPresenter.getTradingList(false);
            }
        });
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(tradingListAdapter);
    }

    @Override
    public void setAdapter(List<TradingListEntity.ListBean> list, boolean isAdd) {
        if (isAdd) {
            tradingListAdapter.addData(list);
            smartRefreshLayout.finishLoadMore();
        } else {
            tradingListAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
            smartRefreshLayout.finishRefresh();
            tradingListAdapter.setNewData(list);
            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
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
