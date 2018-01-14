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
        if (data.getPath().endsWith("xls") || data.getPath().endsWith("xlsx")) {
            vh.mFileImg.setImageResource(R.drawable.ic_file_excel);
        } else if (data.getPath().endsWith("doc") || data.getPath().endsWith("docx")) {
            vh.mFileImg.setImageResource(R.drawable.ic_file_word);
        } else if (data.getPath().endsWith("ppt") || data.getPath().endsWith("pptx")) {
            vh.mFileImg.setImageResource(R.drawable.ic_file_power);
        } else if (data.getPath().endsWith("pdf")) {
            vh.mFileImg.setImageResource(R.drawable.ic_file_pdf);
        } else if (data.getPath().endsWith("txt")) {
            vh.mFileImg.setImageResource(R.drawable.ic_file_unknow);
        } else {
            vh.mFileImg.setImageResource(R.drawable.ic_file_unknow);
        }
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
