package com.mazouri.mvpkotlin.daggerTest

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/16.
 */
//带构造参数的注入类
@Singleton
class Factory @Inject constructor(product: Product) {

    private var product:Product = product

    fun needProduct(needProductCount:Int){
        product.needWord = true
        for(i in 1..needProductCount){
            product.doWorker(i.toString())
        }
        product.needWord = false
    }
}