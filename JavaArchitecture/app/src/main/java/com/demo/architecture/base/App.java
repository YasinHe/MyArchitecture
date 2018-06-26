package com.demo.architecture.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.demo.architecture.db.DBImpl;
import com.demo.architecture.db.DaoManager;
import com.demo.architecture.injection.component.AppComponent;
import com.demo.architecture.injection.component.DaggerAppComponent;
import com.demo.architecture.injection.module.AppModule;
import com.demo.architecture.utils.ActivityUtil;
import com.demo.architecture.utils.CrashExecptionHandler;
import com.demo.architecture.utils.SPUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import static com.just.library.LogUtils.isDebug;

/*SampleApplication*/
public class App extends Application {
    public int systemLive;
    //数据库
    public DBImpl mDB;
    //内存泄漏监测工具
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        systemLive = 0;
        init();
    }

    private void init() {
        //路由
        if (Constants.SystemConfig.isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        //调试内存泄漏
        mRefWatcher = LeakCanary.install(this);
        //配置数据库
        mDB = new DBImpl(this);
        //注入
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        ComponentHolder.setAppComponent(appComponent);
        //Carsh捕获上传
        if(!Constants.SystemConfig.isDebug) {
            CrashExecptionHandler.getInstance().init(this);
            CrashExecptionHandler.getInstance().sendPreviousReportsToServer();
        }
    }

    public void exitSystemEnterLogin(Context context) {
        SPUtils.put(Constants.UserConfig.USER_ID, "");
        SPUtils.put(Constants.UserConfig.TOKEN, "");
    }


    public boolean isLogin() {
        String id = (String) SPUtils.get(Constants.UserConfig.USER_ID, "");
        String token = (String) SPUtils.get(Constants.UserConfig.TOKEN, "");
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(token)) {
            return false;
        }
        return true;
    }

    /**
     * 退出应用，清除数据
     */
    public void appExit() {
        DaoManager.getInstance().closeConnection();
        ActivityUtil.getInstance().finishAllActivity();
        ActivityManager activityMgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.killBackgroundProcesses(getPackageName());
        System.exit(0);
    }

    /**
     * 退出账户
     *
     * @param context
     */
    public void exitAccount(Context context) {
        SPUtils.put(Constants.UserConfig.USER_ID, "");
        SPUtils.put(Constants.UserConfig.TOKEN, "");
        ActivityUtil.getInstance().exitAccount();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
