package com.demo.architecture.api;

import com.demo.architecture.utils.NetworkUtils;

import java.io.IOException;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HeYingXin on 2017/7/8.
 */
class GetCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (request.method().equals("GET")) {
            //无网络时强制使用缓存
            if (!NetworkUtils.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//只走缓存
                        .build();
            }
            if (NetworkUtils.isNetworkAvailable()) {
                // 有网络时，设置超时为0(如果首页请求数据，视频文章  没有那么快更新，可以修改策略)
                int maxStale = 0;
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            } else {
                // 无网络时，设置超时为3周
                int maxStale = 3 * 7 * 24 * 60 * 60;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
        return response;
    }

}