package com.zwj.zhuangwjutils.activity.appmanager;

import android.os.Bundle;
import android.view.View;

import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;
import com.zwj.zwjutils.AppManager;

public class TestCActivity extends BaseAutoLayoutCommonActivity {


    @Override
    protected int getContentViewId() {
        return R.layout.activity_test_c;
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

    public void clearTop(View view) {
        AppManager.getAppManager().finishPreAllActivity();
    }

}
