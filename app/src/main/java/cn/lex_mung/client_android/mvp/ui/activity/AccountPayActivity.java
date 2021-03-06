package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.di.module.AccountPayModule;
import cn.lex_mung.client_android.mvp.ui.adapter.MyAccountPayAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerAccountPayComponent;
import cn.lex_mung.client_android.mvp.contract.AccountPayContract;
import cn.lex_mung.client_android.mvp.presenter.AccountPayPresenter;

import cn.lex_mung.client_android.R;

import com.umeng.analytics.MobclickAgent;

public class AccountPayActivity extends BaseActivity<AccountPayPresenter> implements AccountPayContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_select_wx)
    ImageView ivSelectWx;
    @BindView(R.id.iv_select_zfb)
    ImageView ivSelectZfb;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;

    private DefaultDialog defaultDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountPayComponent
                .builder()
                .appComponent(appComponent)
                .accountPayModule(new AccountPayModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_pay;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate();
    }

    @Override
    public void initRecyclerView(MyAccountPayAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mActivity, 3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setOrderMoney(String format) {
        tvOrderMoney.setText(format);
    }

    @Override
    public void showToAppInfoDialog() {
        if (defaultDialog == null) {
            defaultDialog = new DefaultDialog(mActivity, dialog -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
                startActivity(intent);
                dialog.dismiss();
            }
                    , getString(R.string.text_manual_open_permissions)
                    , getString(R.string.text_to_open)
                    , getString(R.string.text_cancel));
        }
        if (!defaultDialog.isShowing()) {
            defaultDialog.show();
        }
    }

    @OnClick({R.id.tv_wx, R.id.tv_zfb, R.id.bt_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wx:
                ivSelectWx.setImageResource(R.drawable.ic_show_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(1);
                break;
            case R.id.tv_zfb:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_show_select);
                mPresenter.setPayType(2);
                break;
            case R.id.bt_pay:
                mPresenter.pay(webView.getSettings().getUserAgentString());
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public void killMyself() {
        finish();
    }
}
