package com.zhongtie.work.ui.file.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.UserFileEntity;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

@BindItemData(UserFileEntity.class)
public class UserFileItemView extends AbstractItemView<UserFileEntity, UserFileItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_file_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull UserFileEntity data) {
        vh.mFileName.setText("文件" + vh.getItemPosition());
        vh.mFolderFileContent.setText("100k\t 2012-12-12 12:12");
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private ImageView mFileImg;
        private ImageView mFileSelect;
        private TextView mFileName;
        private TextView mFolderFileContent;


        public ViewHolder(View itemView) {
            super(itemView);
            mFileImg = (ImageView) findViewById(R.id.file_img);
            mFileSelect = (ImageView) findViewById(R.id.file_select);
            mFileName = (TextView) findViewById(R.id.file_name);
            mFolderFileContent = (TextView) findViewById(R.id.folder_file_content);
        }

    }
}
