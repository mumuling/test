package com.zhongtie.work.ui.endorse.create;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.ui.file.select.NormalFile;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.util.Util;

/**
 * Auth:Cheek
 * date:2018.1.13
 */


@BindItemData(NormalFile.class)
public class EndorseFileItemView extends AbstractItemView<NormalFile, EndorseFileItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_endorse_file_layout;
    }

    private boolean isEdit;

    public EndorseFileItemView(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public EndorseFileItemView() {
        isEdit = true;
    }


    @Override
    public void onBindViewHolder(@NonNull EndorseFileItemView.ViewHolder vh, @NonNull NormalFile data) {
        vh.mFileName.setText(Util.extractFileNameWithSuffix(data.getPath()));
        String fileSize = Util.getPrintSize(data.getSize());
        String time = TimeUtils.formatDateTimeAll(data.getDate() * 1000);
        if (isEdit) {
            vh.mFileDel.setVisibility(View.VISIBLE);
            vh.mFileDel.setOnClickListener(view -> {
                getCommonAdapter().getListData().remove(data);
                getCommonAdapter().notifyItemRemoved(vh.getLayoutPosition());
            });
        } else {
            vh.mFileDel.setVisibility(View.GONE);
        }
        vh.mFolderFileContent.setText(fileSize);
        if (data.getPath().endsWith("xls") || data.getPath().endsWith("xlsx")) {
            vh.mFileImg.setImageResource(R.drawable.vw_ic_excel);
        } else if (data.getPath().endsWith("doc") || data.getPath().endsWith("docx")) {
            vh.mFileImg.setImageResource(R.drawable.vw_ic_word);
        } else if (data.getPath().endsWith("ppt") || data.getPath().endsWith("pptx")) {
            vh.mFileImg.setImageResource(R.drawable.vw_ic_ppt);
        } else if (data.getPath().endsWith("pdf")) {
            vh.mFileImg.setImageResource(R.drawable.vw_ic_pdf);
        } else if (data.getPath().endsWith("txt")) {
            vh.mFileImg.setImageResource(R.drawable.vw_ic_txt);
        } else {
            vh.mFileImg.setImageResource(R.drawable.vw_ic_file);
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new EndorseFileItemView.ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private ImageView mFileImg;
        private ImageView mFileDel;
        private TextView mFileName;
        private TextView mFolderFileContent;


        public ViewHolder(View itemView) {
            super(itemView);
            mFileImg = (ImageView) findViewById(R.id.file_img);
            mFileDel = (ImageView) findViewById(R.id.file_del);
            mFileName = (TextView) findViewById(R.id.file_name);
            mFolderFileContent = (TextView) findViewById(R.id.folder_file_content);
        }

    }
}
