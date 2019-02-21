package me.zl.mvp.di.component;

import android.app.Activity;
import android.app.Application;

import com.google.gson.Gson;
import me.zl.mvp.base.delegate.AppDelegate;
import me.zl.mvp.di.module.AppModule;
import me.zl.mvp.di.module.ClientModule;
import me.zl.mvp.di.module.GlobalConfigModule;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.integration.IRepositoryManager;
import me.zl.mvp.integration.cache.Cache;

import java.io.File;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;

/**
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {
    Application application();

    /**
     * 用于管理所有 {@link Activity}
     *
     * @return {@link AppManager}
     * @deprecated Use {@link AppManager#getAppManager()} instead
     */
    @Deprecated
    AppManager appManager();

    /**
     * 用于管理网络请求层, 以及数据缓存层
     *
     * @return {@link IRepositoryManager}
     */
    IRepositoryManager repositoryManager();

    /**
     * RxJava 错误处理管理类
     *
     * @return {@link RxErrorHandler}
     */
    RxErrorHandler rxErrorHandler();

    /**
     * 图片加载管理器
     *
     * @return
     */
    ImageLoader imageLoader();

    /**
     * 网络请求框架
     *
     * @return {@link OkHttpClient}
     */
    OkHttpClient okHttpClient();

    /**
     * Json 序列化库
     *
     * @return {@link Gson}
     */
    Gson gson();

    /**
     * 缓存文件根目录 (RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下)
     *
     * @return {@link File}
     */
    File cacheFile();

    /**
     * 用来存取一些整个 App 公用的数据
     *
     * @return {@link Cache}
     */
    Cache<String, Object> extras();

    /**
     * 用于创建框架所需缓存对象的工厂
     *
     * @return {@link Cache.Factory}
     */
    Cache.Factory cacheFactory();

    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }
}
