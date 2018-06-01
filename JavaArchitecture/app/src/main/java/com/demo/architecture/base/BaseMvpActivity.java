package com.demo.architecture.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.MotionEvent;
import android.view.View;

import com.demo.architecture.utils.FaceUtils;
import com.demo.architecture.utils.L;
import com.demo.architecture.utils.T;
import com.demo.architecture.utils.UiHelper;

import javax.inject.Inject;

/**
 * (可合理改动继承扩展)
 * Created by HeYingXin on 2017/2/16.
 */
public abstract class BaseMvpActivity<P extends Presenter> extends BaseActivity implements MvpView {

    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int resourceId){
        getmPresenter();
        if (mPresenter != null)mPresenter.attachView(this);
        super.setContentView(resourceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
        if (mPresenter != null) mPresenter.detachView();
    }

    /**
     * 抽象出骨架
     */
    protected abstract void getmPresenter();

    @Override
    @UiThread
    public void showLoading(String msg) {
        // TODO (如果有需要可以在这写个通用加载中对话框)
        UiHelper.showProgressDialog(this,msg);
    }

    @Override
    @UiThread
    public void hideLoading() {
        // TODO
        UiHelper.dismissProgressDialog();
    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {
        L.e("TAG",msg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    @UiThread
    public void showToast(@NonNull final String message) {
        if(Constants.Server.isUnitTest) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showShort(message);
                }
            });
        }else{
            T.showShort(message);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            try {
                FaceUtils.hideSoftInput(v);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return super.dispatchTouchEvent(ev);
    }
}

