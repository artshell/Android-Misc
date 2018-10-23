package com.artshell.effects.TextView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 圆角矩形背景，可变色TextView
 * Created With Android Studio.
 * Author:zhangjianliang
 * Date:2017/3/11.
 */
public class ColorTagView extends AppCompatTextView {

    private GradientDrawable mDrawable;
    private static final String DEFAULT_COLOR = "#FF4433";
    private int mStrokeWidth = 1;

    public ColorTagView(Context context){
        this(context,null);
    }

    public ColorTagView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }

    public ColorTagView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init();
    }

    private void init(){
        mDrawable = new GradientDrawable();
        mDrawable.setStroke(mStrokeWidth,Color.parseColor(DEFAULT_COLOR));
    }

    public void setStrokeWidth(int strokeWidth){
        mStrokeWidth = strokeWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        mDrawable.setCornerRadius(getMeasuredHeight() / 2);
    }

    private GradientDrawable getDrawable(){
        if(mDrawable == null){
            mDrawable = new GradientDrawable();
            mDrawable.setStroke(mStrokeWidth,Color.parseColor(DEFAULT_COLOR));
            mDrawable.setCornerRadius(getMeasuredHeight() / 2);
        }
        return mDrawable;
    }

    /**
     * 设置文字及背景颜色
     * @param color
     */
    public void setColor(String color){
        setTextColor(Color.parseColor(color));
        getDrawable().setStroke(mStrokeWidth,Color.parseColor(color));
        if(Build.VERSION.SDK_INT >= 16){
            setBackground(getDrawable());
        }else {
            setBackgroundDrawable(getDrawable());
        }
    }
}
