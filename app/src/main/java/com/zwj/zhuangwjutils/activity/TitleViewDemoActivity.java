package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;
import android.view.View;

import com.zwj.customview.titleview.CommonTitleView;
import com.zwj.customview.titleview.SimpleTitleMenuClickListener;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;
import com.zwj.zwjutils.ToastUtil;

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
        titleView.setOnTitleMenuClickListener(new SimpleTitleMenuClickListener(){
            @Override
            public void onClickImLeftListener() {
                ToastUtil.toast(mContext.getApplicationContext(), "onClickImLeftListener");
            }
        });

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.toast(mContext.getApplicationContext(), "click title");
            }
        });
    }
}
