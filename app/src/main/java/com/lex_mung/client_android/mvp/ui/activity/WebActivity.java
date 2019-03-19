package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.app.ShareUtils;
import com.lex_mung.client_android.di.module.WebModule;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

import com.lex_mung.client_android.di.component.DaggerWebComponent;
import com.lex_mung.client_android.mvp.contract.WebContract;
import com.lex_mung.client_android.mvp.presenter.WebPresenter;

import com.lex_mung.client_android.R;
import com.umeng.analytics.MobclickAgent;

import static com.lex_mung.client_android.app.DataHelperTags.TOKEN;

public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View {
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.view_dialog)
    View viewDialog;

    private boolean isShare;
    private String url;
    private String title;
    private String des;
    private String image;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebComponent
                .builder()
                .appComponent(appComponent)
                .webModule(new WebModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_web;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("w_y_shouye_jjfa_list");
        MobclickAgent.onResume(mActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("w_y_shouye_jjfa_list");
        MobclickAgent.onPause(mActivity);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null
                && bundleIntent.containsKey(BundleTags.URL)) {
            url = bundleIntent.getString(BundleTags.URL);
            title = bundleIntent.getString(BundleTags.TITLE);
            des = bundleIntent.getString(BundleTags.DES);
            image = bundleIntent.getString(BundleTags.IMAGE);
            isShare = bundleIntent.getBoolean(BundleTags.IS_SHARE, true);
        }
        if (isShare) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(R.string.text_share);
        }
        if (TextUtils.isEmpty(title)) {
            tvRight.setVisibility(View.GONE);
        }
        tvTitle.setText(title);
        showLoading("");
        initWebView();
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    hideLoading();
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urls) {
                view.loadUrl(urls);
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.addJavascriptInterface(this, "JsBridgeApp");
        synCookies(url);
        webView.loadUrl(url);
    }

    public void synCookies(String url) {
        CookieSyncManager.createInstance(mActivity);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        String cookieStr = "X-Token=" + DataHelper.getStringSF(mActivity, TOKEN) + ";Domain=.lex-mung.com;Path=/";
        cookieManager.setCookie(url, cookieStr);
        CookieSyncManager.getInstance().sync();
    }

    @OnClick(R.id.tv_right)
    public void onViewClicked() {
        MobclickAgent.onEvent(mActivity, "w_y__shouye_jjfa_list_fenxiang");
        if (!TextUtils.isEmpty(url)
                && !TextUtils.isEmpty(title)) {
            ShareUtils.shareUrl(mActivity
                    , viewDialog
                    , url
                    , title
                    , des
                    , image);
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
