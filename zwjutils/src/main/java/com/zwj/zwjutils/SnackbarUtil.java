package com.zwj.zwjutils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zwj on 2017/8/3.
 * Snackbar 工具
 */
public class SnackbarUtil {

    public static final  int NON = -1;


    /**
     *
     * @param view
     * @param content
     * @param duration
     * @param textSize        sp
     * @param textColor
     * @param backgroudColor
     */
    public static void makeSnackbar(View view, String content, int duration, int textSize, int textColor,
                                    int backgroudColor) {

        Snackbar snackbar = Snackbar.make(view, content, duration);

        if(textSize != NON || textColor != NON || backgroudColor != NON) {
            View mView = snackbar.getView();
            TextView tvSnackbarText = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
            if(textSize != NON) {
                tvSnackbarText.setTextSize(textSize);
            }

            if(textColor != NON) {
                tvSnackbarText.setTextColor(textColor);
            }

            if(backgroudColor != NON) {
                mView.setBackgroundColor(backgroudColor);
            }
        }

        snackbar.show();
    }

    public static void makeSnackbar(View view, String content, int duration) {
        makeSnackbar(view, content, duration, NON, NON, NON);
    }
}
