package com.mazouri.mvpkotlin.utils;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.mazouri.mvpkotlin.base.MVPApplication;
import com.mazouri.mvpkotlin.base.ComponentHolder;
import com.mazouri.mvpkotlin.base.Constants;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by HeYingXin on 2018/6/26.
 * 他是不需要注册的，等级越高越优先拦截
 * 这个拦截器是用来拦截有的页面必须要先登录的
 */
@Interceptor(priority = 2, name = "login interceptor")
public class RouteLoginIntercopter implements IInterceptor {

    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        if(postcard.getPath().equals(Constants.Route.MAIN_ACTIVITY)){
                Observable.create((ObservableOnSubscribe<Boolean>) e -> {
                    e.onNext( MVPApplication.Companion.get(ComponentHolder.getAppComponent().context()).isLogin());
                    e.onComplete();
                }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean o) {
                        if(o) {
                            callback.onContinue(postcard);
                        }else {
//                          callback.onInterrupt(null);
                            T.showShort("您尚未登录");
                            //跳转到登录
                            callback.onContinue(postcard);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {

                    }
                });
        }else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        L.d("初始化路由拦截器");
    }
}
