package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.fragment.base.BaseFragment;

import java.util.Random;

public class CenterPagerFragment extends BaseFragment {
    private RelativeLayout flContent;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_center_pager;
    }

    @Override
    protected void initView() {
        flContent = getView(R.id.contentView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        flContent.setBackgroundColor(getRandomColor());
    }

    @Override
    protected void setListener() {

    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
