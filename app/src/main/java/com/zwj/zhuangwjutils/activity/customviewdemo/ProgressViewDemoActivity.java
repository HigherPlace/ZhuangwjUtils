package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zwj.customview.progress.ProgressUtil;
import com.zwj.zhuangwjutils.R;

public class ProgressViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view_demo);
    }

    public void startProgressView(View view) {
        ProgressUtil.startProgress(this, "正在加载...");
    }

    public void hideProgressView(View view) {
        ProgressUtil.hideProgress();
    }
}
