package com.android.frame.platform;

import androidx.databinding.DataBindingUtil;

import com.android.common.core.ui.base.BaseActivity;
import com.android.frame.platform.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        ActivityMainBinding binding =  ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void initEvent() {

    }
}
