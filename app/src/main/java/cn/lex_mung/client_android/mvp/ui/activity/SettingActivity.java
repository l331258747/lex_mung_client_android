package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.TextView;

import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.DownloadUtils;
import cn.lex_mung.client_android.di.module.SettingModule;
import cn.lex_mung.client_android.mvp.model.entity.VersionEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.DeviceUtils;

import cn.lex_mung.client_android.di.component.DaggerSettingComponent;
import cn.lex_mung.client_android.mvp.contract.SettingContract;
import cn.lex_mung.client_android.mvp.presenter.SettingPresenter;

import cn.lex_mung.client_android.R;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import javax.inject.Inject;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent
                .builder()
                .appComponent(appComponent)
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvVersionName.setText(String.format(getString(R.string.text_version), DeviceUtils.getVersionName(mActivity)));

        if(!DataHelper.getBooleanSF(mActivity,DataHelperTags.IS_LOGIN_SUCCESS)){
            tvLogout.setVisibility(View.GONE);
        }
    }

    @Override
    public void startDownload(VersionEntity data) {
        DownloadUtils.getInstance().update(mActivity, data);
    }

    @OnClick({R.id.tv_clear_cache, R.id.tv_version_update, R.id.tv_logout})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_clear_cache:
                mImageLoader.clear(mActivity
                        , ImageConfigImpl
                                .builder()
                                .isClearDiskCache(true)
                                .isClearMemory(true)
                                .build());

                CookieSyncManager.createInstance(getApplicationContext());
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                CookieSyncManager.getInstance().sync();
                webView.setWebChromeClient(null);
                webView.setWebViewClient(null);
                webView.getSettings().setJavaScriptEnabled(false);
                webView.clearCache(true);//清除缓存

                showMessage("清除成功");
                break;
            case R.id.tv_version_update:
                mPresenter.checkVersion();
                break;
            case R.id.tv_logout:
                new DefaultDialog(mActivity, dialog -> mPresenter.logout(dialog)
                        , "您确定要退出账号吗?"
                        , "确定"
                        , "取消").show();
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
