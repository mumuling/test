package com.zhongtie.work.base.adapter;

/**
 * RecyclerView Adapter item onClick Listener
 *
 * @author Chaek
 */
public interface OnRecyclerItemClickListener<T> {
    /**
     * onClick callback
     *
     * @param t     click item data
     * @param index click item position
     */
    void onClick(T t, int index);
}
