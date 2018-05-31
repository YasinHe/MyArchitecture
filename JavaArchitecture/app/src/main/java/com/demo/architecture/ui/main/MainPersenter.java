package com.demo.architecture.ui.main;

import android.Manifest;
import android.app.Activity;

import com.demo.architecture.api.HttpObserver;
import com.demo.architecture.api.QClitent;
import com.demo.architecture.api.QHttpService;
import com.demo.architecture.base.Constants;
import com.demo.architecture.model.UpdateModel;
import com.demo.architecture.utils.L;
import com.demo.architecture.utils.UtilTool;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by HeYingXin on 2017/6/23.
 */
public class MainPersenter extends MainContract.MyPresenter{

    @Inject
    public MainPersenter(){

    }

    @Override
    public void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions((Activity) getMvpView().getContext());
        rxPermission
                .requestEach(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_APN_SETTINGS,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.GET_TASKS,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.INTERNET)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            //注册个推
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            L.d("rxPermission", permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            L.d("rxPermission", permission.name + " is denied.");
                        }
                    }
                });

    }

    @Override
    public void updateAPK() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("version", UtilTool.getCurrentVersionName(getMvpView().getContext()));
            jsonObject.put("type", "android_user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.util.Map<String, String> map = new HashMap<>();
        map.put("json", jsonObject.toString());
        addTask(QClitent.getInstance().create(QHttpService.class).
                postUpdate(Constants.Http.UPDATE_APK, map), new HttpObserver<UpdateModel>() {

            @Override
            public void onFailure(Throwable e) {
                L.e("updateAPK","更新接口请求失败");
            }

            @Override
            public void onSuccess(UpdateModel value) {
                getMvpView().updateApkSucc(value);
            }

        });
    }
}