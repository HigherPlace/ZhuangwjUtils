package com.zwj.zwjutils.net.callback;


import com.zwj.zwjutils.LogUtils;
import com.zwj.zwjutils.net.NetManager;
import com.zwj.zwjutils.net.bean.ResponseBean;

/**
 * Created by Administrator on 2016/5/26.
 */
public abstract class UploadImageCallBack implements NetManager.RequestCallBack {
    private static final String TAG = "UploadImageCallBack";

    @Override
    public void onCancelled(ResponseBean responseBean) {
        LogUtils.i(TAG, "---> onCancelled");
    }

    @Override
    public void onError(ResponseBean responseBean) {
        LogUtils.i(TAG, "---> onError");
//        if(responseBean != null && responseBean.getThrowable() != null) {
//            responseBean.getThrowable().printStackTrace();
//            // 连接超时，重连
//            if (responseBean.getThrowable() instanceof SocketTimeoutException) {
//                ToastUtil.toast(MyApplication.getGlobalContext(), "网络连接超时");
//            } else if (responseBean.isShowToast()) {
//                ToastUtil.toast(MyApplication.getGlobalContext(), responseBean.getMessage());
//            }
//        }
    }

    @Override
    public void onFinished(ResponseBean responseBean) {
        LogUtils.i(TAG, "---> onFinished");
    }
}
