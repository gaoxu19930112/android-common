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
@Deprecated
public abstract class APIMockCallback<T> extends APICallback<T> {
    /**
     * 测试数据
     *
     * @param t
     */
    public abstract void onMock(T t);
}
