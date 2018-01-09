package com.zhongtie.work.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class FanShuPtrFrameLayout extends PtrClassicFrameLayout {
    private OnPullListener onPullListener;
    private PtrClassicKingYunHeader ptrClassicFanShuHeader;

    public void setOnPullListener(OnPullListener onPullListener) {
        this.onPullListener = onPullListener;
        if (ptrClassicFanShuHeader != null) {
            ptrClassicFanShuHeader.setOnPullListener(onPullListener);
        }
    }

    public FanShuPtrFrameLayout(Context context) {
        super(context);
        initView();
    }


    public FanShuPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FanShuPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        ptrClassicFanShuHeader = new PtrClassicKingYunHeader(getContext());
        ptrClassicFanShuHeader.setOnPullListener(onPullListener);
        setHeaderView(ptrClassicFanShuHeader);
        addPtrUIHandler(ptrClassicFanShuHeader);
    }

    public interface OnPullListener {
        void pull();

        void complete();
    }
}