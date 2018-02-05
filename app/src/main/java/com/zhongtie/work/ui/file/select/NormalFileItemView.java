package com.zhongtie.work.ui.file.select;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ZTUtil;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

@BindItemData(NormalFile.class)
public class NormalFileItemView extends AbstractItemView<NormalFile, NormalFileItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_file_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull NormalFile data) {
        vh.mFileName.setText(Util.extractFileNameWithSuffix(data.getPath()));
        String fileSize = Util.getPrintSize(data.getSize());
        String time = TimeUtils.formatDateTimeAll(data.getDate() * 1000);
        vh.mFolderFileContent.setText(fileSize);
        vh.mFolderFileContent.append("\t");
        vh.mFolderFileContent.append(time);
        vh.mFileImg.setImageResource(ZTUtil.getFileTypeImage(data.getPath()));
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
