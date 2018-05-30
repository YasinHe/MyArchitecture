package com.demo.architecture.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.demo.architecture.R;


public abstract class BasePopWindow extends PopupWindow {
	private Context context;

	// PopupWindow的宽度
	public int mWidth;
	// PopupWindow的高度
	public int mHeight;
	// 获取PopupWindow的view
	public View mConvertView;
	public static final int HOR = 0;
	public static final int VER = 1;
	//自定义动画显示
	public static final int CUSTOM_VER = 2;

	public static final int CUSTOM_HOR = 3;

	public static final int CUSTOM = 4;

	/**
	 * 设置布局和一些初始化操作,若proPortion为0，则width和height不能为0，反之亦然。
	 * @param context
	 * @param source
	 * @param proPortion
	 */
	public void setLayout(final Context context, int source, float proPortion, int width, int height, int mode) {
		this.context = context;
		mConvertView = LayoutInflater.from(context).inflate(source, null);
		setContentView(mConvertView);
		if (mode==CUSTOM){
			culculateWidthAndHeight(width,height);
			setWidth(mWidth);
			setHeight(mHeight);
		}else{
			if (proPortion==0) {
				setWidth(width);
				setHeight(height);
			}else{
				culculateWidthAndHeight(proPortion,mode);
				setWidth(mWidth);
				setHeight(mHeight);
			}
		}

		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		initId();
		initEvent();
		if (mode==HOR){
			setAnimationStyle(R.style.mypopwindow_anim_style_hor);
		}else if (mode==VER){
			setAnimationStyle(R.style.mypopwindow_anim_style_ver);
		}

	}

	public void setLayout(final Context context, View view, float proPortion, int width, int height, int mode) {
		this.context = context;
		mConvertView = view;
		setContentView(mConvertView);
		if (mode==CUSTOM){
			culculateWidthAndHeight(width,height);
			setWidth(mWidth);
			setHeight(mHeight);
		}else{
			if (proPortion==0) {
				setWidth(width);
				setHeight(height);
			}else{
				culculateWidthAndHeight(proPortion,mode);
				setWidth(mWidth);
				setHeight(mHeight);
			}
		}

		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		initId();
		initEvent();
		if (mode==HOR){
			setAnimationStyle(R.style.mypopwindow_anim_style_hor);
		}else if (mode==VER){
			setAnimationStyle(R.style.mypopwindow_anim_style_ver);
		}
	}
	
	public abstract void initId();
	public abstract void initEvent();
	public void setAnimation(int style){
		setAnimationStyle(style);
	}

	private void culculateWidthAndHeight(float proPortion,int mode) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
        if (mode==HOR){
			mWidth = (int) (displayMetrics.widthPixels * proPortion);
			mHeight = displayMetrics.heightPixels;
		} else if (mode==VER){
			mWidth = displayMetrics.widthPixels;
			mHeight = (int) (displayMetrics.heightPixels * proPortion);
		} else if (mode==CUSTOM_VER){
			mWidth = displayMetrics.widthPixels;
			mHeight = (int) (displayMetrics.heightPixels * proPortion);
		} else if (mode==CUSTOM_HOR){
			mWidth = (int) (displayMetrics.widthPixels * proPortion);
			mHeight = displayMetrics.heightPixels;
		}

	}
	private void culculateWidthAndHeight(int  mWidth,int mHeight){
		this.mWidth = mWidth;
		this.mHeight = mHeight;
	}

	/**
	 * 灯亮
	 * @param activity
	 */
	public void lightOn(Activity activity,int type) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = 1.0f;
		if(type==1){
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}
		activity.getWindow().setAttributes(lp);

	}

	/**
	 * 灯灭
	 * @param activity
	 */
	public void lightOff(Activity activity,int type) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = 0.3f;
		if(type==1){
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}
		activity.getWindow().setAttributes(lp);
	}

}
