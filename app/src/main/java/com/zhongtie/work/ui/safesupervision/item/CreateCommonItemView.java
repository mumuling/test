package com.zhongtie.work.ui.safesupervision.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.CommonListTypeItem;
import com.zhongtie.work.data.create.CreateTypeItem;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(CommonListTypeItem.class)
public class CreateCommonItemView extends AbstractItemView<CommonListTypeItem, CreateCommonItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_add_user;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull CommonListTypeItem data) {
        vh.mItemUserListTitle.setText(data.getTitle());
        vh.mItemUserListTip.setText(data.getHint());
        vh.mItemUserAddImg.setImageResource((data.getIcon()));
        vh.mItemUserAddImg.setVisibility(View.VISIBLE);
        if (vh.mCheckExamineList.getAdapter() == null) {
            CommonAdapter adapter = new CommonAdapter(data.getTypeItemList());
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    getCommonAdapter().notifyDataSetChanged();
                }
            });
            vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext, LinearLayout.HORIZONTAL, false));
            adapter.register(CreatePicItemView.class);
            //用户信息
            adapter.register(CreateUserItemView.class);
            vh.mCheckExamineList.setAdapter(adapter);
        }
        if (data.getTypeItemList() == null || data.getTypeItemList().isEmpty()) {
            vh.mCheckExamineList.setVisibility(View.GONE);
        } else {
            vh.mCheckExamineList.setVisibility(View.VISIBLE);
        }
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
