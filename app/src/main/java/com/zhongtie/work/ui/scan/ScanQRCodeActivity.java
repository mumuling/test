package com.zhongtie.work.ui.scan;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class ScanQRCodeActivity extends BaseActivity {
    private FrameLayout mContentView;
    private LinearLayout mLvHomeMenu;


    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_scan_qrcode;
    }

    @Override
    protected void initView() {
        mContentView = findViewById(R.id.content_view);
        mLvHomeMenu = findViewById(R.id.lv_home_menu);
        mLvHomeMenu.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {
        Fragments.with(this)
                .fragment(ScanQRCodeFragment.class)
                .into(R.id.content_view);
    }
}
