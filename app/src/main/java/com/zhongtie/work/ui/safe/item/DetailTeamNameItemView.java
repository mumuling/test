package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.TeamNameEntity;

/**
 * 查阅组
 * Auth:Cheek
 * date:2018.1.13
 */

@BindItemData(TeamNameEntity.class)
public class DetailTeamNameItemView extends AbstractItemView<TeamNameEntity, CommonViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_event_detail_team_name;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull TeamNameEntity data) {
        TextView mTeamName;
        mTeamName = vh.findViewById(R.id.team_name);
        mTeamName.setText(data.getTeamName());
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }
}
