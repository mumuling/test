package com.zhongtie.work.ui.rewardpunish.adapter;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.RPRecordEntity;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.BaseImageView;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * 安全监察的操作 记录
 * <p>
 * 创建的操作选择人也会进入这个item
 * Auth: Chaek
 * Date: 2018/1/11
 */

@BindItemData(RPRecordEntity.class)
public class PrRecordItemView extends AbstractItemView<RPRecordEntity, PrRecordItemView.ViewHolder> {
    public static final int PUNISH_BACK = 1;
    public static final int PUNISH_AGREE = 2;
    public static final int PUNISH_SIGN = 3;

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_rp_record_layout;
    }

    private SpannableString getBackStatusText() {
        SpannableString spStr = new SpannableString("批示：退回");
        spStr.setSpan(new ForegroundColorSpan(ViewUtils.getColor(R.color.status_at)), 3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spStr;
    }

    private SpannableString getAgreeNoStatusText() {
        SpannableString spStr = new SpannableString("批示：同意");
        spStr.setSpan(new ForegroundColorSpan(ViewUtils.getColor(R.color.state_green_color)), 3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spStr;
    }

    private SpannableString getSignNoStatusText() {
        SpannableString spStr = new SpannableString("：已签认");
        spStr.setSpan(new ForegroundColorSpan(ViewUtils.getColor(R.color.app_color)), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spStr;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull RPRecordEntity data) {
        vh.mSafeOrderName.setText(data.getUserName());
        if (data.isEdit()) {
            vh.mSafeOrderReplyHead.loadImage(data.getUserID());
            vh.mSafeOrderReplySign.setVisibility(View.GONE);
            vh.mSafeOrderReplyTime.setVisibility(View.GONE);
            vh.mRecordContent.setVisibility(View.GONE);
        } else {
            vh.mSafeOrderReplyHead.loadImage(data.getUserPic());

            if (TextUtil.isEmpty(data.getReplyContent())) {
                vh.mRecordContent.setVisibility(View.GONE);
            } else {
                vh.mRecordContent.setVisibility(View.VISIBLE);
                vh.mRecordContent.setText(data.getReplyContent());
            }
            if (TextUtil.isEmpty(data.getSignatureImg())) {
                vh.mSafeOrderReplySign.setVisibility(View.GONE);
                vh.mSafeOrderReplyTime.setVisibility(View.GONE);
            } else {
                vh.mSafeOrderReplySign.loadImageSign(data.getSignatureImg());
                vh.mSafeOrderReplySign.setVisibility(View.VISIBLE);
                vh.mSafeOrderReplyTime.setVisibility(View.VISIBLE);
                vh.mSafeOrderReplyTime.setText(TimeUtils.formatEventTime(data.getSignTime()));
                if (data.getState() == PUNISH_AGREE) {
                    vh.mSafeOrderName.append(getAgreeNoStatusText());
                } else if (data.getState() == PUNISH_BACK) {
                    vh.mSafeOrderName.append(getBackStatusText());
                } else if (data.getState() == PUNISH_SIGN) {
                    vh.mSafeOrderName.append(getSignNoStatusText());
                }
            }


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
            mSafeOrderReplyHead = findViewById(R.id.safe_order_reply_head);
            mSafeOrderReplySign = findViewById(R.id.safe_order_reply_sign);
            mSafeOrderReplyHead.getLayoutParams().width = ViewUtils.dip2px(50);
            mSafeOrderReplyHead.getLayoutParams().height = ViewUtils.dip2px(50);
            mSafeOrderName = findViewById(R.id.safe_order_name);
            mSafeOrderName.setTextSize(14f);
            mSafeOrderReplyTime = findViewById(R.id.safe_order_reply_time);
            mSafeOrderReplyTime.setTextColor(ViewUtils.getColor(R.color.text_color));
            mRecordContent = findViewById(R.id.record_content);
        }

    }
}