package com.android.common.core.network;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.common.core.config.BaseConfig;
import com.android.common.core.util.BaseLog;
import com.android.common.core.util.SPUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import okhttp3.*;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/11/29
 *     desc   : 联网请求
 *     version: 1.0
 * </pre>
 */
public class RetrofitFactory {
    private static RetrofitFactory sInstance;
    private HashMap<String, Object> services = new HashMap<>();
    private OkHttpClient.Builder mHttpBuilder = (new OkHttpClient()).newBuilder();
    private Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder();
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

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
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

    /**
     * 打印返回的json数据拦截器
     */
    private final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                BaseLog.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            BaseLog.e(
                    "打印url信息:" + request.url() + (request.body() != null ? "?" + _parseParams(request.body(),
                            requestBuffer) : ""));
            okhttp3.Response response = chain.proceed(request);
            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    //创建Session的拦截器
    private final Interceptor mSessionInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request.Builder builder = originalRequest.newBuilder();
            builder.removeHeader("Cookie");
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(BaseConfig.CacheKey.SESSION, null))) {
                builder.header("Cookie", SPUtils.getInstance().getString(BaseConfig.CacheKey.SESSION, null));
            }
            Request authorised = builder.build();
            return chain.proceed(authorised);
        }
    };
}
