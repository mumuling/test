package com.zhongtie.work.ui.select;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;

import java.io.Serializable;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.TITLE;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.FRAGMENT;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.USER_SELECT_CODE;

/**
 * 通用搜索界面
 * Date: 2018/1/11
 *
 * @author Chaek
 */
public class CommonSelectSearchActivity extends BaseActivity implements TextWatcher {

    public final static String SEARCH_HINT = "search_hint";
    private EditText mSearch;
    private OnSearchContentListener mOnSearchContentListener;

    public static void newInstance(Context context, Class fragment, String title) {
        Intent intent = new Intent(context, CommonSelectSearchActivity.class);
        intent.putExtra(FRAGMENT, fragment.getName());
        intent.putExtra(TITLE, title);
        intent.putExtra(SEARCH_HINT, title);
        context.startActivity(intent);
    }

    public static void newInstance(Fragment context, Class fragment, String hint, List list) {
        Intent intent = new Intent(context.getContext(), CommonSelectSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT, fragment.getName());
        bundle.putString(TITLE, hint);
        bundle.putString(SEARCH_HINT, hint);
        bundle.putSerializable(LIST, (Serializable) list);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, USER_SELECT_CODE);
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
        Intent intent = new Intent(context.getContext(), CommonSelectSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT, fragment.getName());
        bundle.putString(TITLE, title);
        bundle.putAll(valueBundle);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, USER_SELECT_CODE);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {
        LinearLayout titleBack = findViewById(R.id.title_back);
        mSearch = findViewById(R.id.search);
        mSearch.addTextChangedListener(this);
        titleBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {
        mSearch.setHint(getIntent().getStringExtra(SEARCH_HINT));
        try {
            Fragment fragment = Fragments.with(this)
                    .fragment(Class.forName(getIntent().getStringExtra(FRAGMENT)))
                    .bundle(getIntent().getExtras())
                    .into(R.id.fragment_content);
            if (fragment instanceof OnSearchContentListener) {
                mOnSearchContentListener = (OnSearchContentListener) fragment;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showToast(getString(R.string.open_fail));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mOnSearchContentListener != null) {
            mOnSearchContentListener.onSearch(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
