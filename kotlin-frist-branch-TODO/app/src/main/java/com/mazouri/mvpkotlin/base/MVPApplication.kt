package com.mazouri.mvpkotlin.base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.mazouri.mvpkotlin.BuildConfig
import com.mazouri.mvpkotlin.injection.component.ApplicationComponent
import com.mazouri.mvpkotlin.injection.component.DaggerApplicationComponent
import com.mazouri.mvpkotlin.injection.module.ApplicationModule
import com.mazouri.mvpkotlin.utils.AdaptationUi
import com.mazouri.mvpkotlin.utils.CrashExecptionHandler
import timber.log.Timber

/**
 * Created by HeYingXin on 2018/4/11.
 */
class MVPApplication : Application() {
    internal var mApplicationComponent: ApplicationComponent? = null
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        //屏幕适配
        AdaptationUi.setDensity(this)
        //路由
        if (Constants.SystemConfig.isDebug) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        //Carsh捕获上传
        if (!Constants.SystemConfig.isDebug) {
            CrashExecptionHandler.getInstance().init(this)
            CrashExecptionHandler.getInstance().sendPreviousReportsToServer()
        }
        ComponentHolder.setAppComponent(component)
    }

    companion object {
        operator fun get(context: Context): MVPApplication {
            return context.applicationContext as MVPApplication
        }
    }

    var component: ApplicationComponent
        get() {
            if (mApplicationComponent == null) {
                mApplicationComponent = DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .build()
            }
            return mApplicationComponent as ApplicationComponent
        }
        set(applicationComponent) {
            mApplicationComponent = applicationComponent
        }

    /**
     * 退出应用，清除数据
     */
    fun appExit(){
        val activityMgr:ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityMgr.killBackgroundProcesses(getPackageName())
        System.exit(0)
    }

    fun isLogin(): Boolean {
        return true
    }

}
