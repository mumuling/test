package com.zhongtie.work.base.adapter;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Auth: Chaek
 * Date: 2017/12/12
 */

public abstract class BaseXDiffCallback<T> extends DiffUtil.Callback {

    private final List<T> oldList;
    private final List<T> newList;

    public BaseXDiffCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final T oldItem = oldList.get(oldItemPosition);
        final T newItem = newList.get(newItemPosition);
        return areItemsTheSame(oldItem, newItem);
    }

    protected abstract boolean areItemsTheSame(T oldItem, T newItem);

    protected abstract boolean areContentsTheSame(T oldItem, T newItem);

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final T oldItem = oldList.get(oldItemPosition);
        final T newItem = newList.get(newItemPosition);
        return areContentsTheSame(oldItem, newItem);
    }
}
