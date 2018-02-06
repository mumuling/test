package com.zhongtie.work.ui.rewardpunish.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.RewardPunishEntity;
import com.zhongtie.work.util.TimeUtils;

/**
 * 我可查看安全处罚信息
 *
 * @author Chaek
 * @date: 2018/1/11
 */
@BindItemData(RewardPunishEntity.class)
public class RewardPunishReadItemView extends AbstractItemView<RewardPunishEntity, RewardPunishItemView.ViewHolder> {

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_reward_punish_me_create_view;
    }

    @Override
    public void onBindViewHolder(@NonNull RewardPunishItemView.ViewHolder vh, @NonNull RewardPunishEntity data) {
        vh.mOrderUserPic.setVisibility(View.VISIBLE);
        vh.mOrderUserPic.loadImage(data.getCreateUserPic());
        vh.mOrderCode.setText(data.getPunishCode());
        vh.mOrderContent.setText(R.string.punish_user_title);
        vh.mOrderContent.append(data.getPunishCompany());
        vh.mOrderCreateTime.setText(TimeUtils.formatPunishTime(data.getCreateTime()));
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new RewardPunishItemView.ViewHolder(view);
    }

}
