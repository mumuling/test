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
import com.zhongtie.work.util.ViewUtils;

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
        vh.mFileImg.setImageResource(ViewUtils.getFileTypeImage(data.getPath()));
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
