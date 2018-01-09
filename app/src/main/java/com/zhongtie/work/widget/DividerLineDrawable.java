package com.zhongtie.work.widget;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;

public class DividerLineDrawable extends GradientDrawable {
    private Paint linePaint;
    private int lineColor = Color.BLUE;

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        setColor(lineColor);
        invalidateSelf();
    }

    public DividerLineDrawable() {
        setShape(GradientDrawable.RECTANGLE);
        setColor(Color.parseColor("#eeeeee"));
    }


}
