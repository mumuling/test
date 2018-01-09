package com.zhongtie.work.ui.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.DividerLineDrawable;

/**
 * 人才详情经历 分割线
 */
public class PerformerGridItemDecoration extends RecyclerView.ItemDecoration {
    private DividerLineDrawable mDivider;
    private int mInsets = 0;
    private int lineHeight = 0;

    public PerformerGridItemDecoration(Context context) {
        mDivider = new DividerLineDrawable();
        mDivider.setColor(context.getResources().getColor(R.color.filtrate_select_color));
        mInsets = ViewUtils.dip2px(16);
        lineHeight = ViewUtils.dip2px(10);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
//        drawHorizontal(c, parent);
    }


    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();


        }
    }


    private boolean isHeadFoot(RecyclerView recyclerView, int itemPosition) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanSize(itemPosition);
//            L.e("1212", spanCount + "<<<<<<<<<<<<<");
            if (spanCount > 1) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition == 0) {
            outRect.set(0, mInsets, 0, 0);
        }

        if (isHeadFoot(parent, itemPosition)) {
            return;
        }
        if (isFirstColum(parent, itemPosition)) {
            outRect.set(mInsets, lineHeight, 0, 0);
        } else if (isLastColum(parent, itemPosition)) {
            outRect.set(0, lineHeight, mInsets, 0);
        } else {
            outRect.set(mInsets, lineHeight, mInsets, 0);
        }
    }

    private boolean isFirstColum(RecyclerView parent, int itemPosition) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int count = 0;
        for (int i = 0; i < itemPosition; i++) {
            int n = layoutManager.getSpanSizeLookup().getSpanSize(i);
            count = count + n;
        }
        if (count % layoutManager.getSpanCount() == 0) {
            return true;
        }
        return false;
    }

    private boolean isLastColum(RecyclerView parent, int itemPosition) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int count = 0;
        for (int i = 0; i < itemPosition; i++) {
            int n = layoutManager.getSpanSizeLookup().getSpanSize(i);
            count = count + n;
        }
        if (count % layoutManager.getSpanCount() == layoutManager.getSpanCount() - 1) {
            return true;
        }
        return false;
    }
}