package com.zhongtie.work.ui.endorse.detail;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.BaseImageView;

/**
 * 签认人
 */
@BindItemData(value = {EndorseUserEntity.class})
public class EndorseUserItemView extends AbstractItemView<EndorseUserEntity, EndorseUserItemView.ViewHolder> {

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_approve_view;
    }

    private SpannableString getSignStatusText() {
        SpannableString spStr = new SpannableString("已签认");
        spStr.setSpan(new ForegroundColorSpan(ViewUtils.getColor(R.color.app_color)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spStr;
    }

    private SpannableString getSignNoStatusText() {
        SpannableString spStr = new SpannableString("未签认");
        spStr.setSpan(new ForegroundColorSpan(ViewUtils.getColor(R.color.text_color3)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spStr;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull EndorseUserEntity data) {
        vh.safeOrderName.setText(data.getUsername());
        vh.safeOrderReplyHead.loadImage(data.getPic());

        if (TextUtil.isEmpty(data.url)) {
            vh.safeOrderName.append("：\t");
            vh.safeOrderName.append(getSignNoStatusText());
            vh.safeOrderReplySign.setVisibility(View.GONE);
        } else {
            vh.safeOrderName.append("：\t");
            vh.safeOrderName.append(getSignStatusText());
            vh.safeOrderReplyTime.setText(TimeUtils.formatEventTime(data.getTime()));
            vh.safeOrderReplySign.setVisibility(View.VISIBLE);
            vh.safeOrderReplySign.loadImageSign(data.url);
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }


    public class ViewHolder extends CommonViewHolder {
        private BaseImageView safeOrderReplyHead;
        private BaseImageView safeOrderReplySign;
        private TextView safeOrderName;
        private TextView safeOrderReplyTime;

        public ViewHolder(View itemView) {
            super(itemView);
            View v = new View(itemView.getContext());
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            v.setBackgroundColor(mContext.getResources().getColor(R.color.line2));
            ((ViewGroup) itemView).addView(v, 0);

            safeOrderReplyHead = (BaseImageView) findViewById(R.id.safe_order_reply_head);
            safeOrderReplySign = (BaseImageView) findViewById(R.id.safe_order_reply_sign);
            safeOrderName = (TextView) findViewById(R.id.safe_order_name);
            safeOrderName.setTextSize(14);
            safeOrderReplyTime = (TextView) findViewById(R.id.safe_order_reply_time);
            itemView.setPadding(0, 0, 0, 0);
        }
    }
}