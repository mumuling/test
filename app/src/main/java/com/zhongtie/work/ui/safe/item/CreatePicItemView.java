package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.util.image.ImageLoader;
import com.zhongtie.work.util.image.ImageLoaderUtil;
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
        ImageLoader imageLoader = new ImageLoader.Builder().url(data.startsWith(HTTP) ? data : "file://" + data).placeHolder(R.drawable.ic_def).
                imgView(vh.mItemPic).size(30, 30).build();
        ImageLoaderUtil.getInstance().loadImage(vh.mContext, imageLoader);
        if (isEdit) {
            vh.mItemPicDelete.setVisibility(View.VISIBLE);
        } else {
            vh.mItemPicDelete.setVisibility(View.GONE);
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