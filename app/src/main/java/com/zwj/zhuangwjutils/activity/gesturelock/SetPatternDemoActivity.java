/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.zwj.zhuangwjutils.activity.gesturelock;

import com.zwj.customview.gesturelock.PatternView;
import com.zwj.customview.gesturelock.utils.PatternLockUtils;

import java.util.List;

public class SetPatternDemoActivity extends SetPatternActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        ThemeUtils.applyTheme(this);
//
//        super.onCreate(savedInstanceState);
//
//        AppUtils.setActionBarDisplayUp(this);
//    }


    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        PatternLockUtils.setPattern(pattern, this);
    }
}
