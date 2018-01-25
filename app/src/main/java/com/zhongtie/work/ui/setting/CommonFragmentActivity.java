package com.zhongtie.work.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.list.OnEventPrintListener;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.print.PrintEventActivity;

import java.io.Serializable;
import java.util.List;

/**
 * 基本的Fragment打开界面 调用参数参考{@link #newInstance(Context, Class, String)}
 * Date: 2018/1/10
 *
 * @author Chaek
 */

public class CommonFragmentActivity extends BaseActivity implements OnEventPrintListener {

    public static final String FRAGMENT = "fragment";
    public static final String TITLE = "title";
    public static final int USER_SELECT_CODE = 10003;
    public static final String LIST = "list";

    private int mEventId;
    private int mPrintType;

    public static void newInstance(Context context, Class fragment, String title) {
        Intent intent = new Intent(context, CommonFragmentActivity.class);
        intent.putExtra(FRAGMENT, fragment.getName());
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    public static void newInstance(Context context, Class fragment, @StringRes int title) {
        newInstance(context, fragment, context.getString(title));
    }

    public static void newInstance(Fragment context, Class fragment, String title, List list) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST, (Serializable) list);
        newInstance(context, fragment, title, bundle);
    }


    @Override
    protected void onClickRight() {
        super.onClickRight();
        if (mEventId > 0) {
            PrintEventActivity.start(this, mPrintType,mEventId);
        }
    }

    @Override
    public void onShowPrint(int type, int eventId) {
        this.mPrintType = type;
        this.mEventId = eventId;
        mMenuTitle.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_file_print);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mMenuTitle.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public void onHidePrint() {
        mMenuTitle.setVisibility(View.GONE);
    }

    /**
     * 参数
     *
     * @param context     context
     * @param fragment    fragment
     * @param title       标题
     * @param valueBundle 参数集合
     */
    public static void newInstance(Fragment context, Class fragment, String title, Bundle valueBundle) {
        Intent intent = new Intent(context.getContext(), CommonFragmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT, fragment.getName());
        bundle.putString(TITLE, title);
        bundle.putAll(valueBundle);
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
