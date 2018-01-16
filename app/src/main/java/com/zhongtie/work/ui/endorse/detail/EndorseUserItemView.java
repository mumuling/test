package com.zhongtie.work.ui.endorse.detail;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.widget.BaseImageView;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * 签认人
 */
@BindItemData(value = {EndorseUserEntity.class})
public class EndorseUserItemView extends AbstractItemView<Object, EndorseUserItemView.ViewHolder> {

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_approve_view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull Object data) {
        vh.safeOrderReplyHead.loadImage(imageUrls[3]);
        vh.safeOrderReplySign.loadImage(imageUrls[1]);
        vh.safeOrderName.setText("测试");
        vh.safeOrderReplyTime.setText("2012-01-12");
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
            safeOrderReplyTime = (TextView) findViewById(R.id.safe_order_reply_time);
            itemView.setPadding(0, 0, 0, 0);
        }
    }
}