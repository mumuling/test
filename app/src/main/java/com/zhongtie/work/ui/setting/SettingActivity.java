package com.zhongtie.work.ui.setting;

import android.content.Context;
import android.content.Intent;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;

/**
 * Auth: Chaek
 * Date: 2018/1/10
 */

public class SettingActivity extends BaseActivity {

    public static final String FRAGMENT = "fragment";
    public static final String TITLE = "title";

    public static void newInstance(Context context, Class fragment, String title) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra(FRAGMENT, fragment.getName());
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.common_activity;
    }

    @Override
    protected void initView() {
        setTitle(getIntent().getStringExtra(TITLE));
        try {
            Fragments.with(this)
                    .fragment(Class.forName(getIntent().getStringExtra(FRAGMENT)))
                    .into(R.id.fragment_content);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showToast(getString(R.string.open_fail));
        }
    }

    @Override
    protected void initData() {

    }
}
