package com.demo.architecture.base;

import com.demo.architecture.injection.component.AppComponent;

/**
 * Created by HeYingXin on 2018/5/30.
 */
public class ComponentHolder {
    private static AppComponent myAppComponent;

    public static void setAppComponent(AppComponent component) {
        myAppComponent = component;
    }

    public static AppComponent getAppComponent() {
        return myAppComponent;
    }
}
