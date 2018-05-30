package com.demo.architecture.api;

import android.os.Looper;
import android.support.annotation.UiThread;
import android.text.TextUtils;

import com.demo.architecture.base.BasePresenter;
import com.demo.architecture.model.BaseModel;
import com.demo.architecture.utils.WeakHandler;

import java.io.EOFException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by HeYingXin on 2017/7/19.
 */
public abstract class HttpObserver<T> implements Observer<T> {

    private WeakHandler handler;
    private Disposable disposable;

    public HttpObserver() {
        handler = new WeakHandler(Looper.getMainLooper());
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        showLoading();
    }

    @Override
    public void onNext(final T value) {
        try {

            if (((BaseModel) value).getStatus().getSucceed() != 1) {
                if (((BaseModel) value).getStatus().getError_code() == 100) {
                    com.demo.architecture.utils.T.showShort("您的账号已过期，请重新登录");
                    onFailure(new UnknownServiceException("token失效"));
                }
                if (!TextUtils.isEmpty(((BaseModel) value).getStatus().getError_desc())) {
//                        com.demo.architecture.utils.T.showShort(((BaseModel) value).getStatus().getError_desc());
                    com.demo.architecture.utils.T.showLong(((BaseModel) value).getStatus().getError_desc());
                } else {
                    com.demo.architecture.utils.T.showShort("获取失败");
                }
            } else {
                onSuccess(value);
            }

        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                onError(new BasePresenter.MvpViewNotAttachedException());
            } else {
                onSuccess(value);
            }
            e.printStackTrace();
        }
    }

    @Override
    public void onError(final Throwable e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (e instanceof SocketTimeoutException) {
                    com.demo.architecture.utils.T.showShort("网络连接超时");
                } else if (e instanceof SocketException) {
                    if (e instanceof ConnectException) {
                        com.demo.architecture.utils.T.showShort("网络未连接");
                    } else if (e instanceof BindException) {
                        com.demo.architecture.utils.T.showShort("网络错误,端口被占用");
                    } else {
                        com.demo.architecture.utils.T.showShort("网络错误");
                    }
                } else if (e instanceof EOFException) {
                    com.demo.architecture.utils.T.showShort("连接丢失");
                } else {
                    onFailure(e);
                }
                hideLoading();
            }
        });
    }

    @Override
    public void onComplete() {
        hideLoading();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    public Disposable getDisposable() {
        return disposable;
    }

    @UiThread
    public abstract void onFailure(Throwable e);

    @UiThread
    public abstract void onSuccess(T value);

    public void fromCache(T value) {
        hideLoading();
    }

    public void showLoading() {

    }

    public void hideLoading() {

    }
}