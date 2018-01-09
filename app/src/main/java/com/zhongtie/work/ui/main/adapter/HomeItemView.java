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
 * Date: 2017/12/22
 */

@BindItemData(Pair.class)
public class HomeItemView extends AbstractItemView<Pair<String, Integer>, CommonViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_home_page_view;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull Pair<String, Integer> data) {
        ImageView mHomePageIcon = vh.findViewById(R.id.home_page_icon);
        TextView mHomePageName = vh.findViewById(R.id.home_page_name);
        mHomePageName.setText(data.first);
        mHomePageIcon.setImageDrawable(vh.mContext.getResources().getDrawable(data.second));
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }
}
