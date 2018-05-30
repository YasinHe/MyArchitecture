package com.demo.architecture.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

/**
 * Created by HeYingXin on 2017/7/20.
 */
public class UiHelper {
    private static ProgressDialog progressDialog;

    public static void hideTitle(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public static void showProgressDialog(Context context, String title,
                                          String msg) {
        showProgressDialog(context, title, msg, true, null);
    }

    public static void showProgressDialog(Context context, String msg) {
        showProgressDialog(context, null, msg, true, null);
    }

    public static void showProgressDialog(Context context, String msg,
                                          boolean cancelable) {
        showProgressDialog(context, null, msg, cancelable, null);
    }

    public static void showProgressDialog(final Context context, final String title, final String msg,
                                          final boolean cancelable, final DialogInterface.OnCancelListener onCancle) {
        if (progressDialog != null && progressDialog.isShowing() == true) {
            return;
        }
        if (context != null) {
            try {//context所代表的activity可能已经不存在，由于延迟执行任务但是activity本身已经死亡
                progressDialog = ProgressDialog.show(context, title, msg, true,
                        cancelable);
                progressDialog.setCanceledOnTouchOutside(false);
                if (onCancle != null)
                    progressDialog.setOnCancelListener(onCancle);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void showProgressDialog(Context context, String msg,
                                          DialogInterface.OnCancelListener onCancle) {
        showProgressDialog(context, null, msg, true, onCancle);
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}