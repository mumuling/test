package com.zhongtie.work.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/10
 */

public class CommonFragmentActivity extends BaseActivity {

    public static final String FRAGMENT = "fragment";
    public static final String TITLE = "title";
    public static final int USER_SELECT_CODE = 10003;
    public static final String LIST = "list";

    public static void newInstance(Context context, Class fragment, String title) {
        Intent intent = new Intent(context, CommonFragmentActivity.class);
        intent.putExtra(FRAGMENT, fragment.getName());
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    public static void newInstance(Fragment context, Class fragment, String title, List list) {
        Intent intent = new Intent(context.getContext(), CommonFragmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT, fragment.getName());
        bundle.putString(TITLE, title);
        bundle.putSerializable(LIST, (Serializable) list);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, USER_SELECT_CODE);
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
                    .bundle(getIntent().getExtras())
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
