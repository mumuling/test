package com.zhongtie.work.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;


import com.zhongtie.work.R;
import com.zhongtie.work.util.ViewUtils;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class PtrClassicKingYunHeader extends FrameLayout implements PtrUIHandler {

    private View mProgressBar;
    private KingYunUpdateView view;
    private FanShuPtrFrameLayout.OnPullListener onPullListener;

    public PtrClassicKingYunHeader(Context context) {
        super(context);
        initViews(null);
    }

    public PtrClassicKingYunHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public PtrClassicKingYunHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.view_king_yun_update, this, false);
        addView(header);
        view = (KingYunUpdateView) findViewById(R.id.view);
        mProgressBar = header.findViewById(R.id.ptr_classic_header_rotate_view_progressbar);
        mProgressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.update_rote));

        resetView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mProgressBar.clearAnimation();
    }

    private void resetView() {
        if (onPullListener != null) {
            onPullListener.complete();
        }
        mProgressBar.clearAnimation();
        mProgressBar.setVisibility(INVISIBLE);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mProgressBar.clearAnimation();
        mProgressBar.setVisibility(INVISIBLE);
        view.setVisibility(VISIBLE);

        if (onPullListener != null) {
            onPullListener.pull();
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        if (onPullListener != null) {
            onPullListener.pull();
        }
        view.setVisibility(GONE);
        mProgressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.update_rote));
        mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mProgressBar.setVisibility(VISIBLE);
        view.setVisibility(GONE);
        if (onPullListener != null) {
            onPullListener.complete();
        }
    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        view.setDropDown(currentPos - ViewUtils.dip2px(15));
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            }

        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                mProgressBar.setVisibility(VISIBLE);
                mProgressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.update_rote));
                view.setVisibility(GONE);
            }
        }
    }


    public void setOnPullListener(FanShuPtrFrameLayout.OnPullListener onPullListener) {
        this.onPullListener = onPullListener;
    }
}
