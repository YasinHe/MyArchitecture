package com.mazouri.mvpkotlin.injection.component

import com.mazouri.mvpkotlin.injection.PerFragment
import com.mazouri.mvpkotlin.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * Created by HeYingXin on 2018/4/17.
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent