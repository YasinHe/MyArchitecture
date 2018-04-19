package com.mazouri.mvpkotlin.daggerTest.module

import dagger.Component
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/16.
 */
@Component(modules = arrayOf(HttpModule::class))
interface HttpActivityComponent {
    fun inject(activity:HttpActivity)
    fun provideRetofitManager():RetofitManager
    fun httpFragmentComponent2():HttpFragmentComponent2
    fun needDataBulider():HttpFragmentComponent3.NeedDataBulider
}