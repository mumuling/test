package com.zhongtie.work.list;

import android.support.v7.widget.RecyclerView;

public class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
    private OnAdapterDataChangedListener mListener;

    public AdapterDataObserver(OnAdapterDataChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void onChanged() {
        super.onChanged();
        dispatchChange();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        dispatchChange();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
        dispatchChange();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        dispatchChange();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        dispatchChange();
    }

    private void dispatchChange() {
        if (mListener != null) {
            mListener.onChanged();
        }
    }

    public interface OnAdapterDataChangedListener {
        void onChanged();
    }
}