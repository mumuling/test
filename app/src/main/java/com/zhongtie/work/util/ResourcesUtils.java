

package com.zhongtie.work.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;


/**
 * Resources 的基本操作 在没有Context的情况下进行资源获取等
 */
public class ResourcesUtils {
    private static final String TAG = "ResourcesUtils";

    public static int getColorPrimary() {
        return getAttrColor(App.getInstance(), R.attr.colorPrimary);
    }


    /**
     * 获取style配置的眼神
     *
     * @param context 上下文
     * @param attr    attr名称
     * @return 参数
     */
    public static int getAttrColor(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }


    public static int getColor(@ColorRes int color) {
        return ContextCompat.getColor(App.getInstance(), color);
    }

    /**
     * @param drawable drawable
     * @return 根据ID获取 drawable
     */
    public static Drawable getDrawable(@DrawableRes int drawable) {
        return ContextCompat.getDrawable(App.getInstance(), drawable);
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
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     * @return 获取屏幕高
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static Drawable getTintColorDrawable(@DrawableRes int drawableID, @ColorInt int color) {
        return getTintColorDrawable(getDrawable(drawableID), color);
    }

    public static Drawable getTintColorDrawable(Drawable oldDrawable, @ColorInt int color) {
        Drawable.ConstantState state = oldDrawable.getConstantState();
        Drawable mark1 = DrawableCompat.wrap(state == null ? oldDrawable : state.newDrawable()).mutate();
        DrawableCompat.setTint(mark1, color);
        return mark1;
    }


    /**
     * @return 返回APP的Resources
     */
    public static Resources getResources() {
        return App.getInstance().getResources();
    }

    /**
     * 获取字符串 不需要传入context对象 用的是系统对象
     *
     * @param id
     * @return
     */
    public static String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return getResources().getStringArray(id);
    }
}
