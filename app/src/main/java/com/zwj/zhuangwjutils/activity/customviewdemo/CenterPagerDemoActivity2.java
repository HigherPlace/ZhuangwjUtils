package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.zwj.customview.centerviewpager.CenterFragmentPageAdapter;
import com.zwj.customview.centerviewpager.CenterViewPager;
import com.zwj.customview.centerviewpager.ZoomOutPageTransformer;
import com.zwj.zhuangwjutils.R;

import java.util.ArrayList;
import java.util.List;

public class CenterPagerDemoActivity2 extends AppCompatActivity {
    private CenterViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_pager);
        viewPager = (CenterViewPager)this.findViewById(R.id.centerViewPager);
        initPager();
    }

    private void initPager() {
        List<Fragment> views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CenterPagerFragment centerPagerFragment = new CenterPagerFragment();
            views.add(centerPagerFragment);
        }
        CenterFragmentPageAdapter adapter = new CenterFragmentPageAdapter(getSupportFragmentManager(), views, 0.7f);
        viewPager.setAdapter(adapter);
        viewPager.enableCenterLockOfChilds();
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer(adapter.getWidthSacle()));
    }

}
