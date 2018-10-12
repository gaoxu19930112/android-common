package cn.aorise.common.component.network;


import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import cn.aorise.common.core.config.AoriseConfig;
import cn.aorise.common.core.util.AoriseLog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 组件Retrofit工厂方法
 *     version: 1.0
 * </pre>
 */
public class AoriseRetrofitFactory {
    private static AoriseRetrofitFactory sInstance;
    // private OkHttpClient mOkHttpClient;
    private OkHttpClient.Builder mHttpBuilder;
    private Retrofit.Builder mRetrofitBuilder;
    private Retrofit mRetrofit;

    public static AoriseRetrofitFactory getInstance() {
        if (sInstance == null) {
            synchronized (AoriseRetrofitFactory.class) {
                if (sInstance == null) {
                    sInstance = new AoriseRetrofitFactory();
                }
            }
        }
        return sInstance;
    }


    private AoriseRetrofitFactory() {
        mHttpBuilder = new OkHttpClient().newBuilder();
        mRetrofitBuilder = new Retrofit.Builder();
    }


    public OkHttpClient getOkHttpsClient(boolean debug) {
        try {
            X509TrustManager manager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            };
            TrustManager[] trustAllCerts = new TrustManager[]{manager};

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory factory = sc.getSocketFactory();

            mHttpBuilder
                    .connectTimeout(AoriseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(AoriseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(AoriseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(factory, manager)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request request = chain.request()
//                                    .newBuilder()
//                                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                    .addHeader("Accept-Encoding", "gzip, deflate")
//                                    .addHeader("Connection", "keep-alive")
//                                    .addHeader("Accept", "*/*")
//                                    .addHeader("Cookie", "add cookies here")
//                                    .build();
//                            return chain.proceed(request);
//                        }
//                    });

            if (debug) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                mHttpBuilder.addInterceptor(interceptor);
            }

            return mHttpBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T create(boolean debug, final Class<T> service, String uri) {
        AoriseLog.i("AoriseRetrofitFactory", "debug = " + debug);

        if (null != mHttpBuilder && null != mRetrofitBuilder) {
            OkHttpClient client = getOkHttpsClient(debug);

            mRetrofit = mRetrofitBuilder.baseUrl(uri)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit.create(service);
    }
}
