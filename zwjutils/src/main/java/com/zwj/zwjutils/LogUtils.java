package com.zwj.zwjutils;

import android.text.TextUtils;
import android.util.Log;

/**
 * log打印辅助类，方便统一管理log，对log的打印进行开关
 */
public class LogUtils {
	public static int logLevel = 6;
	public static final int VERBOSE = 1;		
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;

	
	public static void v(String tag, String msg) {
		if(!TextUtils.isEmpty(msg) && logLevel > VERBOSE){
			Log.v(tag, msg);
		}
	}
	
	public static void d(String tag, String msg) {
		if(!TextUtils.isEmpty(msg) && logLevel > DEBUG){
			Log.d(tag, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if(!TextUtils.isEmpty(msg) && logLevel > INFO){
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag, String msg) {
		if(!TextUtils.isEmpty(msg) && logLevel > WARN){
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag, String msg) {
		if(!TextUtils.isEmpty(msg) && logLevel > ERROR){
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, String msg, Throwable e) {
		if(!TextUtils.isEmpty(msg) && logLevel > ERROR){
			Log.e(tag, msg, e);
		}
	}
	
	public static void sysout(String msg) {
		if(!TextUtils.isEmpty(msg) && logLevel > INFO){
			System.out.println(msg);
		}
	}
}
