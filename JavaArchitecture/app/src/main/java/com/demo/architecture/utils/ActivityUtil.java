package com.demo.architecture.utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.demo.architecture.ui.main.MainActivity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by HeYingXin on 2017/9/25.
 */
public class ActivityUtil {

    private static ActivityUtil au = null;

    public static ActivityUtil getInstance() {
        if (au == null) {
            au = new ActivityUtil();
        }
        return au;
    }

    public Stack<Activity> activityStack;

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(@NonNull Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(@NonNull Activity activity) {
        if (activityStack == null) {
            if (activity != null)
                activity.finish();
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity ac = iterator.next();
            if (activity != null && activity.getClass().equals(ac.getClass())) {
                iterator.remove(); // 注意这个地方
                activity.finish();
                activity = null;
            }
        }
        if (activity != null)
            activity.finish();
    }

    /**
     * 移除指定的Activity
     */
    public void RemoveActivity(@NonNull Activity activity) {
        if(activityStack!=null) {
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity ac = iterator.next();
                if (activity != null && activity.getClass().equals(ac.getClass())) {
                    iterator.remove(); // 注意这个地方
                }
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(@NonNull Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity ac = iterator.next();
            if (ac.getClass().equals(cls)) {
                iterator.remove(); // 注意这个地方
                finishActivity(ac);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity ac = iterator.next();
            if (ac != null) {
                iterator.remove(); // 注意这个地方
                ac.finish();
                ac = null;
            }
        }
        activityStack.clear();
        activityStack = null;
    }

    public void exitAccount(){
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity ac = iterator.next();
            if (ac != null&& !ac.getClass().getName().equals(MainActivity.class.getName())) {
                iterator.remove(); // 注意这个地方
                ac.finish();
                ac = null;
            }
        }
        activityStack.clear();
        activityStack = null;
    }
}
