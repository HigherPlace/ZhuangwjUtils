package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zwj.customview.ClearAndLimitEditText;
import com.zwj.zhuangwjutils.R;

public class ClearWriteEditTextDemo extends AppCompatActivity {
    private ClearAndLimitEditText clearAndLimitEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_write_edit_text_demo);

        clearAndLimitEditText = (ClearAndLimitEditText) findViewById(R.id.clearAndLimitEditText);
        clearAndLimitEditText.setRegex(ClearAndLimitEditText.DEFAULT_REGEX);
    }
}
