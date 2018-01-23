package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.list.OnCreateTextWatcher;
import com.zhongtie.work.util.TextUtil;

/**
 * 创建 输入文字提交
 * Date: 2018/1/11
 *
 * @author Chaek
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
        vh.mCreateModifyContent.setText(data.getContent());

        vh.mCreateModifyContent.setHint(data.getHint());
        if (vh.mCreateModifyContent.getTag() != null) {
            vh.mCreateModifyContent.removeTextChangedListener((TextWatcher) vh.mCreateModifyContent.getTag());
        }
        OnCreateTextWatcher onCreateTextWatcher=new OnCreateTextWatcher(vh){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                EditContentEntity contentEntity= (EditContentEntity) commonAdapter.getListData(mViewHolder.getItemPosition());
                contentEntity.setContent(s.toString());
            }
        };
        vh.mCreateModifyContent.addTextChangedListener(onCreateTextWatcher);
        vh.mCreateModifyContent.setTag(onCreateTextWatcher);
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        public TextView mEditTitle;
        public EditText mCreateModifyContent;

        public ViewHolder(View itemView) {
            super(itemView);

            mEditTitle = findViewById(R.id.edit_title);
            mCreateModifyContent = findViewById(R.id.create_modify_content);
        }

    }
}
