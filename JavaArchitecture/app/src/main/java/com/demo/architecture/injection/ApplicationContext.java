package com.demo.architecture.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by HeYingXin on 2018/4/17.
 */
//自定义注解
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext{

}