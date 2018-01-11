package com.zhongtie.work.ui.safesupervision.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.CreateTypeItem;
import com.zhongtie.work.data.create.EditContentEntity;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(EditContentEntity.class)
public class CreateEditContentItemView extends AbstractItemView<EditContentEntity, CreateEditContentItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_edit_content;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull EditContentEntity data) {
        vh.mEditTitle.setText(data.getTitle());
        vh.mCreateModifyContent.setHint(data.getHint());
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private TextView mEditTitle;
        private EditText mCreateModifyContent;

        public ViewHolder(View itemView) {
            super(itemView);

            mEditTitle = findViewById(R.id.edit_title);
            mCreateModifyContent = findViewById(R.id.create_modify_content);
        }

    }
}
