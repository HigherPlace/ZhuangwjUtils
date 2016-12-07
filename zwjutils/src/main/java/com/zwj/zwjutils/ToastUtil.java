/**
 * 
 */
package com.zwj.zwjutils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;



public class ToastUtil {

	private static Toast mToast;

	/**
	 * 取消mToast引用对象
	 */
	public static void destroy() {
		mToast = null;
	}

	/**
	 * 默认toast的显示
	 * 
	 * @param content
	 *            提示内容
	 */
	public static void toast(Context context, String content) {
		if(!TextUtils.isEmpty(content)) {
			if (mToast != null) {
				mToast.cancel();
			}
			mToast = Toast.makeText(context, content,
					Toast.LENGTH_SHORT);
			mToast.show();
		}
	}

	/***
	 * 
	 * @param content
	 *            内容
	 * @param duration
	 *            时间
	 */
	public static void toast(Context context, String content, int duration) {
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = Toast.makeText(context, content,
				duration);
		mToast.show();
	}

	/**
	 * 默认toast的显示
	 * 
	 * @param resId
	 *            提示内容字符串
	 */
	public static void toast(Context context, int resId) {
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = Toast.makeText(context, resId,
				Toast.LENGTH_SHORT);
		mToast.show();
	}

}