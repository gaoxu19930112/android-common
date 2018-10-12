package cn.aorise.common.core.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : 多语言转换工具类
 *     version: 1.0
 * </pre>
 */
public class LanguageUtils {
    private static final String LOCALE = "locale";

    /**
     * 获取上一次APP设置的语言
     *
     * @param context 上下文
     * @return
     */
    public static Locale getAppLocale(Context context) {
        return (Locale) CacheUtils.getInstance().getSerializable(LOCALE);
    }

    /**
     * 切换APP语言
     *
     * @param context 上下文
     * @param local   对应的语言信息
     */
    private static void changeAppLanguage(Context context, Locale local) {
        if (null != local) {
            CacheUtils.getInstance().put(LOCALE, local);
            updateAppLocal(context, local);
        }
    }

    /**
     * 切换APP语言并且重启应用
     *
     * @param context 上下文
     * @param local   对应的语言信息
     * @param cls     重启APP后进入首页
     */
    public static void changeAppLanguage(Context context, Locale local, Class<?> cls) {
        changeAppLanguage(context, local);
        restartApp(context, cls);
    }

    private static void restartApp(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // 杀掉进程
        // android.os.Process.killProcess(android.os.Process.myPid());
        // System.exit(0);
    }

    /**
     * 更新APP语言
     *
     * @param context 上下文
     * @param local   语言信息
     */
    public static void updateAppLocal(Context context, Locale local) {
        if (null != local) {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration config = resources.getConfiguration();
            // 应用用户选择语言
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            config.setLocale(local);
//            context.createConfigurationContext(config);
//        } else {
//            config.locale = local;
//            resources.updateConfiguration(config, dm);
//        }
            config.locale = local;
            resources.updateConfiguration(config, dm);
        }
    }


    /**
     * 判断是否与设定的语言相同.
     *
     * @param context 上下文
     * @return true 表示没有切换过
     */
    public static boolean isSameWithSetting(Context context) {
        Locale current;
        // 7.0有多语言设置获取顶部的语言
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            current = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            current = context.getResources().getConfiguration().locale;
        }
        return current.equals(getAppLocale(context));
    }
}
