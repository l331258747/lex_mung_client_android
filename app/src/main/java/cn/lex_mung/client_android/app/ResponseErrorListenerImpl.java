package cn.lex_mung.client_android.app;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.google.gson.JsonParseException;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.jpush.im.android.api.JMessageClient;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.LogUtils;
import retrofit2.HttpException;
import timber.log.Timber;

import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGOUT;

/**
 * RxJava中发生的所有错误处理类
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Throwable t) {
        Timber.tag("Catch-Error").w(t);
        String msg = "未知错误";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(context, httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误";
        }
        LogUtils.debugInfo(msg);
    }

    private String convertStatusCode(Context context, HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else if (httpException.code() == 401) {
            msg = "登录失效，请重新登录";
            DataHelper.removeSF(context, DataHelperTags.TOKEN);
            DataHelper.removeSF(context, DataHelperTags.IS_LOGIN_SUCCESS);
            DataHelper.removeSF(context, DataHelperTags.USER_INFO_DETAIL);

            AppUtils.post(LOGIN_INFO, LOGOUT);
            JMessageClient.logout();
            AppUtils.makeText(context, msg);

            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
