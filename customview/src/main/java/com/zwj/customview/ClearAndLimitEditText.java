package com.zwj.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zwj.mycustomview.R;


/**
 * Created by AMing on 15/11/2.
 * Company RongCloud
 */
public class ClearAndLimitEditText extends RelativeLayout implements TextWatcher {
    private EditText et;
    private ImageView ivClear;


    /**
     * 筛选条件
     */
    private String regex;
    /**
     * 默认的筛选条件(正则:只能输入中文)
     */
    public static final String DEFAULT_REGEX = "[^\u4E00-\u9FA5]";


    /**
     * 检测筛选条件时回调
     */
    private interface InputCharacterCheckListener {
        /**
         * 当有输入不符合的字符时回调
         */
        void onInputInconformityCharacter();
    }

    private InputCharacterCheckListener mInputCharacterCheckListener;


    public ClearAndLimitEditText(Context context) {
        this(context, null);
    }

    public ClearAndLimitEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearAndLimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_clear_limit_edittext, this, true);

        initView();
        setListener();
        ivClear.setVisibility(View.GONE);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ClearWriteEditTextAtrr, defStyleAttr, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.ClearWriteEditTextAtrr_clearIcon) {
                int clearIconId = a.getResourceId(attr, 0);
                if (clearIconId != 0) {
//                    mClearDrawable = getResources().getDrawable(clearIconId);
                    ivClear.setImageResource(clearIconId);
                } else {
                    // TODO 默认的
                }
            }
        }
        a.recycle();
    }

    private void initView() {
        et = (EditText) findViewById(R.id.et);
        ivClear = (ImageView) findViewById(R.id.iv_clear);
    }

    private void setListener() {
        et.addTextChangedListener(this);
        ivClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                ivClear.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!TextUtils.isEmpty(regex)) {
            String str = editable.toString();
            String inputStr = clearLimitStr(regex, str);

            if (!str.equals(inputStr)) {
                et.removeTextChangedListener(this);
                // et.setText方法可能会引起键盘变化,所以用editable.replace来显示内容
                editable.replace(0, editable.length(), inputStr.trim());
                et.addTextChangedListener(this);

                if(mInputCharacterCheckListener != null) {
                    mInputCharacterCheckListener.onInputInconformityCharacter();
                }
            }
        }

        String str = editable.toString();
        if(TextUtils.isEmpty(str)) {
            ivClear.setVisibility(View.GONE);
        }else {
            ivClear.setVisibility(View.VISIBLE);
        }
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

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void setInputCharacterCheckListener(InputCharacterCheckListener listener) {
        this.mInputCharacterCheckListener = listener;
    }

    /**
     * 清除不符合条件的内容
     *
     * @param regex
     * @return
     */
    private String clearLimitStr(String regex, String str) {
        return str.replaceAll(regex, "");
    }
}
