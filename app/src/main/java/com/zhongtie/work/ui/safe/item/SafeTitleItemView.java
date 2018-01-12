package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;

/**
 * 详情展示标题 例如：回复（1）
 * Auth: Chaek
 * Date: 2018/1/12
 */

@BindItemData(String.class)
public class SafeTitleItemView extends AbstractItemView<String, CommonViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_title;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull String data) {
        TextView title = vh.findViewById(R.id.item_user_list_title);
        title.setText(data);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }
}
