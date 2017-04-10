package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwj.customview.centerviewpager.CenterFragmentPageAdapter;
import com.zwj.customview.centerviewpager.CenterViewPager;
import com.zwj.customview.centerviewpager.ZoomOutPageTransformer;
import com.zwj.zhuangwjutils.R;
import com.zwj.zwjutils.DensityUtils;
import com.zwj.zwjutils.LogUtils;

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
        LogUtils.sysout("px width -----> "+ DensityUtils.dp2px(this, 100));

        viewPager.setCurrentItemInCenter(1);


        viewPager.setOnPageChangeListener(new CenterViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                LogUtils.sysout("onPageSelected --> "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

        ZoomOutPageTransformer zoomOutPageTransformer = new ZoomOutPageTransformer(adapter.getWidthSacle());
        zoomOutPageTransformer.setScaleCallBack(new ZoomOutPageTransformer.ScaleCallBack() {
            @Override
            public void onScale(View view, float scaleFactor) {
                TextView tv = (TextView) view.findViewById(R.id.tv);
                RelativeLayout.LayoutParams lps = (RelativeLayout.LayoutParams) tv.getLayoutParams();
                LogUtils.sysout("width -----> "+lps.width);
                LogUtils.sysout("margin left -----> "+lps.leftMargin);
            }
        });
        viewPager.setPageTransformer(true, zoomOutPageTransformer);
    }

}
