package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.SelectEventTypeItem;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(SelectEventTypeItem.class)
public class CreateSelectTypeItemView extends AbstractItemView<SelectEventTypeItem, CreateSelectTypeItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_add_user;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull SelectEventTypeItem data) {
        vh.mItemUserListTitle.setText(data.getTitle());
        vh.mItemUserListTip.setVisibility(View.GONE);
        vh.mItemUserAddImg.setVisibility(View.GONE);
        vh.mCheckExamineList.setLayoutManager(new GridLayoutManager(vh.mContext,2));
        vh.mCheckExamineList.setAdapter(new CommonAdapter(data.getTypeItemList()).register(TypeCheckItemView.class));
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private TextView mItemUserListTitle;
        private TextView mItemUserListTip;
        private ImageView mItemUserAddImg;
        private RecyclerView mCheckExamineList;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemUserListTitle = findViewById(R.id.item_user_list_title);
            mItemUserListTip = findViewById(R.id.item_user_list_tip);
            mItemUserAddImg = findViewById(R.id.item_user_add_img);
            mCheckExamineList = findViewById(R.id.check_examine_list);
        }

    }
}
