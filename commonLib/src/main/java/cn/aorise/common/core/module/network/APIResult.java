package cn.aorise.common.core.module.network;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 通用服务器返回对象
 *     version: 1.0
 * </pre>
 */
public class APIResult<T> {
    private static final String SUCCESS = "00000000";

    /**
     * 4位的返回码，成功返回 0
     */
    private String code;

    /**
     * 展示给用户阅读和理解的提示信息
     */
    private String viewMsg;

    /**
     * 调试信息输出
     */
    private String debugMsg;

    /**
     * 服务器返回本响应的时间戳
     */
    private String timestamp;

    /**
     * 特定请求相关的输出项
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getViewMsg() {
        return viewMsg;
    }

    public void setViewMsg(String viewMsg) {
        this.viewMsg = viewMsg;
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "APIResult{" +
                "code='" + code + '\'' +
                ", viewMsg='" + viewMsg + '\'' +
                ", debugMsg='" + debugMsg + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * 判断业务数据是否正常
     *
     * @return true 表示业务数据正常; false 表示业务数据不正常
     */
    public boolean isNormal() {
        return SUCCESS.equals(this.code);
    }
}
