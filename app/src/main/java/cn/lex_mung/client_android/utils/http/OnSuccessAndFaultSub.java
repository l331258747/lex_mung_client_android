package cn.lex_mung.client_android.utils.http;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.text.TextUtils;
import android.util.Log;
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

public class OnSuccessAndFaultSub extends ErrorHandleSubscriber<BaseResponse> {

    ResponseCallback mResponseCallback;
    Context context;

    public OnSuccessAndFaultSub(RxErrorHandler rxErrorHandler,Context context,ResponseCallback mResponseCallback) {
        super(rxErrorHandler);
        this.mResponseCallback = mResponseCallback;
        this.context = context;
    }

    @Override
    public void onNext(BaseResponse t) {
        LogUtil.e("code:"+t.getCode());
        LogUtil.e("msg:"+t.getMessage());
        LogUtil.e("data:"+t.getData());

        if(t.getCode()==0){
            mResponseCallback.onSuccess(t.getData());
        }else{
            if(!TextUtils.isEmpty(t.getMessage())){
                mResponseCallback.onFault(t.getMessage());
            }else{
                mResponseCallback.onFault("--");
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);

        try {
            if (e instanceof SocketTimeoutException) {//请求超时
                mResponseCallback.onFault("网络连接超时");
            } else if (e instanceof ConnectException) {//网络连接超时
                mResponseCallback.onFault("网络连接失败");
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                mResponseCallback.onFault("安全证书异常");
            } else if (e instanceof HttpException) {//请求的地址不存在

                HttpException httpException = (HttpException) e;
                mResponseCallback.onFault(convertStatusCode(context, httpException));

            } else if (e instanceof UnknownHostException) {//域名解析失败
                mResponseCallback.onFault("域名解析失败");
            } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException) {
                mResponseCallback.onFault("数据解析错误");
            }else {
                mResponseCallback.onFault("error:" + e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
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
