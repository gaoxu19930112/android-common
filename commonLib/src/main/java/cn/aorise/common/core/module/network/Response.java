package cn.aorise.common.core.module.network;

import java.io.Serializable;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/09/14
 *     desc   : 统一的联网请求响应实体（用公共库里面的APIResult）
 *     version: 1.0
 * </pre>
 */
public class Response implements Serializable {
//    /**
//     * 错误码
//     */
//    private String code;
//
//    /**
//     * 展示给用户阅读和理解的提示信息
//     */
//    private String message;
//
//    /**
//     * 服务器返回本响应的时间戳
//     */
//    private String timestamp;
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(String timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    @Override
//    public String toString() {
//        return "Response{" +
//                "code='" + code + '\'' +
//                ", message='" + message + '\'' +
//                ", timestamp='" + timestamp + '\'' +
//                '}';
//    }
//
//    /**
//     * 判断业务数据是否正常
//     *
//     * @return true 表示业务数据正常; false 表示业务数据不正常
//     */
//    public boolean isNormal() {
//        return StringUtils.isEmpty(this.code)
//                && StringUtils.isEmpty(this.message)
//                && StringUtils.isEmpty(this.timestamp);
//    }
}
