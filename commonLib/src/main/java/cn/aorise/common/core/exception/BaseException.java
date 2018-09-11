package cn.aorise.common.core.exception;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : Exception基类
 *     version: 1.0
 * </pre>
 */
public class BaseException extends Exception {

    public BaseException() {
        this("Aorise Base Exception");
    }

    public BaseException(String detailMessage) {
        super(detailMessage);
    }

    public BaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }
}
