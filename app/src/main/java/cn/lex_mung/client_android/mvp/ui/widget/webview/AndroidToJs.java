package cn.lex_mung.client_android.mvp.ui.widget.webview;

import android.webkit.JavascriptInterface;

import cn.lex_mung.client_android.utils.LogUtil;

public class AndroidToJs extends Object {

    @JavascriptInterface
    public void toPay(int price){
        //TODO 支付
        LogUtil.e("支付:" + price);

    }

    @JavascriptInterface
    public void callPhone(String phone){
        LogUtil.e("phone:" + phone);
    }

    @JavascriptInterface
    public void Jump(int type){
        LogUtil.e("type:" + type);
    }



}
