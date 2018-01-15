package com.zhongtie.work.ui.user;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.KeyValueEntity;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

@BindItemData(KeyValueEntity.class)
public class UserInfoItemView extends AbstractItemView<KeyValueEntity<String, String>, CommonViewHolder> {
    @Override
    public int getItemViewType(int position, @NonNull KeyValueEntity<String, String> data) {
        if (data.getTitle().startsWith("密码"))
            return 1;
        return super.getItemViewType(position, data);
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == 1)
            return R.layout.item_user_info_password_view;
        return R.layout.item_user_info_view;
    }


    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull KeyValueEntity<String, String> data) {
        TextView title = vh.findViewById(R.id.info_title);
        TextView content = vh.findViewById(R.id.info_content);
        title.setText(data.getTitle());
        title.append(":");
        if (content != null) {
            content.setText(data.getContent());
        } else {

            EditText infoPassword = vh.findViewById(R.id.info_password);
            if (infoPassword.getTag() != null) {
                infoPassword.removeTextChangedListener((TextWatcher) infoPassword.getTag());
            }
            TextWatcher pwTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    data.setContent(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

            };
            infoPassword.addTextChangedListener(pwTextWatcher);
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }
}
