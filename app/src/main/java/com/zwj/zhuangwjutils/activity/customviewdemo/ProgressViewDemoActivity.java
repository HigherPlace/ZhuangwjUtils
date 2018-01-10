package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zwj.customview.progress.ProgressBean;
import com.zwj.customview.progress.ProgressUtil;
import com.zwj.zhuangwjutils.R;
import com.zwj.zwjutils.ToastUtil;

public class ProgressViewDemoActivity extends AppCompatActivity {

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProgressUtil.hideProgress();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view_demo);
    }

    public void startProgressView(View view) {
//        ProgressUtil.startProgress(this, "正在加载...");

        new ProgressBean().setLoadingTip("正在加载...").setColor(Color.BLUE)
                .setCancelable(true)
                .setBackgroudTransparent(true)
                .setCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        ToastUtil.toast(ProgressViewDemoActivity.this, "onCancel");
                    }
                }).startProgress(this);
        // 3秒后关闭loading
//        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    public void hideProgressView(View view) {
        ProgressUtil.hideProgress();
    }
}
