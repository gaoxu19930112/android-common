package cn.aorise.common.component.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

import cn.aorise.common.component.network.entity.response.AccountInfo;
import cn.aorise.common.core.config.AoriseConfig;
import cn.aorise.common.core.util.AoriseLog;
import cn.aorise.common.core.util.AppUtils;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 组件工具类
 *     version: 1.0
 * </pre>
 */
public class CmptUtils {
    private static final String TAG = "common.CmptUtils";

    public static final String PACKAGE_NAME_KEY = "PACKAGE_NAME_KEY";
    public static final String LOGIN_CLASS_NAME_KEY = "LOGIN_CLASS_NAME_KEY";

    /**
     * common
     **********************************************************************************************/


    /**
     * private
     **********************************************************************************************/

    /**
     * 进入配置的首页
     *
     * @param activity    Activity上下文
     * @param accountInfo 登录后返回的账号信息
     */
    @Deprecated
    public static void gotoMainActivity(Activity activity, AccountInfo accountInfo) {
        String packageName = AppUtils.getAppMetaName(PACKAGE_NAME_KEY);
        String className = AppUtils.getAppMetaName(LOGIN_CLASS_NAME_KEY);
        AoriseLog.d("packageName:" + packageName);
        AoriseLog.d("className:" + className);
        if (null == packageName || null == className) {
            AoriseLog.e("请配置首页跳转地址");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setComponent(new ComponentName(packageName, className));

        Bundle bundle = new Bundle();
        bundle.putString(AoriseConfig.AccountKey.USER_ACCOUNT,
                (null == accountInfo) ? "" : accountInfo.getAccount());
        bundle.putString(AoriseConfig.AccountKey.USER_ID,
                (null == accountInfo) ? "" : accountInfo.getId());
        bundle.putString(AoriseConfig.AccountKey.USER_SEX,
                (null == accountInfo) ? "" : accountInfo.getSex());
        intent.putExtras(bundle);

        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 进入配置的目标页面
     *
     * @param activity Activity上下文
     * @param value    透传数据
     */
    public static void gotoTargetActivity(Activity activity, Serializable value) {
        String packageName = AppUtils.getAppMetaName(PACKAGE_NAME_KEY);
        String className = AppUtils.getAppMetaName(LOGIN_CLASS_NAME_KEY);
        gotoTargetActivity(activity, value, packageName, className);
    }

    /**
     * 进入配置的目标页面
     *
     * @param activity    Activity上下文
     * @param value       透传数据
     * @param packageName 跳转页面的APP包名 "cn.aorise.sample"
     * @param className   跳转页面的class类名 "cn.aorise.sample.ui.activity.MainActivity"
     */
    public static void gotoTargetActivity(Activity activity, Serializable value, String packageName, String className) {
        AoriseLog.d("packageName:" + packageName);
        AoriseLog.d("className:" + className);
        if (null == packageName || null == className) {
            AoriseLog.e("请配置首页跳转地址");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setComponent(new ComponentName(packageName, className));

        Bundle bundle = new Bundle();
        bundle.putSerializable(AoriseConfig.TransportKey.BUNDLE_KEY, value);
        intent.putExtras(bundle);

        activity.startActivity(intent);
        activity.finish();
    }
}
