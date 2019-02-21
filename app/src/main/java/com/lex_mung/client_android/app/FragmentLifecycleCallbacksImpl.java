package com.lex_mung.client_android.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * 监听所有fragment的生命周期
 * 可以插入统一处理、逻辑等
 */
public class FragmentLifecycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        f.setRetainInstance(true);
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
    }
}
