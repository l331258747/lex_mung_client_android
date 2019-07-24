package cn.lex_mung.client_android.mvp.ui.widget.webview;

import android.text.TextUtils;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import cn.lex_mung.client_android.utils.LogUtil;

//图片过大处理
public class MyWebViewClient2 extends WebViewClient {

    WebView webView;
    boolean isJump;//支持跳转
    OnClickLisener onClickLisener;

    public MyWebViewClient2(WebView webView) {
        this(webView,false,null);
    }

    public MyWebViewClient2(WebView webView, boolean isJump, OnClickLisener onClickLisener) {
        this.webView = webView;
        this.isJump = isJump;
        this.onClickLisener = onClickLisener;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//        view.loadUrl("file:///android_asset/error.html");
    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
//        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        sslErrorHandler.proceed();    //表示等待证书响应
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        imgReset();//重置webview中img标签的图片大小
        viewMeasure();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // 不重写会调用系统浏览器
//        view.loadUrl(url);//跳转
        if(isJump){
            LogUtil.e("shouldOverrideUrlLoading url:" + url);

            if(TextUtils.isEmpty(url))
                return true;
            if(url.startsWith("tel")){
                if(onClickLisener == null) return true;
                if(url.indexOf(":") == -1) return true;
                if(url.split(":").length < 2) return true;
                onClickLisener.onClick(url);
                return true;
            }

            view.loadUrl(url);//跳转
        }
        return true;
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.width = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    private void viewMeasure(){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        //重新测量
        webView.measure(w, h);
    }

    public interface OnClickLisener{
        void onClick(String string);
    }
}