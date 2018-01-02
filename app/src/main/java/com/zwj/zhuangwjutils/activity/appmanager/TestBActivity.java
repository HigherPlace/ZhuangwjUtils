package com.zwj.zhuangwjutils.activity.appmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;

public class TestBActivity extends BaseAutoLayoutCommonActivity {


    @Override
    protected int getContentViewId() {
        return R.layout.activity_test_b;
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {

    }

    public void goTestC(View view) {
        startActivity(new Intent(mContext, TestCActivity.class));
    }

}
