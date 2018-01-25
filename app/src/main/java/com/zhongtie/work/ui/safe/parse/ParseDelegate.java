package com.zhongtie.work.ui.safe.parse;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Chaek
 * @date: 2018/1/25
 */

public interface ParseDelegate {
    String getString(String key);

    String[] getStringArray(String key);

    int getInt(String key);

    double getDouble(String key);

    boolean getBoolean(String key);

    float getFloat(String key);

    List<String> getListString(String key);

    Serializable getSerializable(String key);

    Parcelable getParcelable(String key);
}
