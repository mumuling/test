package com.zhongtie.work.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringDef;

import com.zhongtie.work.app.App;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created with Android Studio.
 * Authour：Eric_chan
 * Date：2016/8/14 15:54
 * Des：
 */
public class SharePrefUtil {

    public static final String MEDIA_SP_NAME = "user_sp_name";//登录相关的SP存储文件名
    public static final String APP_SP_NAME = "app_share_pre_name";//应用相关的SP存储文件名
    public static final String OTHER_SP_NAME = "other_sp_name";//其他相关的SP存储文件名


    private volatile static SharePrefUtil mInstance;
    private volatile static SharePrefUtil mUserPre;
    private volatile static SharePrefUtil mOtherPre;

    public static SharePrefUtil getInstance() {
        if (mInstance == null) {
            synchronized (SharePrefUtil.class) {
                if (mInstance == null) {
                    mInstance = new SharePrefUtil.Builder()
                            .setFileName(SharePrefUtil.APP_SP_NAME)
                            .build();
                }
            }
        }
        return mInstance;
    }

    public static SharePrefUtil getUserPre() {
        if (mUserPre == null) {
            synchronized (SharePrefUtil.class) {
                if (mUserPre == null) {
                    mUserPre = new SharePrefUtil.Builder()
                            .setFileName(SharePrefUtil.MEDIA_SP_NAME)
                            .build();
                }
            }
        }
        return mUserPre;
    }

    public static SharePrefUtil mOtherPre() {
        if (mOtherPre == null) {
            synchronized (SharePrefUtil.class) {
                if (mOtherPre == null) {
                    mOtherPre = new SharePrefUtil.Builder()
                            .setFileName(SharePrefUtil.OTHER_SP_NAME)
                            .build();
                }
            }
        }
        return mOtherPre;
    }

    private String mName;

    private Context mContext;


    SharePrefUtil(String name) {
        this.mName = name;
    }

    SharePrefUtil(String name, Context context) {
        this.mName = name;
        this.mContext = context;
    }


    public SharedPreferences getSharedPreferences(String name) {
        return mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(mName).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(mName).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences(mName).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = getSharedPreferences(mName).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(mName).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        SharedPreferences settings = getSharedPreferences(mName);
        return settings.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        SharedPreferences settings = getSharedPreferences(mName);
        return settings.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        SharedPreferences settings = getSharedPreferences(mName);
        return settings.getLong(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        SharedPreferences settings = getSharedPreferences(mName);
        return settings.getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings = getSharedPreferences(mName);
        return settings.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = getSharedPreferences(mName).edit();
        editor.remove(key).apply();
    }

    public static final class Builder {

        @StringDef({MEDIA_SP_NAME, APP_SP_NAME, OTHER_SP_NAME})
        @Retention(RetentionPolicy.SOURCE)
        public @interface SharePrefName {
        }

        @SharePrefName
        private String mName = APP_SP_NAME;

        public Builder() {
        }


        public Builder setFileName(@SharePrefName String name) {
            this.mName = name;
            return this;
        }

        public SharePrefUtil build() {
            return new SharePrefUtil(mName, App.getInstance());
        }
    }

}
