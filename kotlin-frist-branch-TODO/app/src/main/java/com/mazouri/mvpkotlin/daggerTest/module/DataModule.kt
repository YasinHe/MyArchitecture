package com.mazouri.mvpkotlin.daggerTest.module

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/17.
 */
@Module
class DataModule(private val login: String, private val url: String){
    @Provides
    fun provideUser():User{
        return User(login, url)
    }
}