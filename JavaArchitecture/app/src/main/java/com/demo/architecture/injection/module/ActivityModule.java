package com.demo.architecture.injection.module;

import android.app.Activity;
import android.content.Context;


import com.demo.architecture.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HeYingXin on 2018/4/17.
 */
@Module
public class ActivityModule{

    private Activity mActivity;

    public ActivityModule(Activity activity){
        mActivity = activity;
    }

    @Provides
    Activity provideActivity(){
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }
}