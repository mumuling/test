package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.TeamNameEntity;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

@BindItemData(TeamNameEntity.class)
public class TeamNameItemView extends AbstractItemView<TeamNameEntity, TeamNameItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_select_type;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamNameItemView.ViewHolder vh, @NonNull TeamNameEntity data) {
        vh.mItemTitleCheck.setText("测试" + vh.getItemPosition());
//        vh.mItemTitleCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                data.setSelect(vh.mItemTitleCheck.isChecked());
//                data.setSelect(true);
//                data.post();
//            }
//        });
//        vh.mItemTitleCheck.setOnClickListener((compoundButton, b) -> {
//
//        });
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
        }

    }
}