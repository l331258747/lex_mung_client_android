package me.zl.mvp.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.cache.Cache;
import me.zl.mvp.integration.cache.LruCache;

/**
 * 每个 {@link Fragment} 都需要实现此类,以满足规范
 */
public interface IFragment {

    /**
     * 提供在 {@link Fragment} 生命周期内的缓存容器, 可向此 {@link Fragment} 存取一些必要的数据
     *
     * @return like {@link LruCache}
     */
    @NonNull
    Cache<String, Object> provideCache();

    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent AppComponent
     */
    void setupFragmentComponent(@NonNull AppComponent appComponent);

    /**
     * 是否使用 EventBus
     *
     * @return true注册 EventBus
     */
    boolean useEventBus();

    /**
     * 初始化 View
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState Bundle
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 与外部交互，比如 {@link Activity}
     *
     * @param data data
     */
    void setData(@Nullable Object data);
}
