package me.zl.mvp.integration.lifecycle;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import me.zl.mvp.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.subjects.Subject;

/**
 * {@link Activity}/{@link Fragment} 实现此接口,即可正常使用 {@link RxLifecycle}
 * 无需再继承 {@link RxLifecycle} 提供的 Activity/Fragment
 */
public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
