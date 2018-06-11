package com.demo.architecture.injection.module;

import com.demo.architecture.ui.main.MainPersenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HeYingXin on 2018/5/31.
 */
@Module
public class MainActivityMoudel {

    private String tag;

    public MainActivityMoudel(String tag){
        this.tag = tag;
    }

    @Provides
    String providesTag(){
        return tag;
    }

//    @Provides
//    MainPersenter providesMainPersenter(String tag){
//        //任何修改构造都不应该修改类，而是修改Moudle
//        return new MainPersenter(tag);
//    }
}
