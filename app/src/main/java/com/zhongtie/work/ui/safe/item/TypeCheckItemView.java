package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CheckBox;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.EventTypeEntity;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(EventTypeEntity.class)
public class TypeCheckItemView extends AbstractItemView<EventTypeEntity, TypeCheckItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_select_type;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull EventTypeEntity data) {
        if (data.isCheck() && !vh.mItemTitleCheck.isChecked()) {
            vh.mItemTitleCheck.setChecked(data.isCheck());
        }
        vh.mItemTitleCheck.setText(data.getCategory());
        vh.mItemTitleCheck.setOnClickListener(v -> data.setCheck(vh.mItemTitleCheck.isChecked()));
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private AppCompatCheckBox mItemTitleCheck;
        private CheckBox mItemNoticeCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemTitleCheck = findViewById(R.id.item_title_check);
            mItemNoticeCheck = findViewById(R.id.item_notice_check);

        }

    }
}
