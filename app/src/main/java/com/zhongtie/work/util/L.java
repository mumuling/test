package com.zhongtie.work.util;

import android.util.Log;

import com.zhongtie.work.BuildConfig;


/**
 * Created by W on 2016/12/16.
 */

public class L {
    private static boolean isDebug = BuildConfig.DEBUG;

    public static void e(String TAG, String message) {
        if (isDebug)
            Log.e(TAG, message);
    }

    public static void e(String TAG, Throwable message) {
        if (isDebug)
            Log.e(TAG, "error", message);
    }

    public static void d(String TAG, String message) {
        if (isDebug)
            Log.d(TAG, message);
    }

    public static void i(String TAG, String message) {
        if (isDebug)
            Log.i(TAG, message);
    }
}
