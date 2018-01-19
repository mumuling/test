package com.zhongtie.work.ui.rewardpunish.item;

import android.support.annotation.NonNull;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.SupervisorInfoEntity;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(SupervisorInfoEntity.SafeSupervisionEntity.class)
public class RewardPunishLookItemView extends AbstractItemView<SupervisorInfoEntity.SafeSupervisionEntity, RewardPunishItemView.ViewHolder> {

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_reward_punish_me_create_view;
    }

    @Override
    public void onBindViewHolder(@NonNull RewardPunishItemView.ViewHolder vh, @NonNull SupervisorInfoEntity.SafeSupervisionEntity data) {
        vh.mOrderUserPic.setVisibility(View.VISIBLE);
        vh.mOrderUserPic.loadImage(imageUrls[0]);
        vh.mOrderCode.setText("张连英");
        vh.mOrderContent.setText("被处理对象：十号线供电线路项目分部十号线供...");
        vh.mOrderCreateTime.setText("11-20 11:49");
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new RewardPunishItemView.ViewHolder(view);
    }


}
