package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.MoreSocialPositionModule;
import cn.lex_mung.client_android.mvp.ui.adapter.MoreSocialPositionAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerMoreSocialPositionComponent;
import cn.lex_mung.client_android.mvp.contract.MoreSocialPositionContract;
import cn.lex_mung.client_android.mvp.presenter.MoreSocialPositionPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.StatusBarUtil;

public class MoreSocialPositionActivity extends BaseActivity<MoreSocialPositionPresenter> implements MoreSocialPositionContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMoreSocialPositionComponent
                .builder()
                .appComponent(appComponent)
                .moreSocialPositionModule(new MoreSocialPositionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_more_social_position;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//设置状态栏文字颜色为白色
        }
        StatusBarUtil.setTranslucentForImageView(mActivity, 0, null);
        if (bundleIntent != null) {
            mPresenter.setEntity((List<String>) bundleIntent.getSerializable(BundleTags.LIST));
        }
    }

    @Override
    public void initRecyclerView(MoreSocialPositionAdapter socialPositionAdapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(socialPositionAdapter);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        killMyself();
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
