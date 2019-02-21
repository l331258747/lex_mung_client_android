package me.zl.mvp.http.imageloader;

import android.content.Context;
import android.support.annotation.Nullable;

import me.zl.mvp.utils.Preconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 图片加载操作
 */
@Singleton
public final class ImageLoader {
    @Inject
    @Nullable
    BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader() {
    }


    /**
     * 加载图片
     *
     * @param context Context
     * @param config T
     * @param <T> ImageConfig
     */
    public <T extends ImageConfig> void loadImage(Context context, T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.loadImage(context, config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context Context
     * @param config T
     * @param <T> ImageConfig
     */
    public <T extends ImageConfig> void clear(Context context, T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.clear(context, config);
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     *
     * @param strategy BaseImageLoaderStrategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        Preconditions.checkNotNull(strategy, "strategy == null");
        this.mStrategy = strategy;
    }

    @Nullable
    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }
}
