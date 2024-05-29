package com.android.common.core.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.common.core.config.BaseConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;


/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : Glide配置类
 *     version: 1.0
 * </pre>
 */
public class GlideModelConfig implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,BaseConfig.CACHE_DISK_DIR, BaseConfig.MAX_DISK_CACHE_SIZE));  //内存中
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, BaseConfig.CACHE_DISK_DIR,BaseConfig.MAX_DISK_CACHE_SIZE)); //sd卡中

        // 自定义内存和图片池大小
        builder.setMemoryCache(new LruResourceCache(BaseConfig.MAX_MEMORY_CACHE_SIZE));
        builder.setBitmapPool(new LruBitmapPool(BaseConfig.MAX_MEMORY_CACHE_SIZE));

        // 定义图片格式
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认
    }


    /**
     * Lazily register components immediately after the Glide singleton is created but before any
     * requests can be started.
     *
     * <p>This method will be called once and only once per implementation.
     *
     * @param context  An Application {@link Context}.
     * @param glide    The Glide singleton that is in the process of being initialized.
     * @param registry An {@link Registry} to use to register components.
     */
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
