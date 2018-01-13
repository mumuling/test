package com.zhongtie.work.ui.file.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.UserFolderEntity;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

@BindItemData(UserFolderEntity.class)
public class UserFolderItemView extends AbstractItemView<UserFolderEntity, UserFolderItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_file_folder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull UserFolderEntity data) {
        vh.mFolderName.setText("文件夹" + vh.getItemPosition());
        vh.mFolderFileContent.setText("文件夹" + data.getFolderList().size() + "个,文件" + data.getFolderList().size() + "个");
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private ImageView mFolderImg;
        private ImageView mFolderArrow;
        private TextView mFolderName;
        private TextView mFolderFileContent;

        public ViewHolder(View itemView) {
            super(itemView);


            mFolderImg = (ImageView) findViewById(R.id.folder_img);
            mFolderArrow = (ImageView) findViewById(R.id.folder_arrow);
            mFolderName = (TextView) findViewById(R.id.folder_name);
            mFolderFileContent = (TextView) findViewById(R.id.folder_file_content);

        }

    }
}
