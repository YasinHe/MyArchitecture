package com.mazouri.mvpkotlin.injection.module

import android.app.Application
import android.content.Context
import com.mazouri.mvpkotlin.injection.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/17.
 */
@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }


}