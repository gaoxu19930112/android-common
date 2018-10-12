package com.android.frame.platform;

import android.databinding.DataBindingUtil;

import com.android.common.core.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        DataBindingUtil.setContentView(this, R.layout.activity_second);
    }

    @Override
    protected void initEvent() {
    }
}
