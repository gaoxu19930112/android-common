package com.android.frame.platform;

import android.databinding.DataBindingUtil;

import com.android.frame.platform.base.SampleBaseActivity;


public class MainActivity extends SampleBaseActivity {


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
