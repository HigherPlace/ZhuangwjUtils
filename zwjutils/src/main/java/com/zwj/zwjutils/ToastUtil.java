/**
 *
 */
package com.zwj.zwjutils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ToastUtil {

    /**
     * 自定义toast布局的id
     */
    public static int customLayoutId = R.layout.toast_default_custom_view;
    /**
     * true - 使用自定义布局
     */
    public static boolean useCustomeLayout = false;


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
     * @param content 提示内容
     */
    public static void toast(Context context, String content) {
        if (!TextUtils.isEmpty(content)) {
            toast(context, content, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 默认toast的显示
     *
     * @param resId 提示内容字符串
     */
    public static void toast(Context context, int resId) {
        String content = context.getString(resId);
        toast(context, content, Toast.LENGTH_SHORT);
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

        if(useCustomeLayout) {
            View v = LayoutInflater.from(context).inflate(customLayoutId, null);
            TextView textView = (TextView) v.findViewById(R.id.tv);
            textView.setText(content);
            mToast = new Toast(context);
            mToast.setDuration(duration);
            mToast.setView(v);

            mToast.setGravity(gravity, xOffset, yOffset);
        }else {
            mToast = Toast.makeText(context.getApplicationContext(), content,
                    duration);
        }

        mToast.show();
    }


    private static int gravity;
    private static int xOffset;
    private static int yOffset;

    /**
     * 设置toast的默认位置
     *
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void setDefaultGravity(int gravity, int xOffset, int yOffset) {
        ToastUtil.gravity = gravity;
        ToastUtil.xOffset = xOffset;
        ToastUtil.yOffset = yOffset;
    }
}