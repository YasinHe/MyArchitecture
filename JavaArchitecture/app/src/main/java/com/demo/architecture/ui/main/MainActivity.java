package com.demo.architecture.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.demo.architecture.R;
import com.demo.architecture.base.BaseMvpActivity;
import com.demo.architecture.model.UpdateModel;

import javax.inject.Inject;

/**
 * Created by HeYingXin on 2018/1/19.
 */
public class MainActivity extends BaseMvpActivity<MainPersenter> implements MainContract.View{

    @Inject
    MainPersenter mainPresenter;

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent.inject(this);
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
    protected MainPersenter getmPresenter() {
        return mPresenter = mainPresenter;
    }

    @Override
    public void updateApkSucc(UpdateModel updateModel) {

    }
}
