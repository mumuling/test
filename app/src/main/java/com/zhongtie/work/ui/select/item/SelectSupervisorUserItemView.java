package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.CreateUserEntity;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

@BindItemData(CreateUserEntity.class)
public class SelectSupervisorUserItemView extends AbstractItemView<CreateUserEntity, SelectSupervisorUserItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_select_type;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectSupervisorUserItemView.ViewHolder vh, @NonNull CreateUserEntity data) {
        vh.mItemTitleCheck.setChecked(data.isSelect());
        vh.mItemTitleCheck.setText(data.getUserName());

        vh.mItemTitleCheck.setOnCheckedChangeListener(null);
        vh.mItemTitleCheck.setOnClickListener(v -> {
            data.post();
            data.setSelect(vh.mItemTitleCheck.isChecked());
            getCommonAdapter().notifyDataSetChanged();
        });
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private AppCompatCheckBox mItemTitleCheck;
        private AppCompatCheckBox mItemNoticeCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemTitleCheck = (AppCompatCheckBox) findViewById(R.id.item_title_check);
            mItemNoticeCheck = (AppCompatCheckBox) findViewById(R.id.item_notice_check);
            mItemNoticeCheck.setVisibility(View.GONE);
        }

    }
}