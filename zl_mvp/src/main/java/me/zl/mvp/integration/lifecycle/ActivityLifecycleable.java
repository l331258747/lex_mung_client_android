package me.zl.mvp.integration.lifecycle;

import android.app.Activity;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * {@link Activity} 实现此接口,即可正常使用 {@link RxLifecycle}
 */
public interface ActivityLifecycleable extends Lifecycleable<ActivityEvent> {
}
