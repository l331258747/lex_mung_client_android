package me.zl.mvp.base.delegate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import me.zl.mvp.base.App;
import me.zl.mvp.di.component.AppComponent;

import me.zl.mvp.di.component.DaggerAppComponent;

import me.zl.mvp.di.module.GlobalConfigModule;
import me.zl.mvp.integration.ConfigModule;
import me.zl.mvp.integration.ManifestParser;
import me.zl.mvp.integration.cache.IntelligentCache;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * AppDelegate 可以代理 Application 的生命周期,在对应的生命周期,执行对应的逻辑,解决java单继承的问题
 */
public class AppDelegate implements App, AppLifecycles {
    private Application mApplication;
    private AppComponent mAppComponent;
    @Inject
    @Named("ActivityLifecycle")
    protected Application.ActivityLifecycleCallbacks mActivityLifecycle;
    @Inject
    @Named("ActivityLifecycleForRxLifecycle")
    protected Application.ActivityLifecycleCallbacks mActivityLifecycleForRxLifecycle;
    private List<ConfigModule> mModules;
    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();

    public AppDelegate(@NonNull Context context) {

        //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
        this.mModules = new ManifestParser(context).parse();

        //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
        for (ConfigModule module : mModules) {

            //将实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合
            module.injectAppLifecycle(context, mAppLifecycles);

            //将实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合
            module.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {
        //遍历 mAppLifecycles, 执行所有已注册的 AppLifecycles 的 attachBaseContext() 方法
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
                .builder()
                .application(mApplication)//提供application
                .globalConfigModule(getGlobalConfigModule(mApplication, mModules))//全局配置
                .build();
        mAppComponent.inject(this);

        //将 ConfigModule 的实现类的集合存放到缓存 Cache, 可以随时获取
        mAppComponent.extras().put(IntelligentCache.getKeyOfKeep(ConfigModule.class.getName()), mModules);

        this.mModules = null;

        //注册内部已实现的 Activity 生命周期逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        //注册内部已实现的 RxLifecycle 逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);

        //注册扩展的 Activity 生命周期逻辑
        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        //执行扩展的 App onCreate 逻辑
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }

    }


    @Override
    public void onTerminate(@NonNull Application application) {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mActivityLifecycleForRxLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppLifecycles lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mAppComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycleForRxLifecycle = null;
        this.mActivityLifecycles = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }


    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     *
     * @return GlobalConfigModule
     */
    private GlobalConfigModule getGlobalConfigModule(Context context, List<ConfigModule> modules) {

        GlobalConfigModule.Builder builder = GlobalConfigModule.builder();

        //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
        for (ConfigModule module : modules) {
            module.applyOptions(context, builder);
        }

        return builder.build();
    }


    /**
     * 将AppComponent返回出去, 供其它地方使用,
     *
     * @return AppComponent
     * @see AppUtils#obtainAppComponentFromContext(Context) 可直接获取 {@link AppComponent}
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppComponent,
                "%s == null, first call %s#onCreate(Application) in %s#onCreate()",
                AppComponent.class.getName(), getClass().getName(), mApplication == null
                        ? Application.class.getName() : mApplication.getClass().getName());
        return mAppComponent;
    }
}

