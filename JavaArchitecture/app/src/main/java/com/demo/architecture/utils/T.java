package com.demo.architecture.utils;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.architecture.R;
import com.demo.architecture.base.App;

/**
 * Toast统一管理类
 * 
 */
public class T
{

	public T()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;

	/**
	 * 短时间显示Toast
	 * 
	 * @param message
	 */
	public static void showShort(CharSequence message)
	{
		if (isShow)
			Toast.makeText(App.getApplication().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param message
	 */
	public static void showShort(int message)
	{
		if (isShow)
			Toast.makeText(App.getApplication().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param message
	 */
	public static void showLong(CharSequence message)
	{
		if (isShow)
			Toast.makeText(App.getApplication().getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param message
	 */
	public static void showLong(int message)
	{
		if (isShow)
			Toast.makeText(App.getApplication().getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param message
	 * @param duration
	 */
	public static void show(CharSequence message, int duration)
	{
		if (isShow)
			Toast.makeText(App.getApplication().getApplicationContext(), message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param message
	 * @param duration
	 */
	public static void show(int message, int duration)
	{
		if (isShow)
			Toast.makeText(App.getApplication().getApplicationContext(), message, duration).show();
	}

	private static Toast toast;


}