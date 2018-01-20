package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.widget.BaseImageView;

/**
 * 安全督导item
 * Date: 2018/1/11
 *
 * @author Chaek
 */
@BindItemData(SupervisorInfoEntity.SafeSupervisionEntity.class)
public class SafeSupervisionItemView extends AbstractItemView<SupervisorInfoEntity.SafeSupervisionEntity, SafeSupervisionItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_supervision_order;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull SupervisorInfoEntity.SafeSupervisionEntity data) {
        vh.mSafeOrderHead.loadImage(data.getUserPicture());
        vh.mSafeOrderName.setText(data.getUserName());
        vh.mSafeOrderState.setText(data.getState());
        vh.mSafeOrderContent.setText(data.getCompanyName());


        int statusColor = vh.mContext.getResources().getColor(R.color.status_read);
        switch (data.getState()) {
            case "新进展":
                statusColor = vh.mContext.getResources().getColor(R.color.status_new_next);
                break;
            case "@我":
                statusColor = vh.mContext.getResources().getColor(R.color.status_at);
                break;
            case "完结":
                statusColor = vh.mContext.getResources().getColor(R.color.status_over);
                break;
            case "已阅":
                statusColor = vh.mContext.getResources().getColor(R.color.status_read);
                break;
        }
        vh.mSafeOrderState.setTextColor(statusColor);

    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private BaseImageView mSafeOrderHead;
        private TextView mSafeOrderState;
        private TextView mSafeOrderName;
        private TextView mSafeOrderContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mSafeOrderHead = findViewById(R.id.safe_order_head);
            mSafeOrderState = findViewById(R.id.safe_order_state);
            mSafeOrderName = findViewById(R.id.safe_order_name);
            mSafeOrderContent = findViewById(R.id.safe_order_content);
        }

    }
}
