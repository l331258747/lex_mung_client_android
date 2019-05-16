package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.decoration.SpacesItemDecoration;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultListComponent;
import cn.lex_mung.client_android.di.module.FreeConsultListModule;
import cn.lex_mung.client_android.mvp.contract.FreeConsultListContract;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultListPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultListAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.EmptyView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class FreeConsultListActivity extends BaseActivity<FreeConsultListPresenter> implements FreeConsultListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.emptyView)
    EmptyView emptyView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultListComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultListModule(new FreeConsultListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_list1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate(smartRefreshLayout);
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public void initRecyclerView(FreeConsultListAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, AppUtils.dip2px(mActivity, AppUtils.getXmlDef(mActivity, R.dimen.qb_px_30))));
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(FreeConsultListAdapter adapter) {
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
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
