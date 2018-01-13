package com.zhongtie.work.ui.endorse;

import android.support.annotation.NonNull;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.EndorseEntity;
import com.zhongtie.work.ui.rewardpunish.item.RewardPunishItemView;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

@BindItemData(EndorseEntity.class)
public class EndorseItemView extends AbstractItemView<EndorseEntity, RewardPunishItemView.ViewHolder> {

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_reward_punish_me_create_view;
    }

    @Override
    public void onBindViewHolder(@NonNull RewardPunishItemView.ViewHolder vh, @NonNull EndorseEntity data) {
        vh.mOrderCode.setText("提取标题轻轨10号线相关技术标准的标题描述文签认");
        vh.mOrderContent.setText("11-20 11:49");
        vh.mOrderCreateTime.setVisibility(View.GONE);
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new RewardPunishItemView.ViewHolder(view);
    }
}