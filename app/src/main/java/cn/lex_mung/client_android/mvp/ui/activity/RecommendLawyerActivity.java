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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerRecommendLawyerComponent;
import cn.lex_mung.client_android.di.module.RecommendLawyerModule;
import cn.lex_mung.client_android.mvp.contract.RecommendLawyerContract;
import cn.lex_mung.client_android.mvp.presenter.RecommendLawyerPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.RecommendLawyerAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;

public class RecommendLawyerActivity extends BaseActivity<RecommendLawyerPresenter> implements RecommendLawyerContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRecommendLawyerComponent
                .builder()
                .appComponent(appComponent)
                .recommendLawyerModule(new RecommendLawyerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recommend_lawyer;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setRegionId(bundleIntent.getInt(BundleTags.REGION_ID));
        mPresenter.setRequireTypeId(bundleIntent.getInt(BundleTags.REQUIRE_TYPE_ID));
        mPresenter.setRequirementId(bundleIntent.getInt(BundleTags.REQUIREMENT_ID));
        mPresenter.setRequireTypeName(bundleIntent.getString(BundleTags.REQUIRE_TYPE_NAME));
        mPresenter.setMoney(bundleIntent.getString(BundleTags.MONEY));
        mPresenter.setContent(bundleIntent.getString(BundleTags.CONTENT));
        mPresenter.setLMemberId(bundleIntent.getInt(BundleTags.L_MEMBER_ID));

        mPresenter.onCreate();
    }

    @OnClick({R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_btn:
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
                break;
        }
    }

    @Override
    public void initRecyclerView(RecommendLawyerAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
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
