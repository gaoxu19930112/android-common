package com.android.common.core.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.aorise.common.R;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 可以查看密文和一次性删除内容的EditText
 *     version: 1.0
 * </pre>
 */
public class ClearEyeEditText extends RelativeLayout implements
        OnFocusChangeListener, TextWatcher, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    /**
     * 输入框
     */
    private EditText mEditText;
    /**
     * 显示密码
     */
    private CheckBox mIvEye;
    /**
     * 删除按钮
     */
    private ImageView mIvDelete;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    public ClearEyeEditText(Context context) {
        super(context);
    }

    public ClearEyeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEyeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.common_view_edittext, this);
        initView();
        initStyleAttr(context, attrs);
    }

    /**
     * 获得我们所定义的自定义样式属性
     *
     * @param context
     * @param attrs
     */
    private void initStyleAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEyeEditText);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (R.styleable.ClearEyeEditText_hint == attr) {
                String hint = typedArray.getString(attr);
                mEditText.setHint(hint);
            } else if (R.styleable.ClearEyeEditText_toggleEyeEnable == attr) {
                boolean toggle = typedArray.getBoolean(attr, false);
                if (toggle) {
                    mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                mIvEye.setVisibility(toggle ? View.VISIBLE : View.GONE);
            }
        }
        typedArray.recycle();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.edit_text);
        mIvEye = (CheckBox) findViewById(R.id.iv_eye);
        mIvDelete = (ImageView) findViewById(R.id.iv_delete);

        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        mEditText.setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        mEditText.addTextChangedListener(this);

        mIvEye.setOnCheckedChangeListener(this);
        mIvDelete.setOnClickListener(this);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(mEditText.getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        mIvDelete.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.iv_delete == id) {
            mEditText.setText("");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (PasswordTransformationMethod.getInstance() == mEditText.getTransformationMethod()) {
            // 显示密码
            mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // 隐藏密码
            mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mEditText.setSelection(mEditText.getText().length());
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(R.integer.common_anim_shake_duration);
        return translateAnimation;
    }

    public EditText getEditText() {
        return mEditText;
    }
}
