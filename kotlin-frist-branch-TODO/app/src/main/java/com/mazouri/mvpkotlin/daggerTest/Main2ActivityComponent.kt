package com.mazouri.mvpkotlin.daggerTest

import dagger.Component
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/16.
 */
//标注这个类，里面的方法的参数表示要将依赖注入到目标的位置(他是一个桥梁，连接注入者和被注入者)
@Singleton
@Component
interface Main2ActivityComponent {
    fun inject(activity:Main2Activity):Unit
}