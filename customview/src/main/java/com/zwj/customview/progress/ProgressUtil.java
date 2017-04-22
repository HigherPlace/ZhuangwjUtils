package com.zwj.customview.progress;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;

import com.zwj.mycustomview.R;


/***
 * 进度框(未完成)
 *
 * @author chenlz
 */
public class ProgressUtil {
    private static final String TAG = "ProgressUtil";

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
        startProgress(context, loadingTip, true, null, null);
    }

    public static void startProgress(Context context, String loadingTip, boolean cancelable,
                                     OnCancelListener cancelListener, DialogInterface.OnDismissListener dismissListener) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        showLoading(context, new ProgressBean().setLoadingTip(loadingTip).setCancelable(cancelable)
                        .setCancelListener(cancelListener).setDismissListener(dismissListener));
    }

    public static void startProgress(Context context, ProgressBean progressBean) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        showLoading(context, progressBean);
    }

    private static void showLoading(Context context, ProgressBean progressBean) {
        dialog = new Dialog(context, R.style.LoadingStyle);
        ProgressView view = new ProgressView(context, progressBean.getLoadingTip(), progressBean.getColor());
//        view.setTipContent(progressBean.getLoadingTip()).setColor(progressBean.getColor());
        dialog.setContentView(view);
        dialog.setCancelable(progressBean.isCancelable());
        dialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                isLoading = false;
            }
        });

        if (progressBean.getCancelListener() != null) {
            dialog.setOnCancelListener(progressBean.getCancelListener());
        }

        if (progressBean.getDismissListener() != null) {
            dialog.setOnDismissListener(progressBean.getDismissListener());
        }
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
