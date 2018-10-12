package cn.aorise.common.component.network;

import cn.aorise.common.component.network.entity.response.AccountInfo;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.util.AppUtils;
import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 公共组件网络模块
 *     version: 1.0
 * </pre>
 */
public interface CmptApiService {
    @GET("v2/book/1220562")
    Observable<APIResult<AccountInfo>> getLogin();

    class Factory {
        public Factory() {
        }
        public static CmptApiService create() {
            return AoriseRetrofitFactory.getInstance().create(AppUtils.isAppDebug(), CmptApiService.class, CmptApi.BASE_URL);
        }
    }
}
