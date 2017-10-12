package com.zwj.zwjutils.net.bean;

import org.xutils.common.Callback;

/**
 * Created by Administrator on 2016/5/27.
 */
public class ResponseBean {
    private int status;
    private String message;
    private String result;
    private Throwable throwable;
    private boolean isShowToast = true;
    private String url;

    // ture - 已经处理完错误的相关业务
    private boolean handleErrorFinish;

    public boolean isShowToast() {
        return isShowToast;
    }

    public void setShowToast(boolean showToast) {
        isShowToast = showToast;
    }

    private Callback.CancelledException cancelledException;

    public ResponseBean() {}

    public ResponseBean(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public ResponseBean setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message != null ? message : "";
    }

    public ResponseBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public Callback.CancelledException getCancelledException() {
        return cancelledException;
    }

    public ResponseBean setCancelledException(Callback.CancelledException cancelledException) {
        this.cancelledException = cancelledException;
        return this;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public ResponseBean setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public String getResult() {
        return result != null ? result : "";
    }

    public ResponseBean setResult(String result) {
        this.result = result;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ResponseBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isHandleErrorFinish() {
        return handleErrorFinish;
    }

    public void setHandleErrorFinish(boolean handleErrorFinish) {
        this.handleErrorFinish = handleErrorFinish;
    }
}
