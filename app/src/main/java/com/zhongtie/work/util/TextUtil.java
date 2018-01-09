package com.zhongtie.work.util;

import android.support.annotation.Nullable;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class TextUtil {

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
