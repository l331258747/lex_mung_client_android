package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.OrganizationLawyerModule;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerOrganizationLawyerComponent;
import cn.lex_mung.client_android.mvp.contract.OrganizationLawyerContract;
import cn.lex_mung.client_android.mvp.presenter.OrganizationLawyerPresenter;

import cn.lex_mung.client_android.R;

public class OrganizationLawyerActivity extends BaseActivity<OrganizationLawyerPresenter> implements OrganizationLawyerContract.View {

    @Inject
    ImageLoader mImageLoader;

    int orgId;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrganizationLawyerComponent
                .builder()
                .appComponent(appComponent)
                .organizationLawyerModule(new OrganizationLawyerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_organization_lawyer;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        orgId = bundleIntent.getInt(BundleTags.ID,-1);
        mPresenter.setOrgId(orgId);
        mPresenter.onCreate(smartRefreshLayout);
    }

    @Override
    public void initRecyclerView(LawyerListAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(LawyerListAdapter adapter) {
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
