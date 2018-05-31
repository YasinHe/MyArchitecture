package com.demo.architecture.injection.component;

import com.demo.architecture.base.BaseActivity;
import com.demo.architecture.injection.PerActivity;
import com.demo.architecture.injection.module.ActivityModule;
import com.demo.architecture.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by HeYingXin on 2018/4/17.
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);
    void inject(MainActivity mainActivity);
}