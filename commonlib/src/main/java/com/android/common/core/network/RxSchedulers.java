package com.android.common.core.network;

import com.android.common.core.ui.base.BaseActivity;
import com.android.common.core.util.NetworkUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import cn.aorise.common.R;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/11/29
 *     desc   : 联网请求
 *     version: 1.0
 * </pre>
 */
public class RxSchedulers {

    /**
     * 不带加载进度条和没有网络的提示语
     *
     * @param lifecycle
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {

                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycle);
            }
        };
    }

    /**
     * 带加载进度条和没有网络的提示语
     *
     * @param activity
     * @param lifecycle
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> compose(final BaseActivity activity, final LifecycleTransformer<T> lifecycle) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (NetworkUtils.isConnected()) {
                                    activity.showLoadingDialog();
                                } else {
                                    activity.showToast(R.string.common_label_no_net);
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycle);
            }
        };
    }
}
