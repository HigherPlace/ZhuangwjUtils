package com.zwj.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.autolayout.utils.DimenUtils;
import com.zwj.mycustomview.R;
import com.zwj.zwjutils.CommonUtil;
import com.zwj.zwjutils.DensityUtils;


/**
 * Created by AMing on 15/11/2.
 * Company RongCloud
 */
public class CusEditText extends RelativeLayout implements TextWatcher {
    private static final String TAG = "CusEditText";

    private static final int DEFAULT_CUS_EDITTEXT_HEIGHT = 40;
    private static final int DEFAULT_CUS_EDITTEXT_HEIGHT_AUTO = 120;

    private EditText et;
    private ImageView ivClear, ivShow;
    private LinearLayout llBtn;
    private boolean isShowContent;
    private boolean useClear, useChangeShowModel;
    private int etHeight;


    public CusEditText(Context context) {
        this(context, null);
    }

    public CusEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_cus_edittext, this, true);

        initView();
        setListener();
        ivClear.setVisibility(View.GONE);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CusEditTextAtrr, defStyleAttr, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CusEditTextAtrr_clearIcon) {
                int clearIconId = a.getResourceId(attr, 0);
                if (clearIconId != 0) {
                    ivClear.setImageResource(clearIconId);
                }
            } else if (attr == R.styleable.CusEditTextAtrr_useClear) {
                useClear = a.getBoolean(attr, true);
            } else if (attr == R.styleable.CusEditTextAtrr_showIcon) {
                int showIconId = a.getResourceId(attr, 0);
                if (showIconId != 0) {
                    ivShow.setImageResource(showIconId);
                }
            } else if (attr == R.styleable.CusEditTextAtrr_useChangeShowModel) {
                useChangeShowModel = a.getBoolean(attr, true);
            }
//            else if(attr == R.styleable.CusEditTextAtrr_cusEditTextHeight) {
//                int etHeight = getSize(a, attr, DEFAULT_CUS_EDITTEXT_HEIGHT, DEFAULT_CUS_EDITTEXT_HEIGHT_AUTO);
//
////                RelativeLayout.LayoutParams ivLeftLp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, etHeight);
////                et.setLayoutParams(ivLeftLp);
//            }
        }
        a.recycle();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//    }

    private void initView() {
        et = findViewById(R.id.et);
        ivClear = findViewById(R.id.iv_clear);
        ivShow = findViewById(R.id.iv_show);
        llBtn = findViewById(R.id.ll_btn);
    }

    private void setListener() {
        et.addTextChangedListener(this);
        et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    EditText editText = (EditText) v;
                    CommonUtil.showView(ivClear, editText.getText().length() > 0);
                } else {
                    CommonUtil.showView(ivClear, false);
                }
            }
        });

        ivClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                ivClear.setVisibility(View.GONE);
            }
        });

        ivShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowContent = !isShowContent;

                String content = et.getText().toString();
                if (isShowContent) {
                    et.setInputType(0x90);
                    ivShow.setImageResource(R.drawable.button_visible);
                } else {
                    ivShow.setImageResource(R.drawable.button_invisible);
                    et.setInputType(0x81);
                }
                et.setSelection(content.length());
            }
        });
    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.startAnimation(shakeAnimation(3));
    }


    /**
     * 晃动动画
     *
     * @param counts 半秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        CommonUtil.showView(ivClear, s.length() > 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private boolean isAuto = true;
    private int getSize(TypedArray typedArray, int attr, int defaultSize, int defaultSizeAuto) {
        int size = 0;
        if(isAuto && DimenUtils.isPxVal(typedArray.peekValue(attr))) {
            size = AutoUtils.getPercentWidthSize(typedArray.getDimensionPixelSize(attr, defaultSizeAuto));
        }else {
            size = typedArray.getDimensionPixelSize(attr, DensityUtils.dp2px(getContext(), defaultSize));
        }

        return size;
    }

    public int getEtHeight() {
        if(et != null) {
            return et.getHeight();
        }
        return 0;
    }
}
