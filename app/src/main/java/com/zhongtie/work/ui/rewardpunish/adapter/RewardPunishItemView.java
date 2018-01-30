package com.zhongtie.work.ui.rewardpunish.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.RewardPunishEntity;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.widget.BaseImageView;

/**
 * 安全处罚信息 我创建的
 *
 * @author Chaek
 * @date: 2018/1/11
 */
@BindItemData(RewardPunishEntity.class)
public class RewardPunishItemView extends AbstractItemView<RewardPunishEntity, RewardPunishItemView.ViewHolder> {

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_reward_punish_me_create_view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull RewardPunishEntity data) {
        vh.mOrderCode.setText(data.getPunishCode());
        vh.mOrderContent.setText(data.getPunishCompany());
        vh.mOrderCreateTime.setText(data.getCreateTime());
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        public BaseImageView mOrderUserPic;
        public TextView mOrderCode;
        public TextView mOrderContent;
        public TextView mOrderCreateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mOrderUserPic = findViewById(R.id.order_user_pic);
            mOrderUserPic.setVisibility(View.GONE);
            mOrderCode = findViewById(R.id.order_code);
            mOrderContent = findViewById(R.id.order_content);
            mOrderCreateTime = findViewById(R.id.order_create_time);
        }
    }
}
