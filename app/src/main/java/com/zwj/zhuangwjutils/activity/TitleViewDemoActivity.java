package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;

import com.zwj.customview.titleview.CommonTitleView;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;

public class TitleViewDemoActivity extends BaseAutoLayoutCommonActivity {
    private CommonTitleView titleView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_title_view_demo;
    }

    @Override
    protected void findViews() {
        titleView = getView(R.id.id_title);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }
}
