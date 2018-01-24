package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.widget.BaseImageView;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

@BindItemData(CommonUserEntity.class)
public class DetailUserItemView extends AbstractItemView<CommonUserEntity, DetailUserItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_user_head;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull CommonUserEntity data) {
        vh.mItemUserHead.loadImage(data.getUserPic());
        vh.mItemUserName.setTextColor(vh.mContext.getResources().getColor(R.color.text_color));
        vh.mItemUserName.setText(data.getUserName());
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private BaseImageView mItemUserHead;
        private TextView mItemUserName;


        public ViewHolder(View itemView) {
            super(itemView);
            mItemUserHead = findViewById(R.id.item_user_head);
            mItemUserName = findViewById(R.id.item_user_name);

        }

    }
}