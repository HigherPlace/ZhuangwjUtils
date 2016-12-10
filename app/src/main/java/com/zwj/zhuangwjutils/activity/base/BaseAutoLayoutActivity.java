package com.zwj.zhuangwjutils.activity.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;


/**
 * Created by zhy on 15/11/19.
 * 自适配屏幕的基础Activity父类
 */
public abstract class BaseAutoLayoutActivity extends BaseActivity {
	private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
	private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
	private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";


	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View view = null;
		if (name.equals(LAYOUT_FRAMELAYOUT)) {
			view = new AutoFrameLayout(context, attrs);
		}

		if (name.equals(LAYOUT_LINEARLAYOUT)) {
			view = new AutoLinearLayout(context, attrs);
		}

		if (name.equals(LAYOUT_RELATIVELAYOUT)) {
			view = new AutoRelativeLayout(context, attrs);
		}

		if (view != null)
			return view;

		return super.onCreateView(name, context, attrs);
	}

}
