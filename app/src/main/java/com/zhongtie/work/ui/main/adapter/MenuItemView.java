package com.zhongtie.work.ui.main.adapter;

import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */
@BindItemData(Pair.class)
public class MenuItemView extends AbstractItemView<Pair<String, Integer>, CommonViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_home_page_left;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull Pair<String, Integer> data) {
        ImageView mHomePageIcon = vh.findViewById(R.id.menu_img);
        TextView mHomePageName = vh.findViewById(R.id.menu_text);
        mHomePageName.setText(data.first);
        mHomePageIcon.setImageDrawable(vh.mContext.getResources().getDrawable(data.second));
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }
}