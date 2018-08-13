package com.android.common.core.network;

import android.content.DialogInterface;
import android.text.TextUtils;

import com.android.common.core.ui.base.BaseActivity;
import com.android.common.core.util.BaseLog;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 模拟网络请求
 * 执行顺序 onStart 到 onNext 到 onCompleted
 *
 * @param <T>
 */
public class APIObserver<T> implements Observer<T> {
    private static final String TAG = APIObserver.class.getSimpleName();
    protected WeakReference<BaseActivity> mWeakReference;
    protected APICallback<T> mCallback;
    protected DialogInterface.OnCancelListener mListener;
    private Disposable mDisposable;

    /**
     * 网络请求
     *
     * @param activity 上下文
     * @param callback 回调接口
     */
    public APIObserver(BaseActivity activity, APICallback<T> callback) {
        this(activity, null, callback);
    }

    /**
     * 网络请求
     *
     * @param activity 上下文
     * @param listener 加载进度取消回调（暂时无效）
     * @param callback 回调接口
     */
    @Deprecated
    public APIObserver(BaseActivity activity, DialogInterface.OnCancelListener listener, APICallback<T> callback) {
        mWeakReference = new WeakReference<>(activity);
        mListener = listener;
        mCallback = callback;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        AoriseLog.i(TAG, "onStart");
//        if (mWeakReference.get() != null) {
//            if (Network.isAvailable(mWeakReference.get())) {
//                mWeakReference.get().showLoadingDialog(mListener);
//            } else {
//                mWeakReference.get().showToast(R.string.aorise_label_no_net);
//            }
//        }
//        mCallback.onStart();
//    }

    @Override
    public void onComplete() {
        BaseLog.i(TAG, "onCompleted");
        mDisposable.dispose();
        if (mWeakReference.get() != null) {
            mWeakReference.get().dismissLoadingDialog();
        }
        // mCallback.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        BaseLog.e(TAG, e.toString());
        mDisposable.dispose();
        if (mWeakReference.get() != null) {
            mWeakReference.get().dismissLoadingDialog();
        }
        mCallback.onError(e);
    }

    @Override
    public void onNext(T t) {
        BaseLog.i(TAG, "onNext");

        if (null == t) {
            BaseLog.d(TAG, "onNext is null");
        } else {
            BaseLog.i(TAG, t.toString());
        }

        if (mWeakReference.get() != null) {
            mWeakReference.get().dismissLoadingDialog();
        }

        // 处理统一的数据返回类型
        if (t instanceof APIResult) {
            APIResult response = (APIResult) t;
            if (!response.isNormal()) {
                if (!TextUtils.isEmpty(response.getViewMsg())) {
                    mWeakReference.get().showToast(response.getViewMsg());
                }
                BaseLog.d(TAG, response.toString());
            }
        } else {
            BaseLog.d(TAG, "非 APIResult 类型");
        }

        mCallback.onNext(t);
    }


    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }
}
