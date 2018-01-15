package com.zwj.customview.progress;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by zwj on 2017/1/4.
 * 加载进度条的参数实体类
 */

public class ProgressBean {
    private String loadingTip = "请稍后...";          // 提示信息
    private boolean cancelable = true;         // true,点击回退键可取消；false,不可取消
    private ProgressUtil.OnCancelListener cancelListener;    // 点击回退键取消时调用该监听类
    private DialogInterface.OnDismissListener dismissListener;  // 加载进度条消失时调用该监听类
    private int color;
    // true - 背景透明
    private boolean backgroudTransparent;

    public String getLoadingTip() {
        return loadingTip;
    }

    public ProgressBean setLoadingTip(String loadingTip) {
        this.loadingTip = loadingTip;
        return this;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public ProgressBean setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public ProgressUtil.OnCancelListener getCancelListener() {
        return cancelListener;
    }

    public ProgressBean setCancelListener(ProgressUtil.OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    public DialogInterface.OnDismissListener getDismissListener() {
        return dismissListener;
    }

    public ProgressBean setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public int getColor() {
        return color;
    }

    public ProgressBean setColor(int color) {
        this.color = color;
        return this;
    }

    public void startProgress(Context context) {
        ProgressUtil.startProgress(context, this);
    }

    public boolean isBackgroudTransparent() {
        return backgroudTransparent;
    }

    public ProgressBean setBackgroudTransparent(boolean backgroudTransparent) {
        this.backgroudTransparent = backgroudTransparent;
        return this;
    }
}
