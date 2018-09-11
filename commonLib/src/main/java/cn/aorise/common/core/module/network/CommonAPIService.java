package cn.aorise.common.core.module.network;

import java.util.List;

import cn.aorise.common.core.module.updateapp.RspAppVersion;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/07/25
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public interface CommonAPIService {

    /**
     * 获取版本更新信息
     *
     * @param
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("/api/appUpdate/softwaredVersion/select")
    Observable<APIResult<List<RspAppVersion.AppVersionBean>>> getAppVersion(@Query("softPubversion") String softPubversion
            , @Query("softType") String softType, @Query("projectType") int projectType);

    /*断点续传下载接口*/
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);

    class Factory {

        public static CommonAPIService create(final String url) {
            return RetrofitFactory.getInstance().create(
                    CommonAPIService.class, url);
        }
    }
}
