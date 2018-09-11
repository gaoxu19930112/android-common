package cn.aorise.common;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import cn.aorise.common.core.interfaces.IAppCycle;
import cn.aorise.common.core.manager.ActivityManager;
import cn.aorise.common.core.manager.AppManager;
import cn.aorise.common.core.module.database.DbHelper;
import cn.aorise.common.core.module.glide.GlideManager;
import cn.aorise.common.core.module.statistics.Statistics;
import cn.aorise.common.core.util.LanguageUtils;
import cn.aorise.common.core.util.Utils;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : Application基类
 *     version: 1.0
 * </pre>
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        create(this);

        for (IAppCycle appCycle : AppManager.getInstance().getList()) {
            appCycle.onCreate(this);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        for (IAppCycle appCycle : AppManager.getInstance().getList()) {
            appCycle.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        for (IAppCycle appCycle : AppManager.getInstance().getList()) {
            appCycle.onTrimMemory(level);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        for (IAppCycle appCycle : AppManager.getInstance().getList()) {
            appCycle.onConfigurationChanged(newConfig);
        }
    }

    /**
     * 初始化APP数据<br>
     * 初始化DEBUG模式、LOG开关等<br>
     * 初始化各个注册的plugin插件APP<br>
     *
     * @param context 上下文
     */
    private void create(Application context) {
        Log.i(TAG, "create");
        Utils.init(this);
        DbHelper.getInstance().init(this);
        LanguageUtils.updateAppLocal(this, LanguageUtils.getAppLocale(this));
        Statistics.getInstance().init(this);
        // AppManager.getsInstance().add(context);
        // AoriseLog.init(BuildConfig.LOG_DEBUG, BuildConfig.APPLICATION_ID);
        // AppManager.getsInstance().createAll(this);
    }

    /**
     * 销毁APP数据<br>
     * 释放图片缓存、释放页面管理、释放网络资源等<br>
     * 释放各个注册的plugin插件APP<br>
     *
     * @param context       上下文
     * @param isKillProcess 为true强制退出APP
     */
    public void destroy(Application context, boolean isKillProcess) {
        Log.i(TAG, "destroy");
        // MobclickAgent.onKillProcess(this);
        GlideManager.getInstance().shutDown(context);
        ActivityManager.getInstance().appExit(context);
        // RxAPIManager.getsInstance().cancelAll();

        // AppManager.getsInstance().destroyAll(context, isKillProcess);
        if (isKillProcess) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
