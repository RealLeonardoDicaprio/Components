package com.joy.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class TitleBar extends RelativeLayout {
    private static final int defaultTitleTextSize = 18;
    private static final int defaultButtonTextSize = 14;
    private static final int defaultTitleTextColor = 0xffffffff;
    private ImageButton mNavButtonView;
    private ImageButton mNegButtonView;
    private Drawable navIco, negIco;
    private String titleText, leftText, rightText;
    private AppCompatTextView mTitleView, mLeftTextView, mRightTextView;
    private int titleTextColor, leftTextColor, rightTextColor;
    private float titleTextSize, leftTextSize, rightTextSize;
    private int mTextAppearance;
    private int padding = 30;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private final ArrayList<View> hiddenViews = new ArrayList<>();

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);
        initAttrs(a);
        if (!TextUtils.isEmpty(titleText)) {
            setTitle(titleText);
        }

        if (navIco != null) {
            setNavIco(navIco);
        }

        if (negIco != null) {
            setNegIco(negIco);
        }

        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }

        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }
        initPaddingAndGravity();
        a.recycle();
    }


    private void initPaddingAndGravity() {
        paddingLeft = paddingBottom = paddingTop = paddingRight = padding;
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        setGravity(Gravity.CENTER_VERTICAL);
    }


    private void initAttrs(TypedArray ta) {
        for (int i = 0; i < ta.getIndexCount(); i++) {
            final int index = ta.getIndex(i);
            switch (index) {
                case R.styleable.TitleBar_titleAppearance:
                    mTextAppearance = ta.getResourceId(R.styleable.TitleBar_titleAppearance, 0);
                    break;
                case R.styleable.TitleBar_bar_title:
                    titleText = getAttrsString(ta, R.styleable.TitleBar_bar_title);
                    break;
                case R.styleable.TitleBar_bar_titleTextColor:
                    titleTextColor = ta.getColor(R.styleable.TitleBar_bar_titleTextColor, 0);
                    break;
                case R.styleable.TitleBar_bar_titleTextSize:
                    titleTextSize = ta.getDimension(R.styleable.TitleBar_bar_titleTextSize, 16);
                    break;
                case R.styleable.TitleBar_bar_padding:
                    padding = ta.getDimensionPixelSize(R.styleable.TitleBar_bar_padding, 0);
                    break;
                case R.styleable.TitleBar_nav_ico:
                    navIco = ta.getDrawable(index);
                    break;
                case R.styleable.TitleBar_neg_ico:
                    negIco = ta.getDrawable(index);
                    break;
                case R.styleable.TitleBar_leftText:
                    leftText = getAttrsString(ta, R.styleable.TitleBar_leftText);
                    break;
                case R.styleable.TitleBar_leftTextColor:
                    leftTextColor = ta.getColor(R.styleable.TitleBar_leftTextColor, 0);
                    break;
                case R.styleable.TitleBar_leftTextSize:
                    leftTextSize = ta.getDimension(R.styleable.TitleBar_leftTextSize, 16);
                    break;
                case R.styleable.TitleBar_rightText:
                    rightText = getAttrsString(ta, R.styleable.TitleBar_rightText);
                    break;
                case R.styleable.TitleBar_rightTextColor:
                    rightTextColor = ta.getColor(R.styleable.TitleBar_rightTextColor, 0);
                    break;
                case R.styleable.TitleBar_rightTextSize:
                    rightTextSize = ta.getDimension(R.styleable.TitleBar_rightTextColor, 16);
                    break;
            }

        }
    }


    public void setNavIco(@NonNull Drawable drawable) {
        if (drawable != null) {
            enSureNavIcoView();
            if (!isChildOrHidden(mNavButtonView)) {
                mNavButtonView.setImageDrawable(drawable);
                addView(mNavButtonView);
            }

        }
    }

    public void setNegIco(@NonNull Drawable drawable) {
        if (drawable != null) {
            enSureNagIcoView();
            if (!isChildOrHidden(mNegButtonView)) {
                mNegButtonView.setImageDrawable(drawable);
                addView(mNegButtonView);
            }

        }
    }

    public void setOnButtonClickListener(final OnClickListener listener) {
        if (mLeftTextView != null) {
            mLeftTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNavClick(v);
                }
            });
        }
        if (mRightTextView != null) {
            mLeftTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNegClick(v);
                }
            });
        }

        if (mNavButtonView != null) {
            mNavButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNavClick(v);
                }
            });
        }

        if (mNegButtonView != null) {
            mNegButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNegClick(v);
                }
            });
        }
    }

    private void enSureNavIcoView() {
        if (mNavButtonView == null) {
            mNavButtonView = new ImageButton(getContext(), null, 0);
            LayoutParams params = generateDefaultLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mNavButtonView.setLayoutParams(params);
        }
    }

    private void enSureNagIcoView() {
        if (mNegButtonView == null) {
            mNegButtonView = new ImageButton(getContext(), null, 0);
            LayoutParams params = generateDefaultLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mNegButtonView.setLayoutParams(params);
        }
    }

    private String getAttrsString(TypedArray ta, int index) {
        return ta.hasValue(index) ? ta.getString(index) : "";
    }


    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            enSureTitleTextView();
            setText(mTitleView, title, titleTextSize, titleTextColor);
            if (!isChildOrHidden(mTitleView)) {
                LayoutParams params = generateDefaultLayoutParams();
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                mTitleView.setLayoutParams(params);
                addView(mTitleView);
            }

        }
    }


    public void setLeftText(String leftText) {
        if (!TextUtils.isEmpty(leftText)) {
            enSureLeftTextView();
            setText(mLeftTextView, leftText, leftTextSize, leftTextColor);
            if (!isChildOrHidden(mLeftTextView)) {
                LayoutParams params = generateDefaultLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                mLeftTextView.setLayoutParams(params);
                addView(mLeftTextView);
            }

        }
    }

    public void setRightText(String rightText) {
        if (!TextUtils.isEmpty(rightText)) {
            enSureRightTextView();
            setText(mRightTextView, rightText, rightTextSize, rightTextColor);
            if (!isChildOrHidden(mRightTextView)) {
                LayoutParams params = generateDefaultLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                mRightTextView.setLayoutParams(params);
                addView(mRightTextView);
            }

        }
    }

    private void setText(@NonNull AppCompatTextView tv, CharSequence text, float textSize, int textColor) {
        if (!TextUtils.isEmpty(text)) {
            if (tv != null) {
                if (textSize != 0) {
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                if (mTextAppearance != 0) {
                    tv.setTextAppearance(getContext(), mTextAppearance);
                }
                if (textColor != 0) {
                    tv.setTextColor(textColor);
                } else {
                    tv.setTextColor(defaultTitleTextColor);
                }
                tv.setText(text);
            }
        }

    }


    private void enSureTitleTextView() {
        if (mTitleView == null) {
            mTitleView = generateAppcompatTextView();
            mTitleView.setTextSize(defaultTitleTextSize);
        }

    }

    private void enSureLeftTextView() {
        if (mLeftTextView == null) {
            mLeftTextView = generateAppcompatTextView();
            mLeftTextView.setTextSize(defaultButtonTextSize);
        }
    }

    private void enSureRightTextView() {
        if (mRightTextView == null) {
            mRightTextView = generateAppcompatTextView();
            mRightTextView.setTextSize(defaultButtonTextSize);
        }
    }


    private AppCompatTextView generateAppcompatTextView() {
        final Context context = getContext();
        AppCompatTextView tv = new AppCompatTextView(context);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        return tv;
    }


    private boolean isChildOrHidden(View view) {
        return view.getParent() == this || hiddenViews.contains(view);
    }


    @Override
    protected RelativeLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new RelativeLayout.LayoutParams(p);
    }


    @Override
    protected RelativeLayout.LayoutParams generateDefaultLayoutParams() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof RelativeLayout.LayoutParams;
    }

    public interface OnClickListener {

        void onNavClick(View v);

        void onNegClick(View v);

    }

}
