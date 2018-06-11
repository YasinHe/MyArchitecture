package com.demo.architecture.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.demo.architecture.R;
import com.demo.architecture.base.BaseMvpActivity;
import com.demo.architecture.base.Constants;
import com.demo.architecture.injection.component.DaggerMainActivityComponent;
import com.demo.architecture.injection.module.MainActivityMoudel;
import com.demo.architecture.model.UpdateModel;

/**
 * Created by HeYingXin on 2018/1/19.
 */
@Route(path = Constants.Route.MAIN_ACTIVITY, group = Constants.Route.GROUP_ONE)
public class MainActivity extends BaseMvpActivity<MainPersenter> implements MainContract.View{

    public static void startMainActivity() {
        ARouter.getInstance().build("/test/mainActivity",Constants.Route.GROUP_ONE).withLong("key1", 666L)
                .withString("key3", "888").navigation();
    }

    public static void startMainActivity2(Activity activity) {
        //直接通过所有拦截器并且制定requestCode
        ARouter.getInstance().build("/test/mainActivity",Constants.Route.GROUP_ONE).withLong("key1", 666L)
                .withString("key3", "888").greenChannel().navigation(activity,Constants.IntentRequest.MAIN_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowlucency();
        setContentView(R.layout.activity_main);
        mPresenter.requestPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {
        setEnableSwipe(false);
    }

    @Override
    protected void initData() {
        mPresenter.updateAPK();
    }

    @Override
    protected void getmPresenter() {
        //方式一，Inject无构造
//        activityComponent.inject(this);//注意这个方法里面需要打开一个inject,并且Presenter无法传参，传参也得传Inject的参数
        //方式二(构造加String)
        DaggerMainActivityComponent.builder().mainActivityMoudel(new MainActivityMoudel("可以")).build().inject(this);
    }

    @Override
    public void updateApkSucc(UpdateModel updateModel) {

    }
}
