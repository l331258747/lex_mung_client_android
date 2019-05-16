package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.decoration.SpacesItemDecoration;
import cn.lex_mung.client_android.di.module.FreeConsultMainModule;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.MyOrderAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerFreeConsultMainComponent;
import cn.lex_mung.client_android.mvp.contract.FreeConsultMainContract;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultMainPresenter;

import cn.lex_mung.client_android.R;

public class FreeConsultMainActivity extends BaseActivity<FreeConsultMainPresenter> implements FreeConsultMainContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultMainComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultMainModule(new FreeConsultMainModule(this))
                .build()
                .inject(this);
    }

    @OnClick({
            R.id.tv_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_btn:
                showMessage("发布");
                break;
        }
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_main1;
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate(smartRefreshLayout);

        titleView.getRightTv().setOnClickListener(v -> {
            launchActivity(new Intent(mActivity,FreeConsultListActivity.class));
        });
    }

    @Override
    public void initRecyclerView(FreeConsultMainAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(FreeConsultMainAdapter adapter) {
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
