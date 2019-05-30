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
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerReleaseDemandHistoryComponent;
import cn.lex_mung.client_android.di.module.ReleaseDemandHistoryModule;
import cn.lex_mung.client_android.mvp.contract.ReleaseDemandHistoryContract;
import cn.lex_mung.client_android.mvp.presenter.ReleaseDemandHistoryPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandHistoryAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class ReleaseDemandHistoryActivity extends BaseActivity<ReleaseDemandHistoryPresenter> implements ReleaseDemandHistoryContract.View {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_tip)
    TextView tvTip;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerReleaseDemandHistoryComponent
                .builder()
                .appComponent(appComponent)
                .releaseDemandHistoryModule(new ReleaseDemandHistoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_release_demand_history;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setInfo(bundleIntent.getInt(BundleTags.MEMBER_ID),
                bundleIntent.getInt(BundleTags.REGION_ID));
        mPresenter.onCreate(smartRefreshLayout);
    }

    @Override
    public void initRecyclerView(ReleaseDemandHistoryAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(ReleaseDemandHistoryAdapter adapter) {
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setTipHide(boolean isShow) {
        if(isShow){
            tvTip.setVisibility(View.VISIBLE);
        }else{
            tvTip.setVisibility(View.GONE);
        }
    }


    @Override
    public Activity getActivity() {
        return mActivity;
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
