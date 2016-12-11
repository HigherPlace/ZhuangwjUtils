package com.zwj.customview.titleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.autolayout.utils.DimenUtils;
import com.zwj.mycustomview.R;
import com.zwj.zwjutils.DensityUtils;
import com.zwj.zwjutils.LogUtils;


/**
 * 通用标题栏
 */
public class CommonTitleView extends RelativeLayout implements OnClickListener {
    private static final int DEFAULT_TITLE_HEIGHT = 49;
    private static final int DEFAULT_TITLE_SIZE = 22;
    private static final int DEFAULT_ICON_SIZE = 25;
    private static final int DEFAULT_MENU_SIZE = 17;

    private static final int DEFAULT_TITLE_SIZE_AUTO = 40;
    private static final int DEFAULT_MENU_SIZE_AUTO = 30;
    private static final int DEFAULT_ICON_SIZE_AUTO = 90;

    private TextView tvTitle;
    private FrameLayout flLeft, flRight;
    private ImageView ivLeft;
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView ivRight;
    private View bottomLine;

    private int ivLeftWidth, ivLeftHeight;
    private int ivRightWidth, ivRightHeight;
    private int maxFrameImgSize;

    private boolean isAuto;


    public CommonTitleView(Context context) {
        this(context, null);
    }

    public CommonTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        maxFrameImgSize = DensityUtils.dp2px(getContext(), 80);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TitleViewAttr, defStyle, 0);


        // 如果是使用自动适配框架则初始化配置
        isAuto = a.getBoolean(R.styleable.TitleViewAttr_isAuto, false);
        if(isAuto) {
            AutoLayoutConifg.getInstance().init(getContext());
            LayoutInflater.from(context).inflate(R.layout.rl_common_title_bar_px, this, true);
        }else {
            LayoutInflater.from(context).inflate(R.layout.rl_common_title_bar, this, true);
        }

        initView();
        setListener();

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TitleViewAttr_rightIcon) {
                setRightIcon(a.getResourceId(attr, 0));
            } else if (attr == R.styleable.TitleViewAttr_leftIcon) {
                setLeftIcon(a.getResourceId(attr, 0));
            } else if (attr == R.styleable.TitleViewAttr_titleColor) {
                setAllTextColor(a.getColor(attr, 0));
            } else if (attr == R.styleable.TitleViewAttr_titleHeight) {
                // 标题栏高度
                int titleHeight = a.getDimensionPixelSize(attr, DensityUtils.dp2px(getContext(), DEFAULT_TITLE_HEIGHT));
                // TODO 进行高度设置

            } else if (attr == R.styleable.TitleViewAttr_tvtitle) {
                tvTitle.setText(a.getString(attr));
            } else if (attr == R.styleable.TitleViewAttr_tvtitleSize) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getSize(a, attr, DEFAULT_TITLE_SIZE, DEFAULT_TITLE_SIZE_AUTO));
            } else if (attr == R.styleable.TitleViewAttr_leftIconSize) {
                int leftIconSize = getSize(a, attr, DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE_AUTO);
                FrameLayout.LayoutParams ivLeftLp = new FrameLayout.LayoutParams(leftIconSize, leftIconSize);
                ivLeftLp.gravity = Gravity.CENTER;
                ivLeft.setLayoutParams(ivLeftLp);
            } else if (attr == R.styleable.TitleViewAttr_leftIconWidth) {
                ivLeftWidth = getSize(a, attr, DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE_AUTO);
            } else if (attr == R.styleable.TitleViewAttr_leftIconHeight) {
                ivLeftHeight = getSize(a, attr, DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE_AUTO);
            } else if (attr == R.styleable.TitleViewAttr_rightIconSize) {
                int rightIconSize = getSize(a, attr, DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE_AUTO);
                FrameLayout.LayoutParams ivRightLp = (FrameLayout.LayoutParams) ivRight.getLayoutParams();
                ivRightLp.width = rightIconSize;
                ivRightLp.height = rightIconSize;
                ivRight.setLayoutParams(ivRightLp);
            } else if (attr == R.styleable.TitleViewAttr_rightIconWidth) {
                ivRightWidth = getSize(a, attr, DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE_AUTO);
            } else if (attr == R.styleable.TitleViewAttr_rightIconHeight) {
                ivRightHeight = getSize(a, attr, DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE_AUTO);
            } else if (attr == R.styleable.TitleViewAttr_rightMenu) {
                setRightMenu(a.getString(attr));
            } else if (attr == R.styleable.TitleViewAttr_rightMenuSize) {
                tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getSize(a, attr, DEFAULT_MENU_SIZE, DEFAULT_MENU_SIZE_AUTO));
            } else if (attr == R.styleable.TitleViewAttr_leftMenu) {
                setLeftMenu(a.getString(attr));
            } else if (attr == R.styleable.TitleViewAttr_leftMenuSize) {
                tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, getSize(a, attr, DEFAULT_MENU_SIZE, DEFAULT_MENU_SIZE_AUTO));
            }
        }

        if (ivLeftWidth > 0 && ivLeftHeight > 0) {
            FrameLayout.LayoutParams ivLeftLp = new FrameLayout.LayoutParams(ivLeftWidth, ivLeftHeight);
            ivLeftLp.gravity = Gravity.CENTER;
            ivLeft.setLayoutParams(ivLeftLp);
        }

        if (ivRightWidth > 0 && ivRightHeight > 0) {
            FrameLayout.LayoutParams ivRightLp = (FrameLayout.LayoutParams) ivRight.getLayoutParams();
            ivRightLp.width = ivRightWidth;
            ivRightLp.height = ivRightHeight;
            ivRight.setLayoutParams(ivRightLp);
        }


        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = MeasureSpec.getSize(heightMeasureSpec);
        LogUtils.sysout("title onMeasure --> " + height);
        LogUtils.sysout("maxFrameImgSize --> " + maxFrameImgSize);
        if (height > 0) {
            LayoutParams flLeftLp = (LayoutParams) flLeft.getLayoutParams();

            if (height > maxFrameImgSize) {
                flLeftLp.width = maxFrameImgSize;
            } else {
                flLeftLp.width = height;
            }

            flLeftLp.height = height;
            flLeft.setLayoutParams(flLeftLp);

            LayoutParams flRightLp = (LayoutParams) flRight.getLayoutParams();
            if (height > maxFrameImgSize) {
                flRightLp.width = maxFrameImgSize;
            } else {
                flRightLp.width = height;
            }
            flRightLp.height = height;
            flRight.setLayoutParams(flRightLp);
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        bottomLine = findViewById(R.id.botton_divider);
        flLeft = (FrameLayout) findViewById(R.id.fl_left);
        flRight = (FrameLayout) findViewById(R.id.fl_right);
    }

    private void setListener() {
        flLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        flRight.setOnClickListener(this);
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
        void onClickImLeftListener();

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
        if (i == R.id.fl_left) {
            if (mOnTitleMenuClickListener != null) {
                mOnTitleMenuClickListener.onClickImLeftListener();
            }

        } else if (i == R.id.fl_right) {
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
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setVisibility(View.GONE);
        }
    }

    public void setLeftMenu(String leftMenu) {
        ivLeft.setVisibility(View.GONE);
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

    public void setLeftIcon(int imgResId) {
        tvLeft.setVisibility(View.GONE);
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(imgResId);
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

    private int getSize(TypedArray typedArray, int attr, int defaultSize, int defaultSizeAuto) {
        int size = 0;
        if(isAuto && DimenUtils.isPxVal(typedArray.peekValue(attr))) {
            size = AutoUtils.getPercentWidthSize(typedArray.getDimensionPixelSize(attr, defaultSizeAuto));
        }else {
            size = typedArray.getDimensionPixelSize(attr, DensityUtils.dp2px(getContext(), defaultSize));
        }

//        LogUtils.sysout("size --> "+size);
        return size;
    }

    private void setAllTextColor(int color) {
        tvTitle.setTextColor(color);
        tvLeft.setTextColor(color);
        tvRight.setTextColor(color);
    }

    /**
     * 将默认的dp改为px
     */
    private void changeSize() {

    }
}
