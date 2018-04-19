package com.mazouri.mvpkotlin.daggerTest.module

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/16.
 */
//不可与Component同时
@Subcomponent(modules = arrayOf(DataModule::class))
interface HttpFragmentComponent3 {

    fun inject(main2Fragment:Main2Fragment)
    fun providUser():User

    @Subcomponent.Builder
    interface NeedDataBulider{
        fun needData(dataModule: DataModule):NeedDataBulider
        fun build():HttpFragmentComponent3
    }
}