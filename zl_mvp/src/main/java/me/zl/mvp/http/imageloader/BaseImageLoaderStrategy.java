package me.zl.mvp.http.imageloader;

import android.content.Context;

/**
 * 图片加载策略,实现 {@link BaseImageLoaderStrategy}
 * 并通过 {@link ImageLoader#setLoadImgStrategy(BaseImageLoaderStrategy)} 配置后,才可进行图片请求
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    /**
     * 加载图片
     *
     * @param ctx Context
     * @param config T
     */
    void loadImage(Context ctx, T config);

    /**
     * 停止加载
     *
     * @param ctx Context
     * @param config T
     */
    void clear(Context ctx, T config);
}
