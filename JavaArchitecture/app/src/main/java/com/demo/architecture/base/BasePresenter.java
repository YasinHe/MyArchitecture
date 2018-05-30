package com.demo.architecture.base;

import android.support.annotation.UiThread;

import com.demo.architecture.api.HttpObservable;
import com.demo.architecture.api.HttpObserver;
import com.demo.architecture.utils.UiHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HeYingXin on 2017/2/16.
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;
    public CompositeDisposable compositeDisposable;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        hideLoadingProgress();
        mMvpView = null;
        compositeDisposable.clear();
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    //获取页面Context实例
    public T getMvpView() {
        return mMvpView;
    }

    //获取页面存活情况
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    //展示进度条
    @UiThread
    public void showLoadingProgress(String msg) {
        if(!Constants.Server.isUnitTest)
            UiHelper.showProgressDialog(mMvpView.getContext(),msg);
    }

    //隐藏进度条
    @UiThread
    public void hideLoadingProgress() {
        if(!Constants.Server.isUnitTest)
            UiHelper.dismissProgressDialog();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("MvpView页面错误，请检查代码");
        }
    }

    public void addTask(Observable observable, HttpObserver consumer) {
        if(observable instanceof HttpObservable){
            if(Constants.Server.isUnitTest){
                observable.subscribeOn(Schedulers.trampoline()).observeOn(Schedulers.trampoline());
            }else {
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
            ((HttpObservable)observable).subscribe(consumer);
        }else {
            if(Constants.Server.isUnitTest){
                observable.subscribeOn(Schedulers.trampoline()).observeOn(Schedulers.trampoline())
                        .subscribe(consumer);
            }else {
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer);
            }
        }
        compositeDisposable.add(consumer.getDisposable());
    }

}
