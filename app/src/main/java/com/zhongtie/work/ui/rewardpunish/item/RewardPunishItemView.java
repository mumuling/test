package com.zhongtie.work.ui.rewardpunish.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.model.SafeSupervisionEntity;
import com.zhongtie.work.widget.BaseImageView;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(SafeSupervisionEntity.class)
public class RewardPunishItemView extends AbstractItemView<SafeSupervisionEntity, RewardPunishItemView.ViewHolder> {


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_reward_punish_me_create_view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull SafeSupervisionEntity data) {
        vh.mOrderCode.setText("编号：重庆-2017-61");
        vh.mOrderContent.setText("被处理对象：十号线供电线路项目分部十号线供...");
        vh.mOrderCreateTime.setText("11-20 11:49");
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
            mOrderUserPic = (BaseImageView) findViewById(R.id.order_user_pic);
            mOrderUserPic.setVisibility(View.GONE);
            mOrderCode = (TextView) findViewById(R.id.order_code);
            mOrderContent = (TextView) findViewById(R.id.order_content);
            mOrderCreateTime = (TextView) findViewById(R.id.order_create_time);
        }
    }
}
