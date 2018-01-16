package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.db.SafeSupervisionEntity;
import com.zhongtie.work.widget.BaseImageView;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(SafeSupervisionEntity.class)
public class SafeSupervisionItemView extends AbstractItemView<SafeSupervisionEntity, SafeSupervisionItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_supervision_order;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull SafeSupervisionEntity data) {
        vh.mSafeOrderHead.loadImage(imageUrls[vh.getItemPosition() % imageUrls.length]);
        vh.mSafeOrderName.setText("测试" + vh.getItemPosition());
        vh.mSafeOrderState.setText("已阅");
        vh.mSafeOrderContent.setText("2018-01-12");
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private BaseImageView mSafeOrderHead;
        private ImageView mArrow;
        private TextView mSafeOrderState;
        private TextView mSafeOrderName;
        private TextView mSafeOrderContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mSafeOrderHead = (BaseImageView) findViewById(R.id.safe_order_head);
            mArrow = (ImageView) findViewById(R.id.arrow);
            mSafeOrderState = (TextView) findViewById(R.id.safe_order_state);
            mSafeOrderName = (TextView) findViewById(R.id.safe_order_name);
            mSafeOrderContent = (TextView) findViewById(R.id.safe_order_content);
        }

    }
}
