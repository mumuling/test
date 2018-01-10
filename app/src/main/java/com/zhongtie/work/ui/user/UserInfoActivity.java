package com.zhongtie.work.ui.user;

import android.content.Context;
import android.content.Intent;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;

/**
 * Auth: Chaek
 * Date: 2018/1/10
 */

public class UserInfoActivity extends BaseActivity {
    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.common_activity;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.user_info_title));
        Fragments.with(this)
                .fragment(UserInfoFragment.class)
                .into(R.id.fragment_content);
    }

    @Override
    protected void initData() {

    }
}
