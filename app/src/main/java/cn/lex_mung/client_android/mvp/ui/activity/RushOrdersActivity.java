package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import butterknife.BindView;
import cn.lex_mung.client_android.di.module.RushOrdersModule;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.RushOrdersView;
import dagger.BindsInstance;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerRushOrdersComponent;
import cn.lex_mung.client_android.mvp.contract.RushOrdersContract;
import cn.lex_mung.client_android.mvp.presenter.RushOrdersPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.StatusBarUtil;

/**
 * 抢单
 */
public class RushOrdersActivity extends BaseActivity<RushOrdersPresenter> implements RushOrdersContract.View {

    @BindView(R.id.view_rush_orders)
    RushOrdersView rushOrdersView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRushOrdersComponent
                .builder()
                .appComponent(appComponent)
                .rushOrdersModule(new RushOrdersModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rush_orders;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_ff), 0);
        rushOrdersView.setProgress(2);
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
