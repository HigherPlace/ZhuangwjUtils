package com.zwj.zwjutils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 操作输入法的工具类。可以方便的关闭和显示输入法.
 * 
 * @date 2015/5/27
 */
public class KeyBoardUtil {
	private static KeyBoardUtil instance;
	private InputMethodManager mInputMethodManager;

	/**
	 * 供Activity调用
	 * @param activity
	 */
	public static void hideFrom(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		// 找到当前获得焦点的 view，从而可以获得正确的窗口 token
		View view = activity.getCurrentFocus();
		// 如果没有获得焦点的 view，创建一个新的，从而得到一个窗口的 token
		if (view == null) {
			view = new View(activity);
		}
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 供fragment调用
	 * @param context
	 * @param view
	 */
	public static void hideFrom(Context context, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	private KeyBoardUtil(Activity activity) {
		mInputMethodManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	public static KeyBoardUtil getInstance(Activity activity) {
		if (instance == null) {
			instance = new KeyBoardUtil(activity);
		}
		return instance;
	}

	/**
	 * 强制显示输入法
	 */
	public void show(Activity activity) {
		show(activity.getWindow().getCurrentFocus());
	}

	public void show(View view) {
		mInputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 强制关闭输入法
	 */
	public void hide(Activity activity) {
		hide(activity.getWindow().getCurrentFocus());
	}

	public void hide(View view) {
		mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 如果输入法已经显示，那么就隐藏它；如果输入法现在没显示，那么就显示它
	 */
	public void showOrHide() {
		mInputMethodManager.toggleSoftInput(0,
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 判断输入法是否已打开 true:输入法已打开
	 */
	public boolean isOpen() {
		return mInputMethodManager.isActive();
	}

}
