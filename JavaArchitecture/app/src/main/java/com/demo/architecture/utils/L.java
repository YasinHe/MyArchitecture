package com.demo.architecture.utils;

import android.util.Log;

import com.demo.architecture.base.Constants;

/**
 * Log统一管理类
 * 
 * @author way
 * 
 */
public class L
{
	private L()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	private static final String TAG = "way";
	private static final boolean isTesting = Constants.Server.isUnitTest;

	// 下面四个是默认tag的函数
	public static void i(String msg)
	{
		if(testingLog(msg))return;
		if (Constants.SystemConfig.isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg)
	{
		if(testingLog(msg))return;
		if (Constants.SystemConfig.isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg)
	{
		if(testingLog(msg))return;
		if (Constants.SystemConfig.isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg)
	{
		if(testingLog(msg))return;
		if (Constants.SystemConfig.isDebug)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg)
	{
		if(testingLog(msg))return;
		if (Constants.SystemConfig.isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg)
	{
		if(testingLog(msg))return;
		if (Constants.SystemConfig.isDebug)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg)
	{
		if (testingLog(msg)) return;
		if(msg.length() > 4000) {
			for(int i=0;i<msg.length();i+=4000){
				if(i+4000<msg.length())
					if (Constants.SystemConfig.isDebug)
						Log.e(tag+i,msg.substring(i, i+4000));
				else
					if (Constants.SystemConfig.isDebug)
						Log.e(tag+i,msg.substring(i, msg.length()));
			}
		} else {
			if (testingLog(msg)) return;
			if (Constants.SystemConfig.isDebug)
				Log.e(tag, msg);
		}
	}

	public static void v(String tag, String msg)
	{
		if(testingLog(msg))return;
		if (Constants.SystemConfig.isDebug)
			Log.i(tag, msg);
	}

	public static boolean testingLog(String msg) {
		if (isTesting) {
			System.out.println(msg);
			return true;
		}
		return false;
	}
}