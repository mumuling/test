package com.zhongtie.work.ui.scan;

import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.util.Util;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class ScanQRCodeFragment extends BaseFragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected View createFragmentView(ViewGroup container) {
        return mScannerView = new ZXingScannerView(getActivity());
    }

    @Override
    public int getLayoutViewId() {
        return 0;
    }

    @Override
    public void initView() {
        mScannerView.setBorderColor(Util.getColor(R.color.app_color));
//        mScannerView.setBorderStrokeWidth(dip2px(80));
//        mScannerView.setBorderLineLength(dip2px(80));
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void handleResult(Result result) {

    }
}