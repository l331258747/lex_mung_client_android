package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.HomeTableModule;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHomeTableComponent;
import cn.lex_mung.client_android.mvp.contract.HomeTableContract;
import cn.lex_mung.client_android.mvp.presenter.HomeTablePresenter;

import cn.lex_mung.client_android.R;

public class HomeTableActivity extends BaseActivity<HomeTablePresenter> implements HomeTableContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;

    String title;
    int id;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeTableComponent
                .builder()
                .appComponent(appComponent)
                .homeTableModule(new HomeTableModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_table;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        title = bundleIntent.getString(BundleTags.TITLE);
        id = bundleIntent.getInt(BundleTags.ID);
        titleView.setTitle(title);
    }

    public void goHelp(){
        bundle.clear();
        bundle.putInt(BundleTags.ID, id);
        bundle.putString(BundleTags.TITLE, title);
        launchActivity(new Intent(mActivity, HelpStepChildActivity.class), bundle);
    }

    @OnClick({R.id.ll_help, R.id.ll_lawyer})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.ll_help:
                goHelp();
                break;
            case R.id.ll_lawyer:
                //TODO 需要类型id
                launchActivity(new Intent(mActivity, LawyerListActivity.class));
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
