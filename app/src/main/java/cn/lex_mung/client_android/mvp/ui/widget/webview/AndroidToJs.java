package cn.lex_mung.client_android.mvp.ui.widget.webview;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import cn.lex_mung.client_android.mvp.ui.activity.MainActivity;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.ActivityCollect;
import me.zl.mvp.utils.AppUtils;

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST_ID;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;

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

    //找律师
    @JavascriptInterface
    public void goLawyerList(int requireTypeId){
        ActivityCollect.getAppCollect().finishAllNotHome(MainActivity.class);
        ((MainActivity) ActivityCollect.getAppCollect().currentActivity()).switchPage(2);
        AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_LIST_ID, requireTypeId);
    }

}
