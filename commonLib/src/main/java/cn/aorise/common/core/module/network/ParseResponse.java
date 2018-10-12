package cn.aorise.common.core.module.network;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.util.AoriseLog;
import cn.aorise.common.core.util.GsonUtils;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2018/02/08
 *     desc   : 业务异常统一处理
 *     version: 1.0
 * </pre>
 */
@Deprecated
public class ParseResponse {
    private static final ParseResponse ourInstance = new ParseResponse();

    public static ParseResponse getInstance() {
        return ourInstance;
    }

    private ParseResponse() {
    }

    /**
     * 解析响应实体
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> T parseResponse(Response<ResponseBody> response, Type typeOfT) {
        return parseResponse(null, response, typeOfT);
    }


    /**
     * 解析响应实体
     *
     * @param activity
     * @param response
     * @param <T>
     * @return
     */
    public <T> T parseResponse(BaseActivity activity, Response<ResponseBody> response, Type typeOfT) {
        String body = null;

        try {
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (RspError.isNormal(body)) {
            return GsonUtils.fromJson(body, typeOfT);
        } else {
            RspError error = GsonUtils.fromJson(body, new TypeToken<RspError>() {
            }.getType());

            if (null != activity && null != error && null != error.getMessage()) {
                activity.showToast(error.getMessage());
                AoriseLog.d("ParseResponse", error.toString());
            } else {
                AoriseLog.d("ParseResponse", "RspError is empty!");
            }
        }
        return null;
    }

    public static class RspError {
        /**
         * 错误码
         */
        private String code;

        /**
         * 展示给用户阅读和理解的提示信息
         */
        private String message;

        /**
         * 服务器返回本响应的时间戳
         */
        private String timestamp;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }

        /**
         * 判断业务数据是否正常
         *
         * @return true 表示业务数据正常; false 表示业务数据不正常
         */
        public static boolean isNormal(String response) {
            if (null == response || response.length() == 0) {
                return false;
            }

            if (response.contains("code")
                    && response.contains("message")
                    && response.contains("timestamp")) {
                return false;
            }

            return true;
        }
    }
}
