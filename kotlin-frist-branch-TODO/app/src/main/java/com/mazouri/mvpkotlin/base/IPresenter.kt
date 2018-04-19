package com.mazouri.mvpkotlin.base

/**
 * Created by HeYingXin on 2018/4/12.
 */
interface IPresenter<in V:IView> {

    fun attachView(view:V)

    fun detachView()
}