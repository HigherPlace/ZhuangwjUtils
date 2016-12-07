package com.zwj.customview.titleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwj.mycustomview.R;


/**
 * 通用标题栏
 */
public class TitleView extends RelativeLayout implements OnClickListener {
    private TextView tvTitle;
    private ImageView ivBack;
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView ivRight;
    private View bottomLine;


    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.rl_title_bar, this, true);

        initView();
        setListener();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TitleViewAttr, defStyle, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TitleViewAttr_rightIcon) {
                setRightIcon(a.getResourceId(attr, 0));
            } else if (attr == R.styleable.TitleViewAttr_tvtitle) {
                tvTitle.setText(a.getString(attr));

            } else if (attr == R.styleable.TitleViewAttr_rightMenu) {
                setRightMenu(a.getString(attr));

            } else if (attr == R.styleable.TitleViewAttr_leftMenu) {
                setLeftMenu(a.getString(attr));

            }
        }
        a.recycle();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        bottomLine = findViewById(R.id.botton_divider);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
    }

    public void requestRightTvTitleClick() {
        tvRight.performClick();
    }

    /**
     * 标题栏菜单选项点击回调接口
     *
     * @author zhuangwj
     */
    public interface OnTitleMenuClickListener {
        /**
         * 回退
         */
        void onClickBack();

        /**
         * 点击左边文字选项
         */
        void onClickTvLeftListener();

        /**
         * 点击右边文字选项
         */
        void onClickTvRightListener();

        /**
         * 点击右边图片选项
         */
        void onClickImRightListener();
    }

    private OnTitleMenuClickListener mOnTitleMenuClickListener;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_back) {
            if (mOnTitleMenuClickListener != null) {
                mOnTitleMenuClickListener.onClickBack();
            }

        } else if (i == R.id.iv_right) {
            if (mOnTitleMenuClickListener != null) {
                mOnTitleMenuClickListener.onClickImRightListener();
            }

        } else if (i == R.id.tv_left) {
            if (mOnTitleMenuClickListener != null) {
                mOnTitleMenuClickListener.onClickTvLeftListener();
            }

        } else if (i == R.id.tv_right) {
            if (mOnTitleMenuClickListener != null) {
                mOnTitleMenuClickListener.onClickTvRightListener();
            }

        }
    }

    public void setImRightImg(int resId) {
        ivRight.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.GONE);
        ivRight.setImageResource(resId);
    }

    public void setRightMenu(String rightMenu) {
        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(rightMenu);
    }

    public void setBottomLineVisible(boolean isVisible) {
        if (isVisible) {
            bottomLine.setVisibility(View.VISIBLE);
        } else {
            bottomLine.setVisibility(View.GONE);
        }
    }

    /**
     * 获取标题栏右文本菜单内容
     *
     * @return 若标题栏右边有设置文本则返回文本内容，否则返回null
     */
    public String getRightMenu() {
        if (tvRight.getVisibility() == View.VISIBLE) {
            return tvRight.getText().toString().trim();
        }

        return null;
    }

    public void setIvBackVisibility(boolean isShow) {
        if (isShow) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }

    public void setLeftMenu(String leftMenu) {
        ivBack.setVisibility(View.GONE);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(leftMenu);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setRightIcon(int imgResId) {
        tvRight.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(imgResId);
    }

    public void setOnTitleMenuClickListener(OnTitleMenuClickListener listener) {
        mOnTitleMenuClickListener = listener;
    }

    public void setRightTvMenuVisible(boolean isVisible) {
        if (isVisible) {
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setVisibility(View.GONE);
        }
    }

    public void setRightIvMenuVisible(boolean isVisible) {
        if (isVisible) {
            ivRight.setVisibility(View.VISIBLE);
        } else {
            ivRight.setVisibility(View.GONE);
        }
    }
}
