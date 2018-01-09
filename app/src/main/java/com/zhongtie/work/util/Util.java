package com.zhongtie.work.util;

import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.zhongtie.work.app.App;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class Util {

    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(App.getInstance(), colorId);
    }
}
