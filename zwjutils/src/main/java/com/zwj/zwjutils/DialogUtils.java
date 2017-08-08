package com.zwj.zwjutils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Window;

/**
 * @author zwj
 * 2015-5-16 23:05
 * 
 * 对话框工具类
 */
public class DialogUtils {

	public static final int NON = -1;

	public interface OnDialogClickListener{
		public void onClickConfirmBtnListener();
		public void onClickCancelBtnListener();
	}

	/**
	 * 创建一个ProgressDialog
	 * @param context
	 * @param title
	 * @param message
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context, String title, String message, boolean cancelable){
		
		ProgressDialog pd = new ProgressDialog(context);
		
		if(!TextUtils.isEmpty(title)){
			pd.setTitle(title);
		}
		
		if(!TextUtils.isEmpty(message)){
			pd.setMessage(message);
		}
		
		pd.setCancelable(cancelable);
		return pd;
	}
	
	public static ProgressDialog createHorizontalProgressDialog(Context context, String title, String message, int max, boolean cancelable){
		
		ProgressDialog pd = new ProgressDialog(context);
		
		if(!TextUtils.isEmpty(title)){
			pd.setTitle(title);
		}
		
		if(!TextUtils.isEmpty(message)){
			pd.setMessage(message);
		}
		
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMax(max);
		pd.setCancelable(cancelable);
		return pd;
	}
	
	/**
	 * 创建一个AlertDialog
	 * @param context
	 * @param title		标题，若不想要标题则传入null或者""
	 * @param message	内容，若没有则传入null或者""
	 * @param iconId  	图标id,若没有图标则传入-1
	 * @param cancelable true:按回退键可取消dialog  false:按回退键无法取消dialo
	 * @return
	 */
	public static Builder createAlertDailog(Context context, String title, String message, int iconId, boolean cancelable){
		Builder builder = new Builder(context);
		
		if(!TextUtils.isEmpty(title)){
			builder.setTitle(title);
		}
		
		if(!TextUtils.isEmpty(message)){
			builder.setMessage(message);
		}
		
		if(iconId != NON) {
			builder.setIcon(iconId);
		}
		
		builder.setCancelable(cancelable);
		return builder;
	}
	
	/**
	 * 创建一个AlertDialog
	 * @param context
	 * @param titleId    若没有则传入-1
	 * @param messageId  若没有则传入-1
	 * @param iconId  	  图标id,若没有图标则传入-1
	 * @param cancelable true:按回退键可取消dialog  false:按回退键无法取消dialo
	 * @return
	 */
	public static Builder createAlertDailog(Context context, int titleId, int messageId, int iconId, boolean cancelable){
		Builder builder = new Builder(context);
		
		if(titleId != NON){
			builder.setTitle(titleId);
		}
		
		if(messageId != NON){
			builder.setMessage(messageId);
		}
		
		if(iconId != NON) {
			builder.setIcon(iconId);
		}
		
		builder.setCancelable(cancelable);
		return builder;
	}
	
	/**
	 * 创建自定义的dialog
	 * @param context
	 * @param layoutId
	 * @param cancelable
	 * @return
	 */
	public static Dialog createCustomDialog(Context context, int layoutId, boolean cancelable){
		return createCustomDialog(context, layoutId, cancelable, NON);
	}
	
	public static Dialog createCustomDialog(Context context, int layoutId, boolean cancelable, int style){
		Dialog dialog = null;
		if(style == NON) {
			dialog = new Dialog(context);
		}else {
			dialog = new Dialog(context, style);
		}
		
		dialog.setCancelable(cancelable);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(layoutId);
		
		return dialog;
	}
}

