package com.demo.architecture.ui.main;


import com.demo.architecture.base.BasePresenter;
import com.demo.architecture.base.MvpView;
import com.demo.architecture.model.UpdateModel;

/**
 * Created by HeYingXin on 2017/6/23.
 */
public interface MainContract {
    interface View extends MvpView {
        public void updateApkSucc(UpdateModel updateModel);
    }

    abstract class MyPresenter extends BasePresenter<View> {
        public abstract void requestPermissions();
        public abstract void updateAPK();
    }
}