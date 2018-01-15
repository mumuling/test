package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.util.L;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

@BindItemData(CreateUserEntity.class)
public class SelectTeamUserItemView extends AbstractItemView<CreateUserEntity, SelectTeamUserItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_select_type;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTeamUserItemView.ViewHolder vh, @NonNull CreateUserEntity data) {
        vh.mItemTitleCheck.setText(data.getUserName());

        L.e("----------------------", data.toString());
        vh.mItemTitleCheck.setChecked(data.isSelect());
        vh.mItemNoticeCheck.setChecked(data.isAt());

        vh.mItemNoticeCheck.setOnClickListener(v -> {
            data.setAt(vh.mItemNoticeCheck.isChecked());
            if (vh.mItemNoticeCheck.isChecked()) {
                data.setSelect(true);
            }
            getCommonAdapter().notifyDataSetChanged();
            data.post();
        });
        vh.mItemTitleCheck.setOnCheckedChangeListener(null);
        vh.mItemTitleCheck.setOnClickListener(v -> {
            boolean isSelect = vh.mItemTitleCheck.isChecked();
            if (!isSelect) {
                data.setAt(false);
            }
            data.setSelect(isSelect);
            getCommonAdapter().notifyItemChanged(vh.getLayoutPosition());
            data.post();
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
            mItemNoticeCheck.setVisibility(View.VISIBLE);
        }

    }
}