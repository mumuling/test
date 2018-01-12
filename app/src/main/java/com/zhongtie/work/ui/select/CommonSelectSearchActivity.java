package com.zhongtie.work.ui.select;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;

import static android.provider.MediaStore.Files.FileColumns.TITLE;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.FRAGMENT;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class CommonSelectSearchActivity extends BaseActivity implements TextWatcher {

    private LinearLayout mTitleLayoutView;
    private View mTitleBarTransparent;
    private LinearLayout mTitleBack;
    private TextView mTitleBackImg;
    private ImageView mSearchBtn;
    private EditText mSearch;
    private View mTitleLine;
    private FrameLayout mFragmentContent;

    private Fragment mFragment;

    private OnSearchContentListener mOnSearchContentListener;

    public static void newInstance(Context context, Class fragment, String title) {
        Intent intent = new Intent(context, CommonSelectSearchActivity.class);
        intent.putExtra(FRAGMENT, fragment.getName());
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {
        mTitleLayoutView = findViewById(R.id.title_layout_view);
        mTitleBarTransparent = findViewById(R.id.title_bar_transparent);
        mTitleBack = findViewById(R.id.title_back);
        mTitleBackImg = findViewById(R.id.title_back_img);
        mSearchBtn = findViewById(R.id.search_btn);
        mSearch = findViewById(R.id.search);
        mTitleLine = findViewById(R.id.title_line);
        mFragmentContent = findViewById(R.id.fragment_content);

        mSearch.addTextChangedListener(this);

    }

    @Override
    protected void initData() {
        mSearch.setHint(getIntent().getStringExtra(TITLE));
        try {
            mFragment = Fragments.with(this)
                    .fragment(Class.forName(getIntent().getStringExtra(FRAGMENT)))
                    .into(R.id.fragment_content);
            if (mFragment instanceof OnSearchContentListener) {
                mOnSearchContentListener = (OnSearchContentListener) mFragment;
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
