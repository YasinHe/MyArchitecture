package com.mazouri.mvpkotlin.daggerTest.module

import dagger.Component
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/16.
 */
@Component(dependencies = arrayOf(HttpActivityComponent::class))
interface HttpFragmentComponent {
    fun inject(main2Fragment:Main2Fragment)
}