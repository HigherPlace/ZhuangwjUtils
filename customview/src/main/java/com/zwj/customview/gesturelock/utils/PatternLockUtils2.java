/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.zwj.customview.gesturelock.utils;

import android.content.Context;
import android.text.TextUtils;

import com.zwj.customview.gesturelock.PatternUtils;
import com.zwj.customview.gesturelock.PatternView;
import com.zwj.zwjutils.SPUtil;

import java.util.List;


public class PatternLockUtils2 {

    private PatternLockUtils2() {
    }

    public static void setPattern(Context context, String key, List<PatternView.Cell> pattern) {
        SPUtil.putString(context, key, PatternUtils.patternToSha1String(pattern));
    }

    private static String getPatternSha1(Context context, String key) {
        return SPUtil.getString(context, key);
    }

    public static boolean hasPattern(Context context, String key) {
        return !TextUtils.isEmpty(getPatternSha1(context, key));
    }

    public static boolean isPatternCorrect(List<PatternView.Cell> pattern, Context context, String key) {
        return TextUtils.equals(PatternUtils.patternToSha1String(pattern), getPatternSha1(context, key));
    }

    public static void clearPattern(Context context, String key) {
        SPUtil.putString(context, key, "");
    }
}
