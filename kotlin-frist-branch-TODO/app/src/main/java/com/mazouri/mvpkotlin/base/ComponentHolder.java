package com.mazouri.mvpkotlin.base;


import com.mazouri.mvpkotlin.injection.component.ApplicationComponent;

/**
 * Created by HeYingXin on 2018/5/30.
 */
public class ComponentHolder {
    private static ApplicationComponent myAppComponent;

    public static void setAppComponent(ApplicationComponent component) {
        myAppComponent = component;
    }

    public static ApplicationComponent getAppComponent() {
        return myAppComponent;
    }
}
