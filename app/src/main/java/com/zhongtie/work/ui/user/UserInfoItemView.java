package com.zhongtie.work.ui.user;

import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
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
public class UserInfoItemView extends AbstractItemView<Pair<String, String>, CommonViewHolder> {
    @Override
    public int getItemViewType(int position, @NonNull Pair<String, String> data) {
        if (data.first.startsWith("密码"))
            return 1;
        return super.getItemViewType(position, data);
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == 1)
            return R.layout.item_user_info_password_view;
        return R.layout.item_user_info_view;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull Pair<String, String> data) {
        TextView title = vh.findViewById(R.id.info_title);
        TextView content = vh.findViewById(R.id.info_content);
        title.setText(data.first);
        title.append(":");
        if (content != null) {
            title.setText(data.first);
        } else {

        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }
}
