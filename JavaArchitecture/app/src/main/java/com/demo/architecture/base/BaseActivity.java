package com.demo.architecture.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.demo.architecture.customView.SwipeBackLayout;
import com.demo.architecture.ui.main.MainActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.demo.architecture.R;
import com.demo.architecture.api.HttpObservable;
import com.demo.architecture.api.HttpObserver;
import com.demo.architecture.utils.ActivityUtil;
import com.demo.architecture.utils.RxBus;
import com.demo.architecture.utils.T;
import com.demo.architecture.utils.UtilTool;

import java.lang.reflect.Field;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        compositeDisposable = new CompositeDisposable();
        if (App.getApplication().systemLive == 0 && !(this instanceof MainActivity)) {
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        ActivityUtil.getInstance().addActivity(this);
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

    public void setWindowLuceency(int color, final View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                try {
                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
                } catch (Exception e) {e.printStackTrace();}
            }
            if(color!=-1)
                window.setStatusBarColor(color);
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
            readyView(view,color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View statusBarView = mContentView.getChildAt(0);
            if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == getStatusBarHeight(this)) {
                mContentView.removeView(statusBarView);
            }
            if (mContentView.getChildAt(0) != null) {
                ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
            }
            readyView(view,color);
        }
    }

    public void readyView(final View view,final int color) {
        if(view==null){
            return;
        }
        final int statusBarHeight = getStatusBarHeight(this.getBaseContext());
        view.post(new Runnable() {
            @Override
            public void run() {
                if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.height = layoutParams.height + statusBarHeight;
                    if (color == -1) {

                    } else if (color != getResources().getColor(R.color.tarn)) {
                        view.setBackgroundColor(color);
                    } else {
                        view.setBackgroundResource(R.drawable.bg_top_title);
                    }
                    view.setLayoutParams(layoutParams);
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight, view.getPaddingRight(), view.getPaddingBottom());
                } else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.height = layoutParams.height + statusBarHeight;
                    if (color == -1) {

                    } else if (color != getResources().getColor(R.color.tarn)) {
                        view.setBackgroundColor(color);
                    } else {
                        view.setBackgroundResource(R.drawable.bg_top_title);
                    }
                    view.setLayoutParams(layoutParams);
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight, view.getPaddingRight(), view.getPaddingBottom());
                } else if (view.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                    CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams
                            (view.getWidth(), view.getHeight() + statusBarHeight);
                    if (color == -1) {

                    } else if (color != getResources().getColor(R.color.tarn)) {
                        view.setBackgroundColor(color);
                    } else {
                        view.setBackgroundResource(R.drawable.bg_shop_top);
                    }
                    view.setLayoutParams(layoutParams);
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight, view.getPaddingRight(), view.getPaddingBottom());
                }else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.height = layoutParams.height + statusBarHeight;
                    if (color == -1) {

                    } else if (color != getResources().getColor(R.color.tarn)) {
                        view.setBackgroundColor(color);
                    } else {
                        view.setBackgroundResource(R.drawable.bg_top_title);
                    }
                    view.setLayoutParams(layoutParams);
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight, view.getPaddingRight(), view.getPaddingBottom());
                }
            }
        });
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setHighlight() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
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
            if (className.contains("MainActivity") || className.contains("SplashActivity") || className.contains("GuideActivity")
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
}