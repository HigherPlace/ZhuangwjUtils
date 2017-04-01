package com.zwj.customview.centerviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zwj on 2017/4/1.
 */

public class CenterFragmentPageAdapter extends FragmentPagerAdapter {
    protected List<Fragment> fragments;
    protected float widthSacle = 0.8f;

    public CenterFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public CenterFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments, float widthSacle) {
        this(fm, fragments);
        this.widthSacle = widthSacle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public float getPageWidth(int position) {
        return widthSacle;
    }

    public float getWidthSacle() {
        return widthSacle;
    }

    public void setWidthSacle(float widthSacle) {
        this.widthSacle = widthSacle;
    }
}
