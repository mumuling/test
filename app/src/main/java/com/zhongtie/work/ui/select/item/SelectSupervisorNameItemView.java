package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.SelectSafeEventEntity;
import com.zhongtie.work.data.SupervisorInfoEntity;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

@BindItemData(SelectSafeEventEntity.class)
public class SelectSupervisorNameItemView extends AbstractItemView<SelectSafeEventEntity, SelectSupervisorNameItemView.ViewHolder> {

    public static final int TITLE = 1;

    @Override
    public int getItemViewType(int position, @NonNull SelectSafeEventEntity data) {
        if (data.getId() == 0)
            return TITLE;
        return super.getItemViewType(position, data);
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == TITLE)
            return R.layout.item_select_safe_event_title;
        return R.layout.item_select_supervisor_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull SelectSafeEventEntity data) {
        if (vh.getItemViewType() == TITLE) {
            vh.mSupervisorName.setText(data.getTime());
        } else {
            vh.mSupervisorName.setText(data.getUnit());
            vh.mSupervisorAddress.setText(data.getLocal());
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private TextView mSupervisorName;
        private TextView mSupervisorAddress;
        private TextView mCompanyTitle;

        public ViewHolder(View itemView) {
            super(itemView);


            mSupervisorName = findViewById(R.id.supervisor_name);
            mSupervisorAddress = findViewById(R.id.supervisor_address);

        }
    }
}
