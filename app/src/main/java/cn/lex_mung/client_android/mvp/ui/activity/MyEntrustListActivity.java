package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.module.MyEntrustListModule;
import cn.lex_mung.client_android.mvp.ui.adapter.MyOrderAdapter2;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.EmptyView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerMyEntrustListComponent;
import cn.lex_mung.client_android.mvp.contract.MyEntrustListContract;
import cn.lex_mung.client_android.mvp.presenter.MyEntrustListPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.DataHelper;

public class MyEntrustListActivity extends BaseActivity<MyEntrustListPresenter> implements MyEntrustListContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.emptyView)
    EmptyView emptyView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyEntrustListComponent
                .builder()
                .appComponent(appComponent)
                .myEntrustListModule(new MyEntrustListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_entrust_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate(smartRefreshLayout);

        emptyView.getBtn().setOnClickListener(v -> {
            killMyself();
        });
    }

    @Override
    public void initRecyclerView(MyOrderAdapter2 adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(boolean isShow) {
        emptyView.setVisibility(isShow?View.VISIBLE:View.GONE);
        smartRefreshLayout.setVisibility(isShow?View.GONE:View.VISIBLE);
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

    @Override
    public void onPause() {
        super.onPause();
        if(smartRefreshLayout != null){
            smartRefreshLayout.finishLoadMore();
            smartRefreshLayout.finishRefresh();
        }
    }
}
