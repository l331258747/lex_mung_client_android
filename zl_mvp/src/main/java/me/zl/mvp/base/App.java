package me.zl.mvp.base;

import android.support.annotation.NonNull;

import me.zl.mvp.di.component.AppComponent;

/**
 * 每个 {@link android.app.Application} 都需要实现此类, 以满足规范
 */
public interface App {
    @NonNull
    AppComponent getAppComponent();
}
