package com.android.common.core.network;

import com.android.common.core.util.GsonUtils;

import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
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
