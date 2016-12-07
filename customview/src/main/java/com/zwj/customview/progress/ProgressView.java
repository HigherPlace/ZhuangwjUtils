package com.zwj.customview.progress;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.zwj.mycustomview.R;

/**
 * Created by bryan on 2016/6/13.
 */
public class ProgressView extends RelativeLayout {

    //提示文字
    TextView tvTip;
    //加载动画
    private Sprite mCircleDrawable;
    private SpinKitView spnite;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        inflate(context, R.layout.view_loading, this);
        spnite= (SpinKitView) findViewById(R.id.spin_kit);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        FadingCircle _circleDrawable = new FadingCircle();
        spnite.setIndeterminateDrawable(_circleDrawable);

    }

    public void setTipContent(String tipStr) {
        if (!TextUtils.isEmpty(tipStr)) {
            tvTip.setText(tipStr);
            invalidate();
        }
    }
}
