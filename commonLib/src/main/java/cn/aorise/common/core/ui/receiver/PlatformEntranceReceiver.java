package cn.aorise.common.core.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import cn.aorise.common.core.config.AoriseConfig;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.util.AoriseLog;
import cn.aorise.common.core.util.AppUtils;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 接受广播进入首页
 *     version: 1.0
 * </pre>
 */
@Deprecated
public class PlatformEntranceReceiver extends BroadcastReceiver {
    private EntranceListener mListener;

    public PlatformEntranceReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (null != intent
                && AoriseConfig.BroadcastKey.CN_AORISE_PLATFORM_LOGIN_ACCOUNT.equals(action)) {
            Bundle bundle = intent.getExtras();
            String account = bundle.getString(AoriseConfig.AccountKey.USER_ACCOUNT);
            String id = bundle.getString(AoriseConfig.AccountKey.USER_ID);
            String sex = bundle.getString(AoriseConfig.AccountKey.USER_SEX);
            Log.d("PlatformReceiver", "Account:" + account + " ;ID:" + id + " ;Sex:" + sex);

            // TODO: 调试稳定后可以删除
            if (AppUtils.isAppDebug()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Account:").append(account).append("\r\n").append("ID:").append(id).append("\r\n")
                        .append("Sex:").append(sex);
                ((BaseActivity) context).showToast(sb.toString());
            }
        }
    }

    public void registerReceiver(Context context, EntranceListener entranceListener) {
        try {
            this.mListener = entranceListener;
            IntentFilter filter = new IntentFilter();
            filter.addAction(AoriseConfig.BroadcastKey.CN_AORISE_PLATFORM_LOGIN_ACCOUNT);
            context.registerReceiver(this, filter);
            AoriseLog.d("registerScreenReceiver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
            AoriseLog.d("unRegisterReceiver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface EntranceListener {
        void gotoTargetActivity();
    }
}