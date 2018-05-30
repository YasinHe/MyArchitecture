package com.demo.architecture.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.demo.architecture.base.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class UtilTool {
    private static String TAG = "UtilTool";

    /**
     * 获取手机唯一标示
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        // TODO Permission
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机设备信息
     *
     * @return
     */
    public static String getDevice_info() {
        String phoneInfo = "";
        try {
            phoneInfo = "手机型号：" + android.os.Build.BRAND + "," + android.os.Build.MODEL + "系统版本："
                    + android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneInfo;
    }

    public static JSONObject getJSONO_device(Context context) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("imei", getIMEI(context));
            jsonObj.put("device_info", getDevice_info());
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
        return jsonObj;

    }


    public static JSONObject getJSONO_userAuth() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", SPUtils.get(Constants.UserConfig.USER_ID, ""));
            jsonObj.put("token", SPUtils.get(Constants.UserConfig.TOKEN, ""));
//            L.e(TAG + "  getJSONO_userAuth : ", "userId= " + SPUtils.get(Constants.UserConfig.USER_ID, "") + " " +
//                    " token= " + SPUtils.get(Constants.UserConfig.TOKEN, ""));
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
        return jsonObj;

    }

    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    // 获取版本信息
    public static int getCurrentVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 1;
    }

    // 获取版本信息
    public static String getCurrentVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return  info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  "1.0.0";
    }

    /**
     *判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     *  服务器返回url，通过url去获取视频的第一帧
     *  Android 原生给我们提供了一个MediaMetadataRetriever类
     *  提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     *  @param videoUrl
     *  @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public static String getWan(String numString){
        double num=Double.parseDouble(numString);
        if(num<10000){
            return ""+numString;
        }else{
            double n = (double)num/10000;
            return String.format("%.1f", n)+"W";
        }
    }
}
