package com.zwj.zhuangwjutils.activity.customviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zwj.customview.centerviewpager.CenterViewPager;
import com.zwj.customview.centerviewpager.CenterViewPagerAdapter;
import com.zwj.customview.centerviewpager.ZoomOutPageTransformer;
import com.zwj.zhuangwjutils.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CenterPagerDemoActivity extends AppCompatActivity {
    private CenterViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_pager);
        viewPager = (CenterViewPager)this.findViewById(R.id.centerViewPager);
        initPager();
    }

    private void initPager() {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            View card = new View(this);
            card.setBackgroundColor(getRandomColor());
            card.setTag(i);
            views.add(card);
        }
        CenterViewPagerAdapter adapter = new CenterViewPagerAdapter(this, views);
        viewPager.setAdapter(adapter);
        viewPager.enableCenterLockOfChilds();
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
