package com.mazouri.mvpkotlin.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import rx.Subscription
import rx.subscriptions.CompositeSubscription


//Kotlin 里类默认都是final的,如果声明的类允许被继承则需要使用open关键字来描述类
//接口继承class Child : MyInterface{}
/**
 * Created by HeYingXin on 2018/4/12.
 */
open class BasePresenter<T:IView> : IPresenter<T> {

    //setter方法
    //也可以写成一行(需要加分号),var view: T?=null;private set
    var view: T? = null
        private set // setter 是私有的并且有默认的实现

    private val mCompositeSubscription = CompositeDisposable()//CompositeDisposable  CompositeSubscription

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        view = null

        if (!mCompositeSubscription.isDisposed) {
            mCompositeSubscription.clear()
        }
    }

    //getter方法
    val isViewAttached: Boolean
        get() = view != null

    fun checkViewAttached() {
        if (!isViewAttached) throw ViewNotAttachedException()
    }

    fun addSubscription(subs: Disposable) {
        mCompositeSubscription.add(subs)
    }

    //internal 修饰符是指成员的可见性是只在同一个模块(module)中才可见的
    private class ViewNotAttachedException internal constructor() : RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")


}