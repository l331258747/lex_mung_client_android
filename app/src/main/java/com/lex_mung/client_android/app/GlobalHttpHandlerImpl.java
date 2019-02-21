package com.lex_mung.client_android.app;

import android.content.Context;

import me.zl.mvp.http.GlobalHttpHandler;
import me.zl.mvp.utils.DataHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 全局处理Http请求和响应结果的处理类
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 可以先拿到服务器的结果、做一些处理
     * 比如token过期等。
     *
     * @param httpResult 返回结果
     * @param chain      OkHttp3的拦截器
     * @param response   Response
     * @return Response
     */
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        return response;
    }

    /**
     * 发送请求之前。可以添加一些统一的参数。
     * 比如添加token、header等。
     *
     * @param chain   OkHttp3的拦截器
     * @param request Request
     * @return Request
     */
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
//        String token = DataHelper.getStringSF(context, TOKEN);
//        String deviceJson = DataHelper.getStringSF(context, DEVICE);
        return chain
                .request()
                .newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
//                .header("X-Token", token)
//                .header("device", deviceJson)
                .build();
    }
}
