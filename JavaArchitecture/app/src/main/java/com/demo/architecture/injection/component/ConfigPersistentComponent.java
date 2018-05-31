package com.demo.architecture.injection.component;


import com.demo.architecture.injection.ConfigPersistent;
import com.demo.architecture.injection.module.ActivityModule;
import com.demo.architecture.injection.module.FragmentModule;

import dagger.Component;

/**
 * Created by HeYingXin on 2018/4/17.
 */
@ConfigPersistent
@Component(dependencies = AppComponent.class)
public interface ConfigPersistentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}