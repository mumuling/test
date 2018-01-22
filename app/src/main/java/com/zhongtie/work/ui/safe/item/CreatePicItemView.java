package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.ui.image.ImageReviewActivity;
import com.zhongtie.work.widget.BaseImageView;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

@BindItemData(String.class)
public class CreatePicItemView extends AbstractItemView<String, CreatePicItemView.ViewHolder> {

    public static final String HTTP = "http";

    private boolean isEdit;

    public CreatePicItemView(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_select_pic;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull String data) {
        vh.mItemPic.loadImagePic(data);
        if (isEdit) {
            vh.mItemPicDelete.setVisibility(View.VISIBLE);
        } else {
            vh.mItemPicDelete.setVisibility(View.GONE);
        }
        if (!isEdit) {
            vh.mItemPic.setOnClickListener(v -> ImageReviewActivity.start(vh.mContext, vh.getAdapterPosition(), commonAdapter.getListData()));
        }

        //点击删除按钮
        vh.mItemPicDelete.setOnClickListener(v -> {
            commonAdapter.getListData().remove(data);
            commonAdapter.notifyItemRemoved(vh.getLayoutPosition());
        });
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private BaseImageView mItemPic;
        private ImageView mItemPicDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemPic = findViewById(R.id.item_pic);
            mItemPicDelete = findViewById(R.id.item_pic_delete);
        }

    }
}