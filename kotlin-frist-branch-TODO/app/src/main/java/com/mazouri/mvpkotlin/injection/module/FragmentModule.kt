package com.mazouri.mvpkotlin.injection.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.mazouri.mvpkotlin.injection.ActivityContext
import dagger.Module
import dagger.Provides

/**
 * Created by HeYingXin on 2018/4/17.
 */
@Module
class FragmentModule(private val mFragment: Fragment) {

    @Provides
    internal fun provideFragment(): Fragment {
        return mFragment
    }
    @Provides
    internal fun provideActivity(): Activity {
        return mFragment.activity as Activity
    }

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mFragment.activity as Context
    }
}