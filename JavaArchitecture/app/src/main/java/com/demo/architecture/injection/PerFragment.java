package com.demo.architecture.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by HeYingXin on 2018/4/17.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment{

}