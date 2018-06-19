package com.demo.architecture.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.LongSparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.demo.architecture.R;
import com.demo.architecture.api.HttpObservable;
import com.demo.architecture.api.HttpObserver;
import com.demo.architecture.customView.SwipeBackLayout;
import com.demo.architecture.injection.component.ActivityComponent;
import com.demo.architecture.injection.component.ConfigPersistentComponent;
import com.demo.architecture.injection.component.DaggerConfigPersistentComponent;
import com.demo.architecture.injection.module.ActivityModule;
import com.demo.architecture.ui.main.MainActivity;
import com.demo.architecture.utils.ActivityUtil;
import com.demo.architecture.utils.UtilTool;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HeYingXin on 2017/7/19.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected CompositeDisposable compositeDisposable;
    //滑动关闭activity
    private SwipeBackLayout mSwipeBackLayout;
    private Unbinder mUnBinder;

    private long mActivityId = 0;
    private String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private AtomicLong NEXT_ID = new AtomicLong(0);
    private LongSparseArray<ConfigPersistentComponent> sComponentsArray = new LongSparseArray<ConfigPersistentComponent>();
    protected ActivityComponent activityComponent;
    //屏幕适配
    private static float sNoncompatDenisty;
    private static float sNoncompatScaleDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        compositeDisposable = new CompositeDisposable();
        if (ComponentHolder.getAppComponent().myApplication().systemLive == 0 && !(this instanceof MainActivity)) {
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        ActivityUtil.getInstance().addActivity(this);
        if(savedInstanceState==null||savedInstanceState.getLong(KEY_ACTIVITY_ID)==0){
            mActivityId = NEXT_ID.getAndIncrement();
        }else {
            mActivityId = savedInstanceState.getLong(KEY_ACTIVITY_ID);
        }
        ConfigPersistentComponent configPersistentComponent;
        if (sComponentsArray.get(mActivityId) == null) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(ComponentHolder.getAppComponent())
                    .build();
            sComponentsArray.put(mActivityId, configPersistentComponent);
        } else {
            configPersistentComponent = sComponentsArray.get(mActivityId);
        }
        activityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
        activityComponent.inject(this);
        setCustomDensity(this,ComponentHolder.getAppComponent().myApplication());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    public void setContentView(@LayoutRes int resourceId) {
        View view = getLayoutInflater().inflate(resourceId, null, false);
        super.setContentView(getContainer());
        mSwipeBackLayout.addView(view);
        mUnBinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        mSwipeBackLayout = new SwipeBackLayout(this);
        mSwipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        container.addView(mSwipeBackLayout);
        return container;
    }

    public void setEnableSwipe(boolean enableSwipe) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setEnablePullToBack(enableSwipe);
            if (enableSwipe == false) {
                mSwipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.NO);
                mSwipeBackLayout.setSwipeSize(0);
            }
        }
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    /*纯透明直接去掉标题*/
    public void setWindowlucency(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(false);
            tintManager.setNavigationBarTintEnabled(false);
        }
    }

    public void setWindowLuceency(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintResource(R.drawable.bg_top_title);
            tintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.bg_top_title));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO Auto-generated method stub
        if (UtilTool.isApplicationBroughtToBackground(this)) {
            //全局变量 app 进入后台
            Constants.SystemConfig.ISACTIVE = false;//  记录当前已经进入后台
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (!Constants.SystemConfig.ISACTIVE) {//app 从后台唤醒，进入前台
            Constants.SystemConfig.ISACTIVE = true;
        }
    }

    @Override
    protected void onDestroy() {
        mUnBinder.unbind();
        compositeDisposable.clear();
        if (!isChangingConfigurations()) {
            sComponentsArray.remove(mActivityId);
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        ActivityUtil.getInstance().RemoveActivity(this);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if(intent!=null&&intent.getComponent()!=null&&intent.getComponent().getClassName()!=null) {
            String className = intent.getComponent().getClassName();
            if (className.contains("MAIN_ACTIVITY") || className.contains("SplashActivity") || className.contains("GuideActivity")
                    || className.contains("PersonalInfoActivity")  || className.contains("DynamicBrowseActivity")
                    || className.contains("DynamicVideoBrowseActivity")) {
                overridePendingTransition(R.anim.zoomin,
                        R.anim.zoomout);
            } else {
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        }
    }

    /**
     * 抽象出骨架
     */
    protected abstract void initView();

    protected abstract void initData();

    public void addTask(Observable observable, HttpObserver consumer) {
        if (observable instanceof HttpObservable) {
            if (Constants.Server.isUnitTest) {
                observable.subscribeOn(Schedulers.trampoline()).observeOn(Schedulers.trampoline());
            } else {
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
            ((HttpObservable) observable).subscribe(consumer);
        } else {
            if (Constants.Server.isUnitTest) {
                observable.subscribeOn(Schedulers.trampoline()).observeOn(Schedulers.trampoline())
                        .subscribe(consumer);
            } else {
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer);
            }
        }
        compositeDisposable.add(consumer.getDisposable());
    }

    //适配方案
    private static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application){
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if(sNoncompatDenisty==0){
            sNoncompatDenisty = displayMetrics.density;
            sNoncompatScaleDensity  = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if(newConfig!=null&&newConfig.fontScale>0){
                        sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }
                @Override
                public void onLowMemory() {
                }
            });
        }

        final float targeDensity = displayMetrics.widthPixels/360;//360dp是设计图宽
        final float targetScaledDensity = targeDensity * (sNoncompatScaleDensity/sNoncompatDenisty);
        final int targetDensityDpi = (int)(160 * targeDensity);

        displayMetrics.density = targeDensity;
        displayMetrics.scaledDensity = targetScaledDensity;
        displayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targeDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}