package com.zwj.customview.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;

public abstract class BasePopupWindow extends PopupWindow {
	private String TAG = getClass().getSimpleName();
	/**
	 * 布局文件的最外层View
	 */
	protected View mContentView;
	protected Context context;

	public BasePopupWindow(View contentView, int width, int height,
			boolean focusable) {
		this(contentView, width, height, focusable, new Object[0]);
	}

	public BasePopupWindow(View contentView, int width, int height,
			boolean focusable, Object... params) {
		super(contentView, width, height, focusable);
		this.mContentView = contentView;
		context = contentView.getContext();

		if (params != null && params.length > 0) {
			beforeInitWeNeedSomeParams(params);
		}

		setBackgroundDrawable(new BitmapDrawable());
		setTouchable(true);
		setOutsideTouchable(true);
		setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		initViews();
		initEvents();
		init();
	}

	protected abstract void beforeInitWeNeedSomeParams(Object... params);

	public abstract void initViews();

	public abstract void initEvents();

	public abstract void init();

	public View findViewById(int id) {
		return mContentView.findViewById(id);
	}

}
