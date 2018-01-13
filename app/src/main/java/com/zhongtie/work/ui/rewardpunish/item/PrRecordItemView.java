package com.zhongtie.work.ui.rewardpunish.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.RPRecordEntity;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.BaseImageView;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreate2Fragment.imageUrls;

/**
 * 安全监察的操作 记录
 * Auth: Chaek
 * Date: 2018/1/11
 */

@BindItemData(RPRecordEntity.class)
public class PrRecordItemView extends AbstractItemView<RPRecordEntity, PrRecordItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_rp_record_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull RPRecordEntity data) {
        vh.mSafeOrderReplyHead.loadImage(imageUrls[0]);
        vh.mSafeOrderName.setText("测试");
        if (data.isEdit()) {
            vh.mSafeOrderReplySign.setVisibility(View.GONE);
            vh.mSafeOrderReplyTime.setVisibility(View.GONE);
            vh.mRecordContent.setVisibility(View.GONE);
        } else {
            vh.mRecordContent.setText("项目安全措施也技术指标没有达标请完善全部指标" +
                    "项目安全措施也技术指标没有达标请完善全部指标");
            vh.mSafeOrderReplyTime.setText("2017-11-20 16:58");
            vh.mSafeOrderReplySign.loadImage(imageUrls[1]);
            vh.mSafeOrderReplySign.setVisibility(View.VISIBLE);
            vh.mSafeOrderReplyTime.setVisibility(View.VISIBLE);
            vh.mRecordContent.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private BaseImageView mSafeOrderReplyHead;
        private BaseImageView mSafeOrderReplySign;
        private TextView mSafeOrderName;
        private TextView mSafeOrderReplyTime;
        private TextView mRecordContent;


        public ViewHolder(View itemView) {
            super(itemView);
            mSafeOrderReplyHead = (BaseImageView) findViewById(R.id.safe_order_reply_head);
            mSafeOrderReplySign = (BaseImageView) findViewById(R.id.safe_order_reply_sign);
            mSafeOrderReplyHead.getLayoutParams().width = ViewUtils.dip2px(50);
            mSafeOrderReplyHead.getLayoutParams().height = ViewUtils.dip2px(50);
            mSafeOrderName = (TextView) findViewById(R.id.safe_order_name);
            mSafeOrderName.setTextSize(14f);
            mSafeOrderReplyTime = (TextView) findViewById(R.id.safe_order_reply_time);
            mSafeOrderReplyTime.setTextColor(mContext.getResources().getColor(R.color.text_color));
            mRecordContent = (TextView) findViewById(R.id.record_content);

        }

    }
}