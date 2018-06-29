package com.demo.architecture.injection.component;

import com.demo.architecture.injection.PerActivity;
import com.demo.architecture.injection.module.MainActivityMoudel;
import com.demo.architecture.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by HeYingXin on 2018/5/31.
 */
@PerActivity
@Component(modules = MainActivityMoudel.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
