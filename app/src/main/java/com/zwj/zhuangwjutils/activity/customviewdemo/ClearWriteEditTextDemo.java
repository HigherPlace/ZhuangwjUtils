package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.os.Bundle;

import com.zwj.customview.ClearAndLimitEditText;
import com.zwj.customview.CusEditText;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;
import com.zwj.zwjutils.LogUtils;

public class ClearWriteEditTextDemo extends BaseAutoLayoutCommonActivity {
    private ClearAndLimitEditText clearAndLimitEditText;
    private CusEditText cusEditText;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_clear_write_edit_text_demo;
    }

    @Override
    protected void findViews() {
        clearAndLimitEditText = (ClearAndLimitEditText) findViewById(R.id.clearAndLimitEditText);
        clearAndLimitEditText.setRegex(ClearAndLimitEditText.DEFAULT_REGEX);

        cusEditText = findViewById(R.id.cusEditText);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        cusEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.i(TAG, "et height ----> "+cusEditText.getEtHeight());
            }
        }, 500);
    }

    @Override
    protected void setListener() {

    }
}
