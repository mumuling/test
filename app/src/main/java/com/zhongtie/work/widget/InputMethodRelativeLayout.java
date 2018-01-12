package com.zhongtie.work.widget;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;


/**
 * 自定义布局解决键盘弹出挡住输入框的问题
 */
public class InputMethodRelativeLayout extends RelativeLayout {
    private int width;
    protected OnInputMethodChangedListener onSizeChangedListener;
    private int height;
    private int screenWidth; //屏幕宽度
    private int screenHeight; //屏幕高度

    public InputMethodRelativeLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.screenWidth = getScreenWidth(getContext());
        this.screenHeight = getScreenWidth(getContext());
    }

    public InputMethodRelativeLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = widthMeasureSpec;
        this.height = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if ((this.onSizeChangedListener != null) && (w == oldw) && (oldw != 0) && (oldh != 0)) {
            if ((h >= oldh) || (Math.abs(h - oldh) <= this.screenHeight / 4)) {
                if ((h <= oldh) || (Math.abs(h - oldh) <= this.screenHeight / 4)) {
                    return;
                }
                onSizeChangedListener.hideInputMethod();
            } else {
                onSizeChangedListener.showInputMethod();
            }
            measure(this.width - w + getWidth(), this.height - h + getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            try {
                IBinder iBinder = this.getWindowToken();
                if (iBinder != null) {
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 设置监听事件
     */
    public void setInputMethodChangedListener(OnInputMethodChangedListener onSizeChangedListener) {
        this.onSizeChangedListener = onSizeChangedListener;
    }

    public interface OnInputMethodChangedListener {
        void showInputMethod();

        void hideInputMethod();

    }

    public int getScreenWidth(Context context) {
        return ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
    }

    public int getScreenHeight(Context context) {
        return ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
    }

}