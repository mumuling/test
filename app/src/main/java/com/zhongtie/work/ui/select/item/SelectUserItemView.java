package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.util.L;

/**
 * 选择用户界面
 * date:2018.1.11
 *
 * @author Chaek
 */

@BindItemData(CommonUserEntity.class)
public class SelectUserItemView extends AbstractItemView<CommonUserEntity, SelectUserItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_select_type;
    }


    private View.OnClickListener onUserNoticeListener = v -> {
        ViewHolder vh = (ViewHolder) v.getTag();
        CommonUserEntity data = (CommonUserEntity) getCommonAdapter().getListData(vh.getItemPosition());
        data.setAt(vh.mItemNoticeCheck.isChecked());
        if (vh.mItemNoticeCheck.isChecked()) {
            data.setSelect(true);
        }
        data.post();
        commonAdapter.notifyItemChanged(vh.getLayoutPosition());
    };
    private View.OnClickListener onUserSelectListener = v -> {
        ViewHolder vh = (ViewHolder) v.getTag();
        CommonUserEntity data = (CommonUserEntity) getCommonAdapter().getListData(vh.getItemPosition());
        boolean isSelect = vh.mItemTitleCheck.isChecked();
        if (!isSelect) {
            data.setAt(false);
        }
        data.setSelect(isSelect);
        commonAdapter.notifyItemChanged(vh.getLayoutPosition());
        data.post();
    };

    @Override
    public void onBindViewHolder(@NonNull SelectUserItemView.ViewHolder vh, @NonNull CommonUserEntity data) {
        L.e("----------------------", data.toString());
        vh.mItemTitleCheck.setText(data.getUserName());
        vh.mItemTitleCheck.setChecked(data.isSelect());
        vh.mItemNoticeCheck.setChecked(data.isAt());
        vh.mItemNoticeCheck.setTag(vh);
        vh.mItemNoticeCheck.setOnClickListener(onUserNoticeListener);
        vh.mItemTitleCheck.setTag(vh);
        vh.mItemTitleCheck.setOnClickListener(onUserSelectListener);
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
            mItemTitleCheck = findViewById(R.id.item_title_check);
            mItemNoticeCheck = findViewById(R.id.item_notice_check);
            mItemNoticeCheck.setVisibility(View.VISIBLE);
        }

    }
}