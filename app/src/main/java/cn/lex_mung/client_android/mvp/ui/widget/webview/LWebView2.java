package cn.lex_mung.client_android.mvp.ui.widget.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import me.zl.mvp.utils.DataHelper;

import static cn.lex_mung.client_android.app.DataHelperTags.TOKEN;

/**
 * Class Name: LWebView.java
 * <p>
 * Function:
 * <p>
 * version 1.0
 * since  2017/02/13 13:57
 */

public class LWebView2 extends WebView {
    private View mErrorView;
    private Context context;

    public LWebView2(Context context) {
        this(context, null);
    }

    public LWebView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LWebView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     *
     */
    private void init() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);//支持js
        settings.setDisplayZoomControls(false); // 关闭自动缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR); //  自适应屏幕处理，不设置，低分辨率显示异常
        settings.setDefaultTextEncodingName("utf-8");
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //不使用缓存

        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setDomStorageEnabled(true); // h5 本地缓存
        settings.setDatabaseEnabled(true); //启用数据库

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把所有内容放大webview等宽的一列中

        this.setVerticalScrollBarEnabled(false);//隐藏纵向ScorollView
        this.setVerticalScrollbarOverlay(false); //指定的垂直滚动条没有叠加样式
        this.setHorizontalScrollBarEnabled(false);
        this.setHorizontalScrollbarOverlay(false);

//        setWebViewClient(new MyWebViewClient(this));
    }

    public void synCookies(String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        String cookieStr = "X-Token=" + DataHelper.getStringSF(context, TOKEN) + ";Domain=.lex-mung.com;Path=/";
        cookieManager.setCookie(url, cookieStr);
        CookieSyncManager.getInstance().sync();
    }

    boolean isOnPause;
    public void onWebResume() {
        try {
            if (isOnPause) {
                if (this != null) {
                    this.onResume();
                }
                isOnPause = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onWebPause() {
        try {
            if (this != null) {
                this.onPause();
                isOnPause = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onWebDestroy() {
        if (this != null) {
            this.clearCache(true);
            this.clearHistory();

            this.setVisibility(View.GONE);
            this.removeAllViews();
            this.destroy();
        }

        isOnPause = false;
    }

}
