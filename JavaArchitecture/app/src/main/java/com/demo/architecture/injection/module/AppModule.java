package com.demo.architecture.injection.module;

import android.content.Context;

import com.demo.architecture.base.App;
import com.demo.architecture.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 全局变量对象初始化
 * Created by HeYingXin on 2018/5/30.
 */
@Module
public class AppModule {

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    //提供全局的Application对象
    @Singleton
    @Provides
    App provideApplication(){
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return application;
    }

    //还可以提供更多ToDo
}
