package com.zhongtie.work.list;

import android.text.Editable;
import android.text.TextWatcher;

import com.zhongtie.work.ui.safe.item.CreateEditContentItemView;

/**
 * Auth: Chaek
 * Date: 2018/1/17
 */

public class OnCreateTextWatcher implements TextWatcher {
    private CreateEditContentItemView.ViewHolder mViewHolder;

    public OnCreateTextWatcher(CreateEditContentItemView.ViewHolder viewHolder) {
        mViewHolder = viewHolder;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
