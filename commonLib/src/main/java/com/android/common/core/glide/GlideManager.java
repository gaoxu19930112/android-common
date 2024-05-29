package com.android.common.core.glide;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.android.common.core.util.BaseLog;
import com.bumptech.glide.Glide;


/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : 图片处理模块
 *     version: 1.0
 * </pre>
 */
public class GlideManager {
    private static final String TAG = GlideManager.class.getSimpleName();

    private static GlideManager ourInstance = new GlideManager();

    public static GlideManager getInstance() {
        return ourInstance;
    }

    private GlideManager() {
    }

    /**
     * 加载图片
     *
     * @param context Activity, Fragment等上下文
     * @param view    ImageView对象
     * @param uri     图片地址
     * @param <T>     请求地址类型（URL FILE ASSETS）
     */
    public <T> void load(final Context context, ImageView view, T uri) {
        Glide.with(context)
                .load(uri)
                .into(view);
    }

    /**
     * 加载图片
     *
     * @param context     Activity, Fragment等上下文
     * @param view        ImageView对象
     * @param uri         图片地址
     * @param <T>         请求地址类型（URL FILE ASSETS）
     * @param placeholder 占位图
     * @param error       网络错误的图片
     */
    public <T> void load(final Context context, ImageView view, T uri, @DrawableRes int placeholder, @DrawableRes int error) {
        Glide.with(context)
                .load(uri)
                .placeholder(placeholder)
                .error(error)
                .into(view);
    }


    /**
     * 清空图片缓存
     *
     * @param context 上下文
     */
    public void shutDown(final Context context) {
        Glide.get(context).clearMemory();
    }
}
