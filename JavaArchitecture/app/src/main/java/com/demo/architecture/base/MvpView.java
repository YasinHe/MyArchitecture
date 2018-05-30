package com.demo.architecture.base;

import android.content.Context;
import android.view.View;

/**
 * Created by HeYingXin on 2017/2/16.
 * 顶层接口，设置了六个通用回调方便扩展(可合理改动扩展)
 */
public interface MvpView {

    void showLoading(String msg);

    void hideLoading();

    void showError(String msg, View.OnClickListener onClickListener);

    Context getContext();

    void showToast(String message);

}
