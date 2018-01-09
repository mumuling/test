package com.zhongtie.work.util;

import android.support.v7.widget.ListPopupWindow;
import android.view.View;

import com.zhongtie.work.list.OnListPopupListener;
import com.zhongtie.work.ui.main.adapter.PopupWindowAdapter;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class ListPopupWindowUtil {

    /**
     * 安全弹窗选择
     */
    public static void showListPopupWindow(View v, int gravity, String[] list, OnListPopupListener onListPopupListener) {
        ListPopupWindow listPopupWindow = new ListPopupWindow(v.getContext());
        listPopupWindow.setAdapter(new PopupWindowAdapter(list));
        listPopupWindow.setAnchorView(v);
        listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            if (onListPopupListener != null) {
                onListPopupListener.onItemClick(list[position], position);
            }
            listPopupWindow.dismiss();
        });
        listPopupWindow.setDropDownGravity(gravity);
        listPopupWindow.show();
    }
}
