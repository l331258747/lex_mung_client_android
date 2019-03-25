package cn.lex_mung.client_android.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import cn.lex_mung.client_android.BuildConfig;
import cn.lex_mung.client_android.mvp.model.api.Api;
import com.zl.mvp.http.imageloader.glide.GlideImageLoaderStrategy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.zl.mvp.base.delegate.AppLifecycles;
import me.zl.mvp.di.module.GlobalConfigModule;
import me.zl.mvp.http.log.RequestInterceptor;
import me.zl.mvp.integration.ConfigModule;

/**
 * App 的全局配置信息
 */
public final class GlobalConfiguration implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) {
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.baseurl(Api.APP_DOMAIN)
                .imageLoaderStrategy(new GlideImageLoaderStrategy())//图片加载框架处理类
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))//全局处理Http请求和响应结果的处理类
                .responseErrorListener(new ResponseErrorListenerImpl())//RxJava中发生的所有错误处理类
                .gsonConfiguration((context1, jsonBuilder) -> {//自定义配置Json的参数
                    jsonBuilder
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();//支持将序列化key为object的map
                })
                .retrofitConfiguration((context1, retrofitBuilder) -> {//自定义配置Retrofit的参数
                })
                .okhttpConfiguration((context1, okHttpBuilder) -> {//自定义配置OkHttp的参数
                    okHttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                    okHttpBuilder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
                    okHttpBuilder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }

}
