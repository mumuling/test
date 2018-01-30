package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.EditContentEntity;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

@BindItemData(EditContentEntity.class)
public class CommonDetailContentItemView extends AbstractItemView<EditContentEntity, CommonDetailContentItemView.ViewHolder> {

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_view_content;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull EditContentEntity data) {
        vh.mItemSafeViewTitle.setText(data.getTitle());
        vh.mItemSafeViewContent.setText(data.getContent());
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private TextView mItemSafeViewTitle;
        private TextView mItemSafeViewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemSafeViewTitle = (TextView) findViewById(R.id.item_safe_view_title);
            mItemSafeViewContent = (TextView) findViewById(R.id.item_safe_view_content);

        }

    }
}
