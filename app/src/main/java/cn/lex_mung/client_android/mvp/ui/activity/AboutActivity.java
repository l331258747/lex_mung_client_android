package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.ShareUtils;
import cn.lex_mung.client_android.di.module.AboutModule;
import cn.lex_mung.client_android.mvp.model.entity.AboutEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DeviceUtils;

import cn.lex_mung.client_android.di.component.DaggerAboutComponent;
import cn.lex_mung.client_android.mvp.contract.AboutContract;
import cn.lex_mung.client_android.mvp.presenter.AboutPresenter;

import cn.lex_mung.client_android.R;

public class AboutActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.view_bottom)
    View viewBottom;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutComponent
                .builder()
                .appComponent(appComponent)
                .aboutModule(new AboutModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setEntity((AboutEntity) bundleIntent.getSerializable(BundleTags.ENTITY));
        }
        tvVersion.setText(String.format(getString(R.string.text_client_side), DeviceUtils.getVersionName(mActivity)));
    }

    @OnClick({R.id.tv_about_me, R.id.tv_feedback, R.id.tv_business_cooperation, R.id.tv_share})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_about_me:
                bundle.clear();
                bundle.putString(BundleTags.URL, mPresenter.getEntity().getAboutUsUrl());
                bundle.putBoolean(BundleTags.IS_SHARE, false);
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                break;
            case R.id.tv_feedback:
                launchActivity(new Intent(mActivity, FeedbackActivity.class));
                break;
            case R.id.tv_business_cooperation:
                bundle.clear();
                bundle.putString(BundleTags.URL, mPresenter.getEntity().getCooperationUrl());
                bundle.putBoolean(BundleTags.IS_SHARE, false);
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                break;
            case R.id.tv_share:
                ShareUtils.shareUrl(mActivity
                        , viewBottom
                        , mPresenter.getEntity().getShare().getUrl()
                        , mPresenter.getEntity().getShare().getTitle()
                        , mPresenter.getEntity().getShare().getDesc());
                break;
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
