package com.mazouri.mvpkotlin.daggerTest

import android.util.Log
import javax.inject.Inject

/**
 * Created by HeYingXin on 2018/4/16.
 */
//需要注入的对象的构造器使用@Inject标注，意思是可以实例化这个类
class Product @Inject constructor(){
    var needWord:Boolean = false
    fun doWorker(string:String){
        if(needWord)
            Log.e("TAG",string)
    }
}
