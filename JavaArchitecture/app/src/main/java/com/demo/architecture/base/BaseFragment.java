package com.demo.architecture.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.demo.architecture.api.HttpObservable;
import com.demo.architecture.api.HttpObserver;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HeYingXin on 2017/7/19.
 */
public abstract class BaseFragment extends Fragment {
    //标志位，标志已经初始化完成
    protected boolean isPrepared;
    //Fragment当前状态是否可见
    protected boolean isVisible;
    protected CompositeDisposable compositeDisposable;
    private View rootView;
    private Unbinder mUnBinder;

    protected void onViewCreated(View view){
        compositeDisposable = new CompositeDisposable();
        isPrepared = true;
        rootView = view;
        mUnBinder = ButterKnife.bind(this,rootView);
        initView();
        initData();
        isDoLazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        mUnBinder.unbind();
    }

    /**
     * 抽象出骨架
     */
    protected abstract void initView();
    protected abstract void initData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        isDoLazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 是否可以做延迟加载
     */
    protected void isDoLazyLoad(){
        if(!isVisible || !isPrepared){
            return;
        }else{
            lazyLoad();
        }
    }

    /**
     * 延迟加载
     */
    protected abstract void lazyLoad();

    public void addTask(Observable observable, HttpObserver consumer) {
        if(observable instanceof HttpObservable){
            if(Constants.Server.isUnitTest){
                observable.subscribeOn(Schedulers.trampoline()).observeOn(Schedulers.trampoline());
            }else {
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
            ((HttpObservable)observable).subscribe(consumer);
        }else {
            if(Constants.Server.isUnitTest){
                observable.subscribeOn(Schedulers.trampoline()).observeOn(Schedulers.trampoline())
                        .subscribe(consumer);
            }else {
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer);
            }
        }
        compositeDisposable.add(consumer.getDisposable());
    }
}