package com.zhongtie.work.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Cheek on 2017.7.11.
 */

public class KingYunUpdateView extends View {

    /**
     * 下拉距离
     */
    private int dropDown = 0;
    private int maxUpdateHeight = 300;
    private Paint paint;
    private RectF oval;

    public void setDropDown(int dropDown) {
        this.dropDown = dropDown;
        if (this.dropDown > maxUpdateHeight) {
            this.dropDown = maxUpdateHeight;
        }
        invalidate();
    }

    public KingYunUpdateView(Context context) {
        super(context);
        initView();
    }


    public KingYunUpdateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                w, getContext().getResources().getDisplayMetrics());
    }

    public KingYunUpdateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //根据分数显示圆环的颜色
        float progress = ((float) dropDown / (float) maxUpdateHeight) * (float) 360;
        paint.setColor(Color.parseColor("#999999")); // 设置圆环的颜色
        canvas.drawArc(oval, -90, progress, false, paint); // 根据进度画圆弧
        paint.setColor(Color.parseColor("#f1f1f1"));
        canvas.drawArc(oval, -90 + progress, 360 - progress, false, paint);
    }

    private void initView() {
        maxUpdateHeight = getDpValue(60);
        int circleCenter = getDpValue(15);
        int radius = (int) (circleCenter - getDpValue(1)); // 圆环的半径
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE); // 设置空心
        paint.setStrokeWidth(getDpValue(1)); // 设置圆环的宽度
        paint.setAntiAlias(true); // 消除锯齿
        oval = new RectF(circleCenter - radius, circleCenter - radius, circleCenter + radius, circleCenter + radius); // 用于定义的圆弧的形状和大小的界限
    }

}
