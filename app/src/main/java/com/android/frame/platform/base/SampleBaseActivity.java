package com.android.frame.platform.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.util.AoriseLog;


/**
 * Created by Administrator on 2016/3/14 0014.
 */
public abstract class SampleBaseActivity extends BaseActivity {
    private static final String TAG = SampleBaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoadingDialog() {
        super.showLoadingDialog();
        AoriseLog.i(TAG, "showLoadingDialog");
    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
        AoriseLog.i(TAG, "dismissLoadingDialog");
    }
}
