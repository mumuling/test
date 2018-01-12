package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.CommonItemType;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(CommonItemType.class)
public class DetailCommonItemView extends AbstractItemView<CommonItemType, DetailCommonItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_detail_view;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull CommonItemType data) {
        vh.mItemUserListTitle.setText(data.getTitle());
        if (data.getTypeItemList().size() > 0) {
            vh.mItemUserListTitle.append("\t(" + data.getTypeItemList().size() + ")");
        }
//        if (vh.mCheckExamineList.getAdapter() == null) {
            CommonAdapter adapter = new CommonAdapter(data.getTypeItemList());
            vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext, LinearLayout.HORIZONTAL, true));
            adapter.register(new CreatePicItemView(false));
            //用户信息
            adapter.register(DetailUserItemView.class);
            vh.mCheckExamineList.setAdapter(adapter);
//        } else {
//            CommonAdapter adapter = (CommonAdapter) vh.mCheckExamineList.getAdapter();
//            adapter.setListData(data.getTypeItemList());
//            vh.mCheckExamineList.getAdapter().notifyDataSetChanged();
//        }
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
        private RecyclerView mCheckExamineList;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemUserListTitle = findViewById(R.id.item_user_list_title);
            mCheckExamineList = findViewById(R.id.check_examine_list);
        }

    }
}
