package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.MyLikeModule;
import cn.lex_mung.client_android.mvp.model.entity.MyLikeEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyLikeAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerMyLikeComponent;
import cn.lex_mung.client_android.mvp.contract.MyLikeContract;
import cn.lex_mung.client_android.mvp.presenter.MyLikePresenter;

import cn.lex_mung.client_android.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import javax.inject.Inject;

public class MyLikeActivity extends BaseActivity<MyLikePresenter> implements MyLikeContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private MyLikeAdapter myLikeAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyLikeComponent
                .builder()
                .appComponent(appComponent)
                .myLikeModule(new MyLikeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_like;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getMyLikeList(false);
        initAdapter();
        initRecyclerView();
        myLikeAdapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    private void initAdapter() {
        myLikeAdapter = new MyLikeAdapter(mImageLoader);
        myLikeAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            MyLikeEntity.ListBean bean = myLikeAdapter.getItem(position);
            if (bean == null) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, bean.getMemberId());
            launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                    mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                    mPresenter.getMyLikeList(true);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.setPageNum(1);
                mPresenter.getMyLikeList(false);
            }
        });

        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(myLikeAdapter);
    }

    @Override
    public void setAdapter(List<MyLikeEntity.ListBean> list, boolean isAdd) {
        if (isAdd) {
            myLikeAdapter.addData(list);
            smartRefreshLayout.finishLoadMore();
        } else {
            myLikeAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
            smartRefreshLayout.finishRefresh();
            myLikeAdapter.setNewData(list);
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
