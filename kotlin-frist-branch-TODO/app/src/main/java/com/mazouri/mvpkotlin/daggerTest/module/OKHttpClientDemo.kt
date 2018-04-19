package com.mazouri.mvpkotlin.daggerTest.module

/**
 * Created by HeYingXin on 2018/4/16.
 */
class OKHttpClientDemo {
    private var cacheSize:Int=0

    fun setCacheSize(size:Int) {
        this.cacheSize = size
    }

    fun getCacheSize():Int {
        return cacheSize
    }
}