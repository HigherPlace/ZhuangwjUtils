package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;
import com.zwj.zhuangwjutils.R;
import com.zwj.zwjutils.ToastUtil;

public class CustomToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_toast);
    }

    public void showSystemToast(View view) {
        ToastUtil.useCustomeLayout = false;
        ToastUtil.toast(this, "系统默认的toast");
    }

    public void showCustomeToast(View view) {
        ToastUtil.useCustomeLayout = true;
        ToastUtil.setDefaultGravity(Gravity.CENTER, 0, AutoUtils.getPercentHeightSize(550));
        ToastUtil.toast(this, "自定义toast");
    }
}
