package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
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
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.ui.endorse.detail.EndorseUserItemView;

/**
 * 安全督导详情
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(CommonItemType.class)
public class DetailCommonItemView extends AbstractItemView<CommonItemType, DetailCommonItemView.ViewHolder> {
    @Override
    public int getItemViewType(int position, @NonNull CommonItemType data) {
        if (data.getTitle().contains("查阅"))
            return 1;
        return super.getItemViewType(position, data);
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == 1)
            return R.layout.item_safe_detail_view;
        return R.layout.item_safe_create_add_user;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull CommonItemType data) {
        vh.mItemUserListTitle.setText(data.getTitle());
        if (data.getTypeItemList().size() > 0) {
            vh.mItemUserListTitle.append("(" + data.getTypeItemList().size() + ")");
        }
        if (vh.mCheckExamineList.getAdapter() == null) {
            CommonAdapter adapter = new CommonAdapter(data.getTypeItemList());
            //验证人竖向
            if (data.getTitle().contains(vh.mContext.getString(R.string.CHECK_TITLE))) {
                vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext));
            } else {
                vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext, LinearLayout.HORIZONTAL, false));
            }
            adapter.register(new CreatePicItemView(false));
            //用户信息
            adapter.register(DetailUserItemView.class);
            adapter.register(EndorseUserItemView.class);
            adapter.register(DetailTeamNameItemView.class);
            vh.mCheckExamineList.setAdapter(adapter);
        } else {
            CommonAdapter adapter = (CommonAdapter) vh.mCheckExamineList.getAdapter();
            adapter.setListData(data.getTypeItemList());
            adapter.notifyDataSetChanged();
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
        private TextView mSlideLookMore;


        public ViewHolder(View itemView) {
            super(itemView);
            mSlideLookMore = (TextView) findViewById(R.id.slide_look_more);
            mItemUserListTitle = findViewById(R.id.item_user_list_title);
            mItemUserListTip = findViewById(R.id.item_user_list_tip);
            mItemUserAddImg = findViewById(R.id.item_user_add_img);
            mCheckExamineList = findViewById(R.id.check_examine_list);
            if (mItemUserListTip != null) {
                mItemUserListTip.setVisibility(View.GONE);
                mItemUserAddImg.setVisibility(View.GONE);
                mSlideLookMore.setVisibility(View.GONE);
            }
        }


    }
}
