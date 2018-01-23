package com.zhongtie.work.util;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 全局Toast
 * 避免Toast弹出根本停不下来的那种
 */
public class ToastUtil {
    private static Context application;
    private static Toast timeToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        @Override
        public void run() {
            timeToast.cancel();
        }
    };

    public static void initContext(Context application) {
        ToastUtil.application = application.getApplicationContext();
    }

    public static void showToast(@StringRes int str) {
        showToast(application.getText(str).toString());
    }


    public static void showToast(String text) {
        try {
            if (TextUtils.isEmpty(text)) {
                return;
            }
            mHandler.removeCallbacks(r);
            if (null != timeToast) {
                timeToast.setText(text);
            } else {
                timeToast = Toast.makeText(application, text, Toast.LENGTH_LONG);
            }
            mHandler.postDelayed(r, 3000);
            timeToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCenterToast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast toast = Toast.makeText(application, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showCenterToast(int message) {
        showCenterToast(application.getText(message).toString());
    }
}
