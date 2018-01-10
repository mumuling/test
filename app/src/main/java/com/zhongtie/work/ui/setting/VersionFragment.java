package com.zhongtie.work.ui.setting;

import android.widget.TextView;

import com.zhongtie.work.BuildConfig;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseFragment;

/**
 * Auth:Cheek
 * date:2018.1.10
 */

public class VersionFragment extends BaseFragment {
    private TextView mNowVersion;
    private TextView mVersion;
    private TextView mCopyRight;
    private TextView mCompany;

    @Override
    public int getLayoutViewId() {
        return R.layout.fragment_version;
    }

    @Override
    public void initView() {
        mNowVersion = (TextView) findViewById(R.id.now_version);
        mVersion = (TextView) findViewById(R.id.version);
        mCopyRight = (TextView) findViewById(R.id.copy_right);
        mCompany = (TextView) findViewById(R.id.company);
    }

    @Override
    protected void initData() {
        mVersion.setText("v"+BuildConfig.VERSION_NAME);
    }
}
