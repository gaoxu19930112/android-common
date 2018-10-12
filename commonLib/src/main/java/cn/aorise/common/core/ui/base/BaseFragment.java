package cn.aorise.common.core.ui.base;


import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
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
