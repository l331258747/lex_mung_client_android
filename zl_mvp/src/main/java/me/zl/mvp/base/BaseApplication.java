package me.zl.mvp.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import me.zl.mvp.base.delegate.AppDelegate;
import me.zl.mvp.base.delegate.AppLifecycles;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.Preconditions;

/**
 * application，一定要实现App接口
 */
public class BaseApplication extends Application implements App {
    private AppLifecycles mAppDelegate;

    /**
     * 最早调用。用于 MultiDex 以及插件化框架的初始化
     *
     * @param base Context
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    /**
     * 将 {@link AppComponent} 返回出去, 供其它地方使用
     *
     * @return AppComponent
     * @see AppUtils#obtainAppComponentFromContext(Context) 可直接获取 {@link AppComponent}
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", mAppDelegate.getClass().getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }

}
