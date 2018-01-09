

package com.zhongtie.work.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;


/**
 * Shall
 */
public class ViewUtils {
    private static final String TAG = "ViewUtils";

    public static int getColorPrimary( ) {
        return getAttrColor(App.getInstance(), R.attr.colorPrimary);
    }

    public static int getAttrColor(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    /**
     * 自定义字体数字字体
     *
     * @param context
     * @return
     */
    public static Typeface getTextTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueLight.otf");
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context
     * @return 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * @param context
     * @return 获取屏幕高
     */
    public static int getScreenHeight(Context context) {
        return ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
    }






}
