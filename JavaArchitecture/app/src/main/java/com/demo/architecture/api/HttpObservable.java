package com.demo.architecture.api;

import android.text.TextUtils;

import com.demo.architecture.base.BasePresenter;
import com.demo.architecture.utils.CacheManager;
import com.demo.architecture.utils.L;
import com.demo.architecture.utils.NetworkUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.subscriptions.AsyncSubscription;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HeYingXin on 2017/7/10.
 *
 * 例子
 *
     * HttpSubscribe subscribe = new HttpSubscribe() {
    @Override
    public void onComplete() {
    Log.e("AG","AA");
    }

    @Override
    public void onError(Throwable t) {
    Log.e("AG","AA");
    }

    @Override
    public void onNext(Object value) {
    Log.e("AG","AA");
    }

    @Override
    public void onSubscribe(Subscription s) {
    Log.e("AG","AA");
    }

    @Override
    public void fromCache(Object value) {
    Log.e("AG","AA");
    }
    };
     new HttpObservable(QClitent.getInstance().create(QHttpService.class).postLogin2(Constants.Http.YLL_LOGIN,map))
     .dataClassName(LoginModel.class).useCache(true).subscribe(subscribe);
 */
public class HttpObservable<T> extends Observable<T>{

    private boolean mUseCache = true;
    private Call<T> mCall;
    private Class dataClassName;
    private AsyncSubscription subscription;

    public HttpObservable(Call<T> call) {
        this.mCall = call;
    }
    /**
     * 是否使用缓存 默认使用
     */
    public HttpObservable<T> useCache(boolean useCache) {
        subscription = new AsyncSubscription();
        mUseCache = useCache;
        return this;
    }

    /**
     * Gson反序列化缓存时 需要获取到泛型的class类型
     */
    public HttpObservable<T> dataClassName(Class className) {
        dataClassName = className;
        return this;
    }

    public void subscribe(final HttpObserver subscribe){
        subscribe.onSubscribe(subscription);
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Request request = call.request();
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
                Object obj = response.body();
                if (obj != null) {
                    subscribe.onNext((T) obj);
                }else{
                    onFailure(call,new BasePresenter.MvpViewNotAttachedException());
                }
                subscribe.onComplete();
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
//                subscribe.onSubscribe(subscription);
                if (!mUseCache || NetworkUtils.isNetworkAvailable()){
                    //不使用缓存 或者网络可用 的情况下直接回调onFailure
                    subscribe.onError(t);
                    return;
                }
                Request request = call.request();
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

                String cache = CacheManager.getInstance().getCache(sb.toString());
                L.d(CacheManager.TAG, "get cache->" + cache);

                if (!TextUtils.isEmpty(cache) && dataClassName != null) {
                    Object obj = new Gson().fromJson(cache, dataClassName);
                    if (obj != null) {
                        subscribe.fromCache((T) obj);
                    }else{
                        subscribe.onError(t);
                    }
                }else{
                    subscribe.onError(t);
                }
                subscribe.onComplete();
                L.d(CacheManager.TAG, "onFailure->" + t.getMessage());
            }
        });
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {

    }
}