package me.zl.mvp.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.zl.mvp.integration.EventBusManager;
import me.zl.mvp.utils.AppUtils;

/**
 * ActivityDelegate默认实现类
 */
public class ActivityDelegateImpl implements ActivityDelegate {
    private Activity mActivity;
    private IActivity iActivity;

    public ActivityDelegateImpl(@NonNull Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //使用 EventBus 请将此方法返回 true
        if (iActivity.useEventBus()){
            //注册到事件主线
            EventBusManager.getInstance().register(mActivity);
        }

        //提供 AppComponent 对象给 BaseActivity 的子类, 用于 Dagger2 的依赖注入
        iActivity.setupActivityComponent(AppUtils.obtainAppComponentFromContext(mActivity));
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroy() {
        //使用 EventBus 请将此方法返回 true
        if (iActivity != null && iActivity.useEventBus())
            EventBusManager.getInstance().unregister(mActivity);
        this.iActivity = null;
        this.mActivity = null;
    }
}
