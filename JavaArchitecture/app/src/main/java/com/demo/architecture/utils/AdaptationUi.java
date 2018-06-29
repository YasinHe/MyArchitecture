package com.demo.architecture.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * Created by HeYingXin on 2018/6/28.
 * 适配方案
 */
public class AdaptationUi {

    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;

    //此方法在Application的onCreate方法中调用
    public static void setDensity(@NonNull Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (appDensity == 0) {
            //初始化的时候赋值（只在Application里面初始化的时候会调用一次）
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;
            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }
                @Override
                public void onLowMemory() {
                }
            });
        }
        //调用修改density值的方法(默认以宽度作为基准)
        setAppOrientation(null, "width");
    }

    //此方法用于在某一个Activity里面更改适配的方向    Density.setOrientation(mActivity, "width/height");
    public static void setOrientation(Activity activity, String orientation) {
        setAppOrientation(activity, orientation);
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * orientation:方向值,传入width或height
     */
    private static void setAppOrientation(@Nullable Activity activity, String orientation) {
        float targetDensity = 0;
        //根据带入参数选择不同的适配方向
        if (orientation.equals("height")) {
            targetDensity = appDisplayMetrics.heightPixels/667;//设计图高度
        } else {
            targetDensity = appDisplayMetrics.heightPixels/360;//设计图宽度
        }
        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);
        if (activity != null) {
            DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = targetDensity;
            activityDisplayMetrics.scaledDensity = targetScaledDensity;
            activityDisplayMetrics.densityDpi = targetDensityDpi;
        } else {
            appDisplayMetrics.density = targetDensity;
            appDisplayMetrics.scaledDensity = targetScaledDensity;
            appDisplayMetrics.densityDpi = targetDensityDpi;
        }
    }
}
