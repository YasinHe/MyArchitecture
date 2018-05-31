package com.demo.architecture.injection.component;


import com.demo.architecture.injection.PerFragment;
import com.demo.architecture.injection.module.FragmentModule;

import dagger.Subcomponent;

/**
 * Created by HeYingXin on 2018/4/17.
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent{

}