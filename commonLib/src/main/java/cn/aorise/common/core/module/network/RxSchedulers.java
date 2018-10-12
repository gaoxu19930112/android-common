package cn.aorise.common.core.module.network;

import com.trello.rxlifecycle2.LifecycleTransformer;

import cn.aorise.common.R;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.util.NetworkUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
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
                                    activity.showToast(R.string.aorise_label_no_net);
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycle);
            }
        };
    }
}
