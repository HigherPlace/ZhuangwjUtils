package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.os.Bundle;
import android.widget.EditText;

import com.zwj.customview.LimitInputTextWatcher;
import com.zwj.zhuangwjutils.MyApplication;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;
import com.zwj.zwjutils.ToastUtil;

/**
 * Created by zwj on 2017/6/12.
 */

public class LimitInputTextActivity extends BaseAutoLayoutCommonActivity {
    private EditText et;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_limit_input_text;
    }

    @Override
    protected void findViews() {
        et = getView(R.id.et);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        // 匹配除了中文外的其他数字、字符等
        et.addTextChangedListener(new LimitInputTextWatcher(et, "[\\u4e00-\\u9fa5]") {
            @Override
            public void onInputInconformityCharacter() {
                ToastUtil.toast(MyApplication.getGlobalContext(), "请不要输入中文!");
            }
        });
    }

    @Override
    protected void setListener() {

    }
}
