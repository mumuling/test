package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.SupervisorInfoEntity;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

@BindItemData(SupervisorInfoEntity.class)
public class SelectSupervisorNameItemView extends AbstractItemView<SupervisorInfoEntity, CommonViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_select_supervisor_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull SupervisorInfoEntity data) {
        TextView mSupervisorName;
        TextView mSupervisorAddress;
        mSupervisorName = (TextView) vh.findViewById(R.id.supervisor_name);
        mSupervisorAddress = (TextView) vh.findViewById(R.id.supervisor_address);
        mSupervisorName.setText("标题提取单位的名称");
        mSupervisorAddress.setText("标题提取单位的名称");
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }
}
