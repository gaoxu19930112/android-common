package com.android.common.core.updateapp;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.common.core.module.download.DownloadListener;
import com.android.common.core.module.download.DownloadTask;
import com.android.common.core.network.APICallback;
import com.android.common.core.network.APIObserver;
import com.android.common.core.network.APIResult;
import com.android.common.core.network.CommonAPIService;
import com.android.common.core.network.RxSchedulers;
import com.android.common.core.ui.base.BaseActivity;
import com.android.common.core.util.AppUtils;
import com.android.common.core.util.SPUtils;
import com.android.common.core.util.Utils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import cn.aorise.common.R;



/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/11
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class UpdateAppUtils {

    private static final String TAG = UpdateAppUtils.class.getSimpleName();

    public static void getAppVersion(BaseActivity activity, String baseUrl, String versionCode, int projectType, boolean isActive) {
        CommonAPIService.Factory.create(baseUrl).getAppVersion(versionCode, "1", projectType)
                .compose(RxSchedulers.compose(activity.bindUntilEvent(ActivityEvent.STOP)))
                .subscribe(new APIObserver<APIResult<List<RspAppVersion.AppVersionBean>>>(activity, new APICallback<APIResult<List<RspAppVersion.AppVersionBean>>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (isActive) {
                            activity.showToast("请求失败");
                        }
                    }

                    @Override
                    public void onNext(APIResult<List<RspAppVersion.AppVersionBean>> rspAppVersionAPIResult) {
                        List<RspAppVersion.AppVersionBean> beans = null;
//                        if (!rspAppVersionAPIResult.isNormal() || (beans = rspAppVersionAPIResult.getData().getList()) == null || beans.size() < 2) {
//                            return;
//                        }
                        RspAppVersion.AppVersionBean bean = new RspAppVersion.AppVersionBean(1, "1", "2", "快快体验新版本吧，好玩又刺激！");
                        bean.setSavePath("https://zhstatic.zhihu.com/pkg/store/zhihu/futureve-mobile-zhihu-release-5.8.2(596).apk");
                        showAppUpdate(activity, bean, isActive);
                    }
                }));
    }

    /**
     * @param activity
     * @return 是否显示弹窗
     */
    private static void showAppUpdate(BaseActivity activity, RspAppVersion.AppVersionBean appVersion, boolean isActive) {
        if (isActive || activity == null || appVersion == null) {
            activity.showToast("请求失败");
            return;
        }
        if (isActive || TextUtils.isEmpty(appVersion.getSoftPubversion()) || TextUtils.isEmpty(appVersion.getMinVersion())) {
            activity.showToast("请求失败");
            return;
        }
        if (isActive || appVersion.getForceUpdate() == 0) {
            activity.showToast("目前已经是最新版本");
            return;
        }
        //服务器的最新版本
        int softAppVersion = Integer.parseInt(appVersion.getSoftPubversion());
        //服务器允许的最低版本
        int minVersion = Integer.parseInt(appVersion.getMinVersion());
        if (isActive || softAppVersion <= AppUtils.getAppVersionCode()) {//当前软件版本大于等于服务器最新版本
            activity.showToast("目前已经是最新版本");
            return;
        }
        //用户是否忽略此版本
        if (!isActive || softAppVersion <= SPUtils.getInstance().getInt("AppVersion", -1)) {
            activity.showToast("目前已经是最新版本");
            return;
        }
        Dialog dialog = new Dialog(activity, R.style.FullHeightDialog);
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.visual_dialog_update_app_layout, null);

        TextView title = view.findViewById(R.id.tv_update_title);
        TextView content = view.findViewById(R.id.tv_content);
        CheckBox isOverlook = view.findViewById(R.id.radio_update);
        TextView cancle = view.findViewById(R.id.cancle);
        TextView confirm = view.findViewById(R.id.sure);
        LinearLayout ll_ignore = view.findViewById(R.id.ll_ignore);
        if (isActive) {
            ll_ignore.setVisibility(View.GONE);
        }
        ProgressBar progressBar = view.findViewById(R.id.progressBar_up);
        progressBar.setVisibility(View.GONE);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消
                if (isOverlook.isChecked()) {
                    //如果选择了就忽略更新
                    SPUtils.getInstance().put("AppVersion", softAppVersion);
                }
                dialog.cancel();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//确定升级
                v.setClickable(false);
                ll_ignore.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                DownloadTask task = new DownloadTask.Builder(appVersion.getSavePath()).updateProgress(true).build();
                task.enqueue(new DownloadListener() {
                    @Override
                    public void onReady() {

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(DownloadTask task) {

                    }

                    @Override
                    public void onFailed() {
                        dialog.cancel();
                        Toast.makeText(Utils.getApp(), "网络异常，请稍后下载", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        dialog.cancel();
                        AppUtils.installApp(task.getSaveFile(), Utils.getApp().getPackageName() + ".fileProvider");
                    }

                    @Override
                    public void onProgress(long readLength, long totalSize) {
                        progressBar.setProgress((int) (readLength * 100 / totalSize));
                    }

                    @Override
                    public void onPause() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        title.setText(R.string.common_about_version_update);
        if (AppUtils.getAppVersionCode() < minVersion) {//版本过低
            content.setText(R.string.common_version_update_info_by_low_version);
            cancle.setText("");
            ll_ignore.setVisibility(View.GONE);
            dialog.setCancelable(false);
        } else {
            content.setText(appVersion.getSoftContent());
        }
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.setContentView(view);
        dialog.show();
    }
}
