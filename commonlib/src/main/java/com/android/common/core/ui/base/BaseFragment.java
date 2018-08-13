package com.android.common.core.ui.base;


import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : Fragment基类
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment extends RxFragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
//        BaseApplication.getRefWatcher(getActivity()).watch(this);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        BaseApplication.getRefWatcher(getActivity()).watch(this);
//    }

    /**
     * 获取公共基类Activity
     *
     * @return BaseActivity对象
     */
    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
