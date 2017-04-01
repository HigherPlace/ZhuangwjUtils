package com.zwj.customview.centerviewpager;

/**
 * Created by MRWANG on 2016/2/24.
 */
import android.annotation.SuppressLint;
import android.view.View;

import com.zwj.zwjutils.LogUtils;

/**
 * viewpager过场动画，扩放效果
 */
public class ZoomOutPageTransformer implements CenterViewPager.PageTransformer {
    private static float MIN_SCALE = 0.85f;
    private static float MIN_ALPHA = 0.5f;
    private float widthScale;

    public ZoomOutPageTransformer(float widthScale) {
        super();
        this.widthScale = widthScale;
    }

    @SuppressLint("NewApi")
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        LogUtils.sysout("pageWidth ---> "+pageWidth);

        if(position > -1 && position < 0) {
            position -= (1 - widthScale);
        }

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to
            // shrink the page as well
            //float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

            LogUtils.sysout("position ---> "+position);
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            LogUtils.sysout("scaleFactor ---> "+scaleFactor);

            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                LogUtils.sysout("<<0 ---> "+(horzMargin - vertMargin / 2));
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                LogUtils.sysout(">>0 ---> "+(-horzMargin + vertMargin / 2));
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}