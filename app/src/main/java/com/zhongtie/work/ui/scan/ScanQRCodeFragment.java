package com.zhongtie.work.ui.scan;

import android.content.Context;
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

    private OnNextFragmentListener onNextFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNextFragmentListener) {
            onNextFragmentListener = (OnNextFragmentListener) context;
        }
    }

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
        mScannerView.setBorderColor(Util.getColor(R.color.white));
        mScannerView.setAspectTolerance(0.5f);
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
        if (onNextFragmentListener != null) {
            onNextFragmentListener.onNextUserInfo(result.getText());
        }
    }
}