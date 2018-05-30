package com.demo.architecture.api;

import com.demo.architecture.utils.CacheManager;
import com.demo.architecture.utils.L;
import com.demo.architecture.utils.NetworkUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 网络post请求拦截
 * Created by HeYingXin on 2017/7/10.
 */
public class PostCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if(NetworkUtils.isNetworkAvailable()) {//如果网络存在，就把请求结果缓存下来，网络不存在就不记录
            String url = request.url().toString();
            RequestBody requestBody = request.body();
            Charset charset = Charset.forName("UTF-8");
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (request.method().equals("POST")) {
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(Charset.forName("UTF-8"));
                }
                Buffer buffer = new Buffer();
                try {
                    requestBody.writeTo(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(buffer.readString(charset));
                buffer.close();
            }
            L.d(CacheManager.TAG, "EnhancedCacheInterceptor -> key:" + sb.toString());
            ResponseBody responseBody = response.body();
            MediaType contentType = responseBody.contentType();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            String key = sb.toString();
            //服务器返回的json原始数据
            String json = buffer.clone().readString(charset);

            CacheManager.getInstance().putCache(key, json);
            L.d(CacheManager.TAG, "put cache-> key:" + key + "-> json:" + json);
        }
        return response;
    }
}