package com.zwj.customview.progress;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.View;

import com.zwj.mycustomview.R;


/***
 * 进度框(未完成)
 *
 * @author chenlz
 */
public class ProgressUtil {

    /**
     * 弹出dialog 不可取消
     */
    public static final int SHOW_WAITING_DIALOG = 1;
    /**
     * 弹出dialog 点击空白可取消
     */
    public static final int SHOW_NO_WAITTING_DIALOG = 2;
    /**
     * 弹出dialog 点击空白不可取消，但是可以按返回键取消
     */
    public static final int SHOW_CANCLE_DIALOG = 3;

    /**
     * 进度对话框
     */
    protected static View loadingView;

    /**
     * dialog
     */
    private static Dialog dialog;

    // 是否正在显示
    protected static boolean isLoading = false;

    /**
     * 提供调用的公用接口
     *
     * @param context
     * @param loadingTip
     */
    public static void startProgress(Context context, String loadingTip) {
        startProgress(context, loadingTip, true);
    }

    public static void startProgress(Context context, String loadingTip, boolean cancelable) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        showLoading(context, loadingTip, cancelable);
    }

    private static void showLoading(Context context, String loadingTip, boolean cancelable) {
        if (TextUtils.isEmpty(loadingTip)) {
            loadingTip = "请稍后...";
        }
        dialog = new Dialog(context, R.style.LoadingStyle);
        ProgressView view = new ProgressView(context);
        view.setTipContent(loadingTip);
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                isLoading = false;
            }
        });
        dialog.show();
    }

    /***
     * 隐藏进度对话框
     */
    public static void hideProgress() {
        if (!isLoading) {
            return;
        }
        isLoading = false;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
