package com.zhongtie.work.util;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.zhongtie.work.app.App;


/**
 * Shall
 */
public class AppUtil {
    private static final String TAG = "AppUtil";
    /**
     * 获取版本名称
     *
     * @return 版本号 string
     */
    public static String appVersion() {
        String PackageName = "1.0";
        try {
            PackageName = App.getInstance().getPackageManager().getPackageInfo(App.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return PackageName;
    }

    /**
     * 获取版本号
     *
     * @return 版本号 INT
     */
    public static int appVersionCode() {
        try {
            return App.getInstance().getPackageManager().getPackageInfo(App.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

 
    public static Point getScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point out = new Point();
        display.getSize(out);
        return out;
    }
}
