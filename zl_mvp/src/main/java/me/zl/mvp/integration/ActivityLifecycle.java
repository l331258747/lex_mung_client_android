package me.zl.mvp.integration;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.base.delegate.ActivityDelegate;
import me.zl.mvp.base.delegate.ActivityDelegateImpl;
import me.zl.mvp.base.delegate.FragmentDelegate;
import me.zl.mvp.base.delegate.IActivity;
import me.zl.mvp.integration.cache.Cache;
import me.zl.mvp.integration.cache.IntelligentCache;
import me.zl.mvp.utils.Preconditions;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;

/**
 * {@link Application.ActivityLifecycleCallbacks} 默认实现类
 * 通过 {@link ActivityDelegate} 管理 {@link Activity}
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    Cache<String, Object> mExtras;
    @Inject
    Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycle;
    @Inject
    Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理
        boolean isNotAdd = false;
        if (activity.getIntent() != null)
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);

        if (!isNotAdd)
            mAppManager.addActivity(activity);

        //配置ActivityDelegate
        if (activity instanceof IActivity) {
            ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
            if (activityDelegate == null) {
                Cache<String, Object> cache = getCacheFromActivity((IActivity) activity);
                activityDelegate = new ActivityDelegateImpl(activity);
                cache.put(IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE), activityDelegate);
            }
            activityDelegate.onCreate(savedInstanceState);
        }

        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mAppManager.setCurrentActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (mAppManager.getCurrentActivity() == activity) {
            mAppManager.setCurrentActivity(null);
        }

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mAppManager.removeActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
            getCacheFromActivity((IActivity) activity).clear();
        }
    }

    /**
     * 每个 Activity 的所有 Fragment 设置监听其生命周期, Activity 可以通过 {@link IActivity#useFragment()}
     *
     * @param activity
     */
    private void registerFragmentCallbacks(Activity activity) {

        boolean useFragment = !(activity instanceof IActivity) || ((IActivity) activity).useFragment();
        if (activity instanceof FragmentActivity && useFragment) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true);
            if (mExtras.containsKey(IntelligentCache.getKeyOfKeep(ConfigModule.class.getName()))) {
                List<ConfigModule> modules = (List<ConfigModule>) mExtras.get(IntelligentCache.getKeyOfKeep(ConfigModule.class.getName()));
                for (ConfigModule module : modules) {
                    module.injectFragmentLifecycle(mApplication, mFragmentLifecycles.get());
                }
                mExtras.remove(IntelligentCache.getKeyOfKeep(ConfigModule.class.getName()));
            }

            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles.get()) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity) {
        ActivityDelegate activityDelegate = null;
        if (activity instanceof IActivity) {
            Cache<String, Object> cache = getCacheFromActivity((IActivity) activity);
            activityDelegate = (ActivityDelegate) cache.get(IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE));
        }
        return activityDelegate;
    }

    @NonNull
    private Cache<String, Object> getCacheFromActivity(IActivity activity) {
        Cache<String, Object> cache = activity.provideCache();
        Preconditions.checkNotNull(cache, "%s cannot be null on Activity", Cache.class.getName());
        return cache;
    }

}
