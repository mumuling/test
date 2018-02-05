package com.zhongtie.work.util;

import android.support.annotation.DrawableRes;

import com.zhongtie.work.R;

/**
 * @author: Chaek
 * @date: 2018/2/5
 */

public class ZTUtil {

    @DrawableRes
    public static int getFileTypeImage(String filePath) {
        if (filePath.endsWith("xls") || filePath.endsWith("xlsx")) {
            return R.drawable.ic_file_excel;
        } else if (filePath.endsWith("doc") || filePath.endsWith("docx")) {
            return R.drawable.ic_file_word;
        } else if (filePath.endsWith("ppt") || filePath.endsWith("pptx")) {
            return R.drawable.ic_file_power;
        } else if (filePath.endsWith("pdf")) {
            return R.drawable.ic_file_pdf;
        } else if (filePath.endsWith("txt")) {
            return R.drawable.ic_file_unknow;
        } else {
            return R.drawable.ic_file_unknow;
        }
    }
}
