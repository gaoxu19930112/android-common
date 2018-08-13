package com.android.common.core.interfaces;

import android.app.Application;
import android.content.res.Configuration;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 模拟插件APP的生命周期管理
 *     version: 1.0
 * </pre>
 */
public interface IAppCycle {
//    /**
//     * 插件APP初始化
//     *
//     * @param context 上下文
//     */
//    void create(Application context);
//
//    /**
//     * 插件APP释放
//     *
//     * @param context       上下文
//     * @param isKillProcess 为true强制释放插件
//     */
//    void destroy(Application context, boolean isKillProcess);

//    void attachBaseContext(Context base);

    void onCreate(Application context);

    void onLowMemory();

    void onTrimMemory(int level);

    void onConfigurationChanged(Configuration newConfig);
}
