package me.zl.mvp.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import me.zl.mvp.http.GlobalHttpHandler;
import me.zl.mvp.http.log.RequestInterceptor;
import me.zl.mvp.utils.DataHelper;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 提供一些三方库客户端实例的 {@link Module}
 */
@Module
public abstract class ClientModule {
    private static final int TIME_OUT = 15;

    /**
     * 提供 {@link Retrofit}
     *
     * @param application   Application
     * @param configuration RetrofitConfiguration
     * @param builder       Retrofit.Builder
     * @param client        OkHttpClient
     * @param httpUrl       HttpUrl
     * @param gson          Gson
     * @return {@link Retrofit}
     */
    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application
            , @Nullable RetrofitConfiguration configuration
            , Retrofit.Builder builder
            , OkHttpClient client
            , HttpUrl httpUrl
            , Gson gson) {
        builder
                .baseUrl(httpUrl)//域名
                .client(client);//设置okhttp

        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }

        builder
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 Rxjava
                .addConverterFactory(GsonConverterFactory.create(gson));//使用 Gson
        return builder.build();
    }

    /**
     * 提供 {@link OkHttpClient}
     *
     * @param application   Application
     * @param configuration OkhttpConfiguration
     * @param builder       OkHttpClient.Builder
     * @param intercept     Interceptor
     * @param interceptors  List<Interceptor>
     * @param handler       GlobalHttpHandler
     * @return {@link OkHttpClient}
     */
    @Singleton
    @Provides
    static OkHttpClient provideClient(Application application
            , @Nullable OkhttpConfiguration configuration
            , OkHttpClient.Builder builder
            , Interceptor intercept
            , @Nullable List<Interceptor> interceptors
            , @Nullable GlobalHttpHandler handler) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        if (handler != null) {
            builder.addInterceptor(chain -> chain.proceed(handler.onHttpRequestBefore(chain, chain.request())));
        }
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (configuration != null) {
            configuration.configOkhttp(application, builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Binds
    abstract Interceptor bindInterceptor(RequestInterceptor interceptor);

    /**
     * 提供 {@link RxCache}
     *
     * @param application    Application
     * @param configuration  RxCacheConfiguration
     * @param cacheDirectory File
     * @param gson           Gson
     * @return {@link RxCache}
     */
    @Singleton
    @Provides
    static RxCache provideRxCache(Application application
            , @Nullable RxCacheConfiguration configuration
            , @Named("RxCacheDirectory") File cacheDirectory
            , Gson gson) {
        RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
        if (configuration != null) {
            rxCache = configuration.configRxCache(application, builder);
        }
        if (rxCache != null) return rxCache;
        return builder
                .persistence(cacheDirectory, new GsonSpeaker(gson));
    }

    /**
     * 需要单独给 {@link RxCache} 提供缓存路径
     *
     * @param cacheDir File
     * @return {@link File}
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    static File provideRxCacheDirectory(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "RxCache");
        return DataHelper.makeDirs(cacheDirectory);
    }

    /**
     * 提供处理 RxJava 错误的管理器
     *
     * @param application Application
     * @param listener    ResponseErrorListener
     * @return {@link RxErrorHandler}
     */
    @Singleton
    @Provides
    static RxErrorHandler proRxErrorHandler(Application application, ResponseErrorListener listener) {
        return RxErrorHandler
                .builder()
                .with(application)
                .responseErrorListener(listener)
                .build();
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkhttpConfiguration {
        void configOkhttp(Context context, OkHttpClient.Builder builder);
    }

    public interface RxCacheConfiguration {
        /**
         * RxCache配置
         *
         * @param context Context
         * @param builder RxCache.Builder
         * @return {@link RxCache}
         */
        RxCache configRxCache(Context context, RxCache.Builder builder);
    }
}
