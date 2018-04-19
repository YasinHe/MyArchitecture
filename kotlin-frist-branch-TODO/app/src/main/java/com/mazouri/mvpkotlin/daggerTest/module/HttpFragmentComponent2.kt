package com.mazouri.mvpkotlin.daggerTest.module

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/16.
 */
//不可与Component同时
@Subcomponent
interface HttpFragmentComponent2 {
    fun inject(main2Fragment:Main2Fragment)
}