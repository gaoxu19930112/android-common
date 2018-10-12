package cn.aorise.common.core.module.network;


import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import cn.aorise.common.BuildConfig;
import cn.aorise.common.core.config.AoriseConfig;
import cn.aorise.common.core.util.AoriseLog;
import cn.aorise.common.core.util.CacheUtils;
import okhttp3.*;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : 联网请求
 *     version: 1.0
 * </pre>
 */
public class RetrofitFactory {
    private static RetrofitFactory sInstance;
    private HashMap<String, Object> services = new HashMap<>();
    private OkHttpClient.Builder mHttpBuilder = (new OkHttpClient()).newBuilder();
    private retrofit2.Retrofit.Builder mRetrofitBuilder = new retrofit2.Retrofit.Builder();
    private Retrofit mRetrofit;

    public static RetrofitFactory getInstance() {
        if (sInstance == null) {
            Class var0 = RetrofitFactory.class;
            synchronized (RetrofitFactory.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitFactory();
                }
            }
        }

        return sInstance;
    }

    private RetrofitFactory() {
    }

    public OkHttpClient getOkHttpsClient(boolean debug) {
        try {
            X509TrustManager manager = new X509TrustManager() {
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }

                public void checkClientTrusted(javax.security.cert.X509Certificate[] chain, String authType) throws javax.security.cert.CertificateException {
                }

                public void checkServerTrusted(javax.security.cert.X509Certificate[] chain, String authType) throws javax.security.cert.CertificateException {
                }
            };
            TrustManager[] trustAllCerts = new TrustManager[]{manager};
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init((KeyManager[]) null, trustAllCerts, new SecureRandom());
            SSLSocketFactory factory = sc.getSocketFactory();
            this.mHttpBuilder.connectTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).sslSocketFactory(factory, manager).hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            if (debug) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                this.mHttpBuilder.addInterceptor(interceptor);
            }
            // this.mHttpBuilder.addInterceptor(mSessionInterceptor);

            return this.mHttpBuilder.build();
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public <T> T create(Class<T> service, String uri) {
        Object apiService = services.get(uri);
        if (apiService == null) {
            synchronized (services) {
                apiService = services.get(uri);
                if (apiService == null) {
                    mRetrofit = mRetrofitBuilder.baseUrl(uri)
                            .client(getOkHttpsClient(false))
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
                    apiService = mRetrofit.create(service);
                    services.put(uri, apiService);
                }
            }
        }
        return (T) apiService;
    }
}
