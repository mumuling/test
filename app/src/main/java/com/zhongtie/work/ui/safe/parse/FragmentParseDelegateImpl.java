package com.zhongtie.work.ui.safe.parse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Chaek
 * @date: 2018/1/25
 */

class FragmentParseDelegateImpl implements ParseDelegate {

    private Bundle mBundle;

    public FragmentParseDelegateImpl(Object bundle) {
        if (bundle instanceof Fragment) {
            mBundle = ((Fragment) bundle).getArguments();
        } else if (bundle instanceof Activity) {
            mBundle = ((Activity) bundle).getIntent().getExtras();
        }
        if (mBundle == null) {
            mBundle = new Bundle();
        }
    }

    @Override
    public String getString(String key) {
        return mBundle.getString(key);
    }

    @Override
    public String[] getStringArray(String key) {
        return mBundle.getStringArray(key);
    }

    @Override
    public int getInt(String key) {
        return mBundle.getInt(key);
    }

    @Override
    public double getDouble(String key) {
        return mBundle.getDouble(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return mBundle.getBoolean(key);
    }

    @Override
    public float getFloat(String key) {
        return mBundle.getFloat(key);
    }

    @Override
    public List<String> getListString(String key) {
        return mBundle.getStringArrayList(key);
    }

    @Override
    public Serializable getSerializable(String key) {
        return mBundle.getSerializable(key);
    }

    @Override
    public Parcelable getParcelable(String key) {
        return mBundle.getParcelable(key);
    }
}
