package com.lex_mung.client_android.app;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lex_mung.client_android.R;

/**
 * 监听所有activity的生命周期
 * 可以插入统一处理、逻辑等
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!activity.getIntent().getBooleanExtra("isInitToolbar", false)) {
            activity.getIntent().putExtra("isInitToolbar", true);
            if (activity.findViewById(R.id.toolbar) != null) {
                if (activity instanceof AppCompatActivity) {
                    ((AppCompatActivity) activity).setSupportActionBar(activity.findViewById(R.id.toolbar));
                    ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.setActionBar(activity.findViewById(R.id.toolbar));
                        activity.getActionBar().setDisplayShowTitleEnabled(false);
                    }
                }
            }
            if (activity.findViewById(R.id.tv_title) != null
                    && !activity.getTitle().equals(activity.getResources().getText(R.string.app_name))) {
                ((TextView) activity.findViewById(R.id.tv_title)).setText(activity.getTitle());
            }
            if (activity.findViewById(R.id.iv_back) != null) {
                activity.findViewById(R.id.iv_back).setOnClickListener(v -> activity.onBackPressed());
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activity.getIntent().removeExtra("isInitToolbar");
    }
}
