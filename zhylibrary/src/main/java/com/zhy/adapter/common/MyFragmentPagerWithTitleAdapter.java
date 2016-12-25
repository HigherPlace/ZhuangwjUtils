package com.zhy.adapter.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public class MyFragmentPagerWithTitleAdapter extends MyFragmentPagerAdapter {
    private String[] titles;

    public MyFragmentPagerWithTitleAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm, fragments);
        this.titles = titles;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
