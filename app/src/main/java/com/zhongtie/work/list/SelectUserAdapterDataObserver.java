package com.zhongtie.work.list;

import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.base.adapter.CommonViewHolder;

public class SelectUserAdapterDataObserver extends RecyclerView.AdapterDataObserver {
    private OnAdapterDataChangedListener mListener;
    private CommonViewHolder commonViewHolder;

    public SelectUserAdapterDataObserver(CommonViewHolder commonViewHolder, OnAdapterDataChangedListener listener) {
        mListener = listener;
        this.commonViewHolder = commonViewHolder;
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
            mListener.onChanged(commonViewHolder);
        }
    }

    public interface OnAdapterDataChangedListener {
        void onChanged(CommonViewHolder commonViewHolder);
    }
}