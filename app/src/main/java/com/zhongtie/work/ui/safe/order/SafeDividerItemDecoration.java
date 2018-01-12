package com.zhongtie.work.ui.safe.order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.DividerLineDrawable;


/**
 * RecyclerView 分割线
 */
public class SafeDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int paddingLeft = 0;
    private int dividerHeight = 1;

    private int lineColor;

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        mDivider.setColor(lineColor);
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = ViewUtils.dip2px(paddingLeft);
    }

    public void setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private GradientDrawable mDivider;

    private int mOrientation;
    private boolean isTopLine;
    private int endPosition;
    private DividerLineDrawable lineDrawable;
    private DividerLineDrawable topLine;
    private DividerLineDrawable bottomLine;
    private int color;

    public void setTopLine(boolean topLine) {
        isTopLine = topLine;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }

    public SafeDividerItemDecoration(Context context, int orientation) {
        mDivider = new DividerLineDrawable();
        lineDrawable = new DividerLineDrawable();
        color = ContextCompat.getColor(App.getInstance(), R.color.line2);
        topLine = new DividerLineDrawable();
        topLine.setLineColor(color);

        bottomLine = new DividerLineDrawable();
        bottomLine.setLineColor(color);

        lineDrawable.setLineColor(ContextCompat.getColor(App.getInstance(), R.color.filtrate_select_color));
        setOrientation(orientation);
    }

    public SafeDividerItemDecoration(Context context) {
        this(context, VERTICAL_LIST);
    }

    public SafeDividerItemDecoration(Context context, boolean isTopLine) {
        this(context, VERTICAL_LIST);
        this.isTopLine = isTopLine;
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }


    private void drawVertical(Canvas c, RecyclerView parent) {
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int p = params.getViewLayoutPosition();
            final int top = child.getBottom() + params.bottomMargin;
            int line = endPosition == 0 || p < endPosition ? dividerHeight : 1;
            final int bottom = top + line;
            final int left = parent.getPaddingLeft() + paddingLeft;

            if (p < endPosition) {
                topLine.setBounds(left, top, right, top + 1);
                topLine.draw(c);
                bottomLine.setBounds(left, bottom - 1, right, bottom);
                bottomLine.draw(c);
            } else {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if (p == 0 && isTopLine) {
                final int l = 0;
                final int r = parent.getWidth();
                final int t = child.getTop() + params.topMargin - ViewUtils.dip2px(8);
                final int b = child.getTop() + params.topMargin;
                lineDrawable.setBounds(l, t, r, b);
                lineDrawable.draw(c);
            }
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {

        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, isTopLine && itemPosition == 0 ? ViewUtils.dip2px(8) : 0, 0, endPosition == 0 || itemPosition < endPosition ? dividerHeight : 1);
        } else {
            outRect.set(0, 0, dividerHeight, 0);
        }
    }


}