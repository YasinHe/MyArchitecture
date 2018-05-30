package com.demo.architecture.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;

import com.demo.architecture.utils.L;
import com.demo.architecture.utils.T;

/**(可合理改动继承扩展)
 * Created by HeYingXin on 2017/2/20.
 */
public abstract class BaseMvpFragment<P extends Presenter> extends BaseFragment implements MvpView{

    public P mPresenter;

    protected void onViewCreated(View view){
        mPresenter = getmPresenter();
        if (mPresenter != null)mPresenter.attachView(this);
        super.onViewCreated(view);
    }

    /**
     * 抽象出骨架
     */
    protected abstract P getmPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.detachView();
    }


    @Override
    @UiThread
    public void showLoading(String msg) {
        // TODO (如果有需要可以在这写个通用加载中对话框)
    }

    @Override
    @UiThread
    public void hideLoading() {
        // TODO
    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {
        L.e("TAG",msg);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    @UiThread
    public void showToast(@NonNull final String message) {
        if(Constants.Server.isUnitTest) {
            ((Activity)getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showShort(message);
                }
            });
        }else{
            T.showShort(message);
        }
    }
}
