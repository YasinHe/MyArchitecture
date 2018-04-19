package com.mazouri.mvpkotlin.daggerTest.module

/**
 * Created by HeYingXin on 2018/4/16.
 */
class RetofitManager(client: OKHttpClientDemo) {
    private var client: OKHttpClientDemo = client

    fun getRetofitManager():OKHttpClientDemo{
        return client
    }
}