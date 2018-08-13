package com.android.common.core.network;

import com.android.common.core.util.GsonUtils;

import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/09/14
 *     desc   : 统一的联网请求实体
 *     version: 1.0
 * </pre>
 */
public class Request implements Serializable {
    public Request() {
    }

    public String toJson() {
        return GsonUtils.toJson(this);
    }


    public RequestBody toRequestBody() {
        return RequestBody.create(MediaType.parse("application/json"), this.toJson());
    }
}
