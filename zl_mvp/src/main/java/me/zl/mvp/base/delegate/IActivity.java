package me.zl.mvp.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.cache.Cache;
import me.zl.mvp.integration.cache.LruCache;

/**
 * 每个 {@link Activity} 都需要实现此类,以满足规范
 */
public interface IActivity {

    /**
     * 提供在 {@link Activity} 生命周期内的缓存容器, 可向此 {@link Activity} 存取一些必要的数据
     *
     * @return like {@link LruCache}
     */
    @NonNull
    Cache<String, Object> provideCache();

    /**
     * 提供 AppComponent给实现类, 进行 Component 依赖
     *
     * @param appComponent AppComponent
     */
    void setupActivityComponent(@NonNull AppComponent appComponent);

    /**
     * 是否使用 EventBus
     *
     * @return true注册EventBus
     */
    boolean useEventBus();

    /**
     * 初始化 View, 如果返回0, 则不会调用 {@link Activity#setContentView(int)}
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    int initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState Bundle
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 这个 Activity 是否会使用 Fragment
     *
     * @return boolean
     */
    boolean useFragment();
}
