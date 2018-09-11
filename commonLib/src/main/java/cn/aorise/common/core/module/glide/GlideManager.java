package cn.aorise.common.core.module.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import cn.aorise.common.core.util.AoriseLog;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
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
                .listener(getRequestListener())
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
                .listener(getRequestListener())
                .into(view);
    }

    @NonNull
    private <T> RequestListener<T, GlideDrawable> getRequestListener() {
        return new RequestListener<T, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, T model, Target<GlideDrawable> target, boolean isFirstResource) {
                AoriseLog.e(TAG, e.toString());
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, T model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                AoriseLog.e(TAG, "onResourceReady");
                return false;
            }
        };
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
