package me.zl.mvp.integration.lifecycle;

import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * {@link Fragment} 实现此接口,即可正常使用 {@link RxLifecycle}
 */
public interface FragmentLifecycleable extends Lifecycleable<FragmentEvent> {
}
