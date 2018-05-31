package com.demo.architecture.api;

import com.demo.architecture.base.App;
import com.demo.architecture.base.ComponentHolder;
import com.demo.architecture.base.Constants;
import com.demo.architecture.utils.L;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HeYingXin on 2017/2/16.
 */

public class QClitent {

    private boolean DEBUG = true;

    private static QClitent mQClient;

    private OkHttpClient.Builder mBuilder;

    private QClitent() {
        initSetting();
    }

    public static QClitent getInstance() {
        if (mQClient == null) {
            synchronized (QClitent.class) {
                if (mQClient == null) {
                    mQClient = new QClitent();
                }
            }
        }
        return mQClient;
    }

    /**
     * 创建相应的服务接口
     */
    public <T> T create(Class<T> service) {
        String baseUrl = Constants.Server.BaseServer;
        checkNotNull(service, "service is null");
        checkNotNull(baseUrl, "baseUrl is null");

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

    private void initSetting() {
        //缓存
        int size = 1024 * 1024 * 100;
        Cache cache = null;
        try {
            if(ComponentHolder.getAppComponent().myApplication()!=null) {
                File cacheFile = new File(ComponentHolder.getAppComponent().myApplication().getCacheDir(), "NewsOkHttpCache");
                cache = new Cache(cacheFile, size);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //初始化OkHttp
        mBuilder = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(9, TimeUnit.SECONDS)    //设置连接超时 9s
                .readTimeout(10, TimeUnit.SECONDS);      //设置读取超时 10s
        mBuilder.addInterceptor(new RequestInterceptor());//请求拦截
        mBuilder.addInterceptor(new GetCacheInterceptor());//get请求缓存拦截
        mBuilder.addInterceptor(new PostCacheInterceptor());//post请求缓存
//        mBuilder.addInterceptor(new ProgressInterceptor());//下载进度拦截
        SSLSocketFactory sslSocketFactory = new SslContextFactory().getSslSocket().getSocketFactory();
        mBuilder.sslSocketFactory(sslSocketFactory);
        if (DEBUG) { // 判断是否为debug
            // 如果为 debug 模式，则添加日志拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    L.e("OKHTTP", "request====日志:"+message);
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.addInterceptor(interceptor);
        }
    }

}
