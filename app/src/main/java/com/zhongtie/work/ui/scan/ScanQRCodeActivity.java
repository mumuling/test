package com.zhongtie.work.ui.scan;

import android.content.Context;
import android.content.Intent;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.scan.info.ScanQRCodeInfoFragment;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class ScanQRCodeActivity extends BaseActivity implements OnNextFragmentListener {

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, ScanQRCodeActivity.class));
    }

    private ScanQRCodeFragment qrCodeFragment;

    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_scan_qrcode;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.scan_qr_title));
    }

    @Override
    protected void initData() {
        qrCodeFragment = (ScanQRCodeFragment) Fragments.with(this)
                .fragment(ScanQRCodeFragment.class)
                .into(R.id.content_view);
    }

    @Override
    public void onNextUserInfo(String userInfo) {
        Fragments.with(this)
                .fragment(ScanQRCodeInfoFragment.class)
                .addToBackStack()
                .into(R.id.content_view);
    }

    @Override
    public void onResumeCamera() {
        if (qrCodeFragment != null) {
            qrCodeFragment.onResume();
        }
    }
}
