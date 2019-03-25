package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.RequirementListModule;
import cn.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.RequirementAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerRequirementListComponent;
import cn.lex_mung.client_android.mvp.contract.RequirementListContract;
import cn.lex_mung.client_android.mvp.presenter.RequirementListPresenter;

import cn.lex_mung.client_android.R;

import java.util.List;

public class RequirementListActivity extends BaseActivity<RequirementListPresenter> implements RequirementListContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRequirementListComponent
                .builder()
                .appComponent(appComponent)
                .requirementListModule(new RequirementListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_requirement_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate((List<RequirementStatusEntity>) bundleIntent.getSerializable(BundleTags.LIST));
    }

    @Override
    public void initRecyclerView(RequirementAdapter adapter) {
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
