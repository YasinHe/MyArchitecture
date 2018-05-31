package com.demo.architecture.injection.component;

import android.content.Context;

import com.demo.architecture.base.App;
import com.demo.architecture.injection.ApplicationContext;
import com.demo.architecture.injection.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by HeYingXin on 2018/5/30.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(ActivityComponent activityComponent);

    App myApplication();

    @ApplicationContext
    Context context();
}
