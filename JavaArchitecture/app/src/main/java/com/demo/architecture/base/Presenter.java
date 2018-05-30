package com.demo.architecture.base;


/**
 * (可合理改动继承扩展)
 * Created by HeYingXin on 2017/2/16.
 */
public interface Presenter<V extends MvpView> {

    public abstract void attachView(V mvpView);

    public abstract void detachView();
}
