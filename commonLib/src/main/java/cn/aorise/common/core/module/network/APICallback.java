package cn.aorise.common.core.module.network;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 网络请求回调
 *     version: 1.0
 * </pre>
 */
public abstract class APICallback<T> {
    /**
     * 开始下载
     */
    // public abstract void onStart();

    /**
     * 下载发生错误
     *
     * @param e
     */
    public abstract void onError(Throwable e);

    /**
     * 下载完成
     */
    // public abstract void onCompleted();

    /**
     * 下载完成
     *
     * @param t
     */
    public abstract void onNext(T t);
}
