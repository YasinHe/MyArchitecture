package com.demo.architecture.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.demo.architecture.base.Constants;
import com.demo.architecture.utils.update.FileCallBack;

import java.io.IOException;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HeYingXin on 2017/2/16.
 */

public class QClitentDownload {

    private static QClitentDownload mQClient;

    private OkHttpClient.Builder mBuilder;

    public static QClitentDownload getInstance() {
        if (mQClient == null) {
            synchronized (QClitentDownload.class) {
                if (mQClient == null) {
                    mQClient = new QClitentDownload();
                }
            }
        }
        return mQClient;
    }

    /**
     * 创建相应的服务接口
     */
    public <T> T create(Class<T> service, final FileCallBack<ResponseBody> callback) {
        String baseUrl = Constants.Server.BaseServer;
        checkNotNull(service, "service is null");
        checkNotNull(baseUrl, "baseUrl is null");

        //初始化OkHttp
        mBuilder = new OkHttpClient.Builder();
        mBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(),callback)).build();
            }
        });//下载进度拦截
        SSLSocketFactory sslSocketFactory = new SslContextFactory().getSslSocket().getSocketFactory();
        mBuilder.sslSocketFactory(sslSocketFactory);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(service);
    }

    private <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

}
