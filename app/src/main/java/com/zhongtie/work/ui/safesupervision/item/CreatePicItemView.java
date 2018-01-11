package com.zhongtie.work.ui.safesupervision.item;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.widget.BaseImageView;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

@BindItemData(String.class)
public class CreatePicItemView extends AbstractItemView<String, CreatePicItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_select_pic;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull String data) {
        vh.mItemPic.setImageURI(Uri.parse(data));
        //点击删除按钮
        vh.mItemPicDelete.setOnClickListener(v -> {
            commonAdapter.getListData().remove(data);
            commonAdapter.notifyDataSetChanged();
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