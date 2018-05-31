package com.demo.architecture.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.demo.architecture.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HeYingXin on 2018/4/17.
 */
@Module
public class FragmentModule{

    private Fragment mFragment;

    public FragmentModule(Fragment fragment){
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment(){
        return mFragment;
    }
    @Provides
    Activity provideActivity(){
        return mFragment.getActivity();
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return (Context)mFragment.getActivity();
    }
}