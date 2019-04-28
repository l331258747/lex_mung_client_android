package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.ShareUtils;
import cn.lex_mung.client_android.di.component.DaggerWebComponent;
import cn.lex_mung.client_android.di.module.WebModule;
import cn.lex_mung.client_android.mvp.contract.WebContract;
import cn.lex_mung.client_android.mvp.model.entity.other.WebGoPayEntity;
import cn.lex_mung.client_android.mvp.presenter.WebPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.webview.LWebView;
import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST_ID;

public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View {
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.web_view)
    LWebView webView;
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
        mPresenter.onResume();
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
        showLoading("");
        initWebView();
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tvTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    hideLoading();
                }
                super.onProgressChanged(view, newProgress);
            }
        });
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String urls) {
//                view.loadUrl(urls);
//                return true;
//            }
//        });
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setBlockNetworkImage(false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        webView.addJavascriptInterface(new AndroidToJs(), "JsBridgeApp");//h5 js调用 app.pay();

        webView.synCookies(url);
        webView.loadUrl(url);
    }

//    public void synCookies(String url) {
//        CookieSyncManager.createInstance(mActivity);
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
//        String cookieStr = "X-Token=" + DataHelper.getStringSF(mActivity, TOKEN) + ";Domain=.lex-mung.com;Path=/";
//        cookieManager.setCookie(url, cookieStr);
//        CookieSyncManager.getInstance().sync();
//    }

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

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    public class AndroidToJs extends Object {

        //支付
        @JavascriptInterface
        public void goPay(String string) {
            if(TextUtils.isEmpty(string))
                return;

            WebGoPayEntity businessEntity = GsonUtil.convertString2Object(string,WebGoPayEntity.class);

            if (isFastClick()) return;
            Bundle bundle = new Bundle();
            if (mPresenter.isLogin()) {
                bundle.clear();
                bundle.putInt(BundleTags.ID, businessEntity.getRequireTypeId());
//                bundle.putInt(BundleTags.TYPE, businessEntity.getType());//支付方式在下个页面选择、
                bundle.putString(BundleTags.TITLE, businessEntity.getRequireTypeName());
//                bundle.putSerializable(BundleTags.ENTITY, entity);//没有律师，不要律师信息
                bundle.putFloat(BundleTags.MONEY, businessEntity.getMoney());
                launchActivity(new Intent(mActivity, RushLoanPayActivity.class), bundle);
            } else {
                bundle.clear();
                bundle.putInt(BundleTags.TYPE, 1);
                launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
            }
        }

        //电话
        @JavascriptInterface
        public void toCall(String phone) {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(dialIntent);
        }

        //找律师
        @JavascriptInterface
        public void goLawyerList(int requireTypeId) {
            runOnUiThread(() -> {
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
                ((MainActivity)AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(2);
                AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_LIST_ID, requireTypeId);
            });
        }

    }


}
