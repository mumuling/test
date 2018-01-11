package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.ProjectTeamEntity;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

@BindItemData(ProjectTeamEntity.class)
public class SelectContentItemView extends AbstractItemView<ProjectTeamEntity, CommonViewHolder> {

    public static final int TITLE = 1;

    @Override
    public int getItemViewType(int position, @NonNull ProjectTeamEntity data) {
        if (data.getProjectTeamID() == 0) {
            return TITLE;
        }
        return super.getItemViewType(position, data);
    }


    @Override
    public int getLayoutId(int viewType) {
        if (viewType == TITLE) {
            return R.layout.item_company_title;
        }
        return R.layout.item_company_name;
    }


    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull ProjectTeamEntity data) {
        if (vh.getItemViewType() == TITLE) {
            TextView title = vh.findViewById(R.id.company_title);
            title.setText(data.getProjectTeamName());
        } else {
            TextView content = vh.findViewById(R.id.company_name);
            content.setText(data.getProjectTeamName());
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }


}