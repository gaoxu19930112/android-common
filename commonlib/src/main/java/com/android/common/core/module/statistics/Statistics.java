package com.android.common.core.module.statistics;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 第三方统计
 *     version: 1.0
 * </pre>
 */
public class Statistics {
    private static final String TAG = Statistics.class.getSimpleName();
    private static Statistics ourInstance = new Statistics();
    // private static Context sCtx;

    public static Statistics getInstance() {
        return ourInstance;
    }

    private Statistics() {
    }


    /**
     * 第三方统计初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        if (!(context instanceof Application)) {
            throw new AssertionError();
        }
        // sCtx = context;

        /** 友盟继承测试集成. */
        // String device = getDeviceInfo(context);
        // AoriseLog.d(TAG, device);

        /** 使用集成测试模式请先在程序入口处调用如下代码，打开调试模式. */
        // MobclickAgent.setDebugMode(true);

        /** 禁止默认的页面统计方式，这样将不会再自动统计Activity. */
        // MobclickAgent.openActivityDurationTrack(false);

        /* 场景类型设置接口 */
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);

        /** 如不需要错误统计功能，可通过此方法关闭. */
        // MobclickAgent.setCatchUncaughtExceptions(false);

        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        // MobclickAgent.enableEncrypt(false);
    }


    /**
     * 友盟集成测试<br>
     * {"device_id":"864527020392731","mac":"ac:38:70:24:99:69"}
     *
     * @param context
     * @return
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
