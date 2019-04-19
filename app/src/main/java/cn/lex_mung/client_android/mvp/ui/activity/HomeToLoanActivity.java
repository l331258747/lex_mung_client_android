package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.HomeToLoanActivityModule;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHomeToLoanActivityComponent;
import cn.lex_mung.client_android.mvp.contract.HomeToLoanActivityContract;
import cn.lex_mung.client_android.mvp.presenter.HomeToLoanActivityPresenter;

import cn.lex_mung.client_android.R;

public class HomeToLoanActivity extends BaseActivity<HomeToLoanActivityPresenter> implements HomeToLoanActivityContract.View {

    @BindView(R.id.tv_btn)
    TextView tvBtn;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeToLoanActivityComponent
                .builder()
                .appComponent(appComponent)
                .homeToLoanActivityModule(new HomeToLoanActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_to_loan;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @OnClick({
            R.id.tv_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_btn:

                break;
        }
    }
}
