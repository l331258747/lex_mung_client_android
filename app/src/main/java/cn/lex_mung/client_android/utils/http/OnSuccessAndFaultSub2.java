package cn.lex_mung.client_android.utils.http;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import cn.jpush.im.android.api.JMessageClient;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.utils.LogUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import retrofit2.HttpException;

import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGOUT;

public class OnSuccessAndFaultSub2<T extends BaseResponse> extends ErrorHandleSubscriber<T> {

    Context context;

    public OnSuccessAndFaultSub2(RxErrorHandler rxErrorHandler, Context context) {
        super(rxErrorHandler);
        this.context = context;
    }

    @Override
    public void onNext(T t) {
        LogUtil.e("code:"+t.getCode());
        LogUtil.e("msg:"+t.getMessage());
        LogUtil.e("data:"+t.getData());

    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);

        String message = "-";
        int code = -1;
        try {
            if (e instanceof SocketTimeoutException) {//请求超时
                message = "网络连接超时";
            } else if (e instanceof ConnectException) {//网络连接超时
                message = "网络连接失败";
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                message = "安全证书异常";
            } else if (e instanceof HttpException) {//请求的地址不存在

                HttpException httpException = (HttpException) e;
                message = convertStatusCode(context, httpException);
                code = httpException.code();

            } else if (e instanceof UnknownHostException) {//域名解析失败
                message = "域名解析失败";
            } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException) {
                message = "数据解析错误";
            }else {
                message = "error:" + e.getMessage();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            LogUtil.e("error:" + e.getMessage());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(code);
            baseResponse.setMessage(message);
            onNext((T) baseResponse);
        }
    }


    private String convertStatusCode(Context context, HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if(httpException.code() == 504){
            msg = "网络异常，请检查您的网络状态";
        }else if (httpException.code() == 404) {
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
