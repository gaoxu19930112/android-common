package cn.aorise.common.core.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import cn.aorise.common.R;
import cn.aorise.common.core.config.AoriseConfig;
import cn.aorise.common.core.manager.ActivityManager;
import cn.aorise.common.core.module.glide.GlideManager;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : Utils 初始化相关
 *     version: 1.0
 * </pre>
 */
public final class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;


    private static Application.ActivityLifecycleCallbacks sLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            ActivityManager.getInstance().addActivity(activity);

            // 强制修改应用语言
            if (!LanguageUtils.isSameWithSetting(activity)) {
                LanguageUtils.updateAppLocal(activity, LanguageUtils.getAppLocale(activity));
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityManager.getInstance().removeActivity(activity);
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        Utils.sApplication = app;
        app.registerActivityLifecycleCallbacks(sLifecycleCallbacks);
    }

    /**
     * 获取 Application
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

//    private static void setTopActivityWeakRef(final Activity activity) {
//        if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;
//        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
//            sTopActivityWeakRef = new WeakReference<>(activity);
//        }
//    }

//    /**
//     * 开启APP严格模式
//     */
//    @Deprecated
//    public static void enabledStrictMode() {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll()
//                .penaltyLog()
//                .penaltyDeath()
//                .build());
//    }

    /**
     * 手机内置SD卡路径
     *
     * @return 手机内置SD卡路径
     */
    public static String getSdCard() {
        // String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        List<String> sdcard = SDCardUtils.getSDCardPaths(false);
        for (String sd : sdcard) {
            AoriseLog.d(TAG, sd);
        }
        return (ObjectUtils.isEmpty(sdcard)) ? null : sdcard.get(0);
    }

    /**
     * 获取APP在手机内置SD卡里面创建的根目录地址
     *
     * @param root APP包名
     * @return APP手机T卡根目录地址
     */
    public static String getRootPath(String root) {
        String absolutePath = getSdCard() + File.separator + root;
        AoriseLog.i(TAG, "getRootPath = " + absolutePath);
        return absolutePath;
    }

    /**
     * 获取APP在手机内置SD卡里面创建的下载、LOG等文件夹地址
     *
     * @param root    APP包名
     * @param relPath 下载、LOG等文件夹名字
     * @return 下载、LOG等文件夹在手机卡里面的绝对路径地址
     */
    public static String getSdPath(String root, String relPath) {
        String absolutePath = getRootPath(root) + File.separator + relPath;
        AoriseLog.i(TAG, "getSdPath = " + absolutePath);
        return absolutePath;
    }

    private static void makeFolders(String root) {
        if (SDCardUtils.isSDCardEnable()) {
            boolean isExists = false;
            isExists = FileUtils.createOrExistsDir(new File(getRootPath(root)));
            isExists = FileUtils.createOrExistsDir(new File(getSdPath(root, AoriseConfig.Folder.LOG_PATH)));
            isExists = FileUtils.createOrExistsDir(new File(getSdPath(root, AoriseConfig.Folder.CACHE_PATH)));
            isExists = FileUtils.createOrExistsDir(new File(getSdPath(root, AoriseConfig.Folder.DOWNLOAD_PATH)));
        } else {
            AoriseLog.d(TAG, "T卡无效");
        }
    }

    /**
     * 初始化APP环境
     * 创建文件夹<br>
     *
     * @param root 包名
     */
    public static void initAppEnvironment(String root) {
        makeFolders(root);
    }


    /**
     * 获取应用渠道ID
     *
     * @return
     */
    public static String getChannel() {
        String channel = AppUtils.getAppMetaName(AoriseConfig.CHANNEL);
        AoriseLog.i(TAG, "channel = " + channel);
        return channel;
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
    @Deprecated
    public static <T> void loadImage(final Context context, ImageView view, T uri,
                                     @DrawableRes int placeholder, @DrawableRes int error) {
        GlideManager.getInstance().load(context, view, uri, placeholder, error);
    }

    /**
     * 加载图片
     *
     * @param context Activity, Fragment等上下文
     * @param view    ImageView对象
     * @param uri     图片地址
     * @param <T>     请求地址类型（URL FILE ASSETS）
     */
    @Deprecated
    public static <T> void loadImage(final Context context, ImageView view, T uri) {
        GlideManager.getInstance().load(context, view, uri);
    }

    /**
     * 标记Intent
     *
     * @param context 上下文
     * @param bundle  数据
     * @param cls     跳转的页面
     * @return Intent
     */
    public static Intent getMaskIntent(Context context, Bundle bundle, Class<?> cls) {
        Intent intent = new Intent();
        // intent.putExtra(AoriseConstant.TransportKey.INTENT_KEY, bundle);
        intent.putExtras(bundle);
        intent.setClass(context.getApplicationContext(), cls);
        return intent;
    }

    /**
     * 标记Intent
     *
     * @param context 上下文
     * @param bundle  数据
     * @return Intent
     */
    public static Intent getMaskIntent(Context context, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * 获取序列化的数据
     *
     * @param intent intent对象
     * @return 序列化数据
     */
    public static Serializable getMaskSerializable(Intent intent) {
        if (null != intent) {
            // Bundle bundle = intent.getBundleExtra(AoriseConstant.TransportKey.INTENT_KEY);
            Bundle bundle = intent.getExtras();
            if (null != bundle) {
                return bundle.getSerializable(AoriseConfig.TransportKey.BUNDLE_KEY);
            }
        }
        return null;
    }

    /**
     * 创建绑定序列化数据的Bundle对象
     *
     * @param value 序列化对象
     * @return Bundle
     */
    public static Bundle getMaskBundle(Serializable value) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AoriseConfig.TransportKey.BUNDLE_KEY, value);
        return bundle;
    }


    /**
     * 通过布局文件获取view
     *
     * @param context  上下文
     * @param resource 布局文件ID
     * @param root     根布局view
     * @return View对象
     */

    public static View inflateLayout(Activity context, @LayoutRes int resource, @Nullable ViewGroup root) {
        // return LayoutInflater.from(context).inflate(R.layout.aorise_include_empty_tips, null);
        return LayoutInflater.from(context).inflate(resource, root, false);
    }

    /**
     * 获取公共列表为空的view
     *
     * @param context 上下文
     * @param root    根布局view
     * @return View对象
     */
    public static View inflateEmptyView(Activity context, @Nullable ViewGroup root) {
        return inflateLayout(context, R.layout.aorise_include_empty_tips, root);
    }

    /**
     * 获取公共列表底部加载更多的view
     *
     * @param context 上下文
     * @param root    根布局view
     * @return View对象
     */
    public static View inflateFooterView(Activity context, @Nullable ViewGroup root) {
        return inflateLayout(context, R.layout.aorise_include_load_more, root);
    }
}
