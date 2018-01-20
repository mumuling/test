package com.zhongtie.work.util;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class TextUtil {

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * @return 照片list
     */
    public static List<String> getPicList(String img) {
        List<String> imageList = new ArrayList<>();
        String[] t = img.split(",");
        for (String aT : t) {
            if (TextUtil.isEmpty(aT)) {
                continue;
            }
            imageList.add(aT);
        }
        return imageList;
    }
}
