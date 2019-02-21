package me.zl.mvp.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import me.zl.mvp.utils.AppUtils;

import static me.zl.mvp.utils.Preconditions.checkNotNull;

/**
 * 每个 View 都需要实现此类, 以满足规范
 */
public interface IView {

    /**
     * 显示加载
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    default void showLoading(@NonNull String message) {

    }

    /**
     * 隐藏加载
     */
    default void hideLoading() {

    }

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    void showMessage(@NonNull String message);

    /**
     * 跳转 {@link Activity}
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    default void launchActivity(Intent intent) {
        checkNotNull(intent);
        AppUtils.startActivity(intent);
    }

    default void launchActivity(Intent intent, Bundle bundle) {
    }

    /**
     * 杀死自己
     */
    default void killMyself() {

    }
}
