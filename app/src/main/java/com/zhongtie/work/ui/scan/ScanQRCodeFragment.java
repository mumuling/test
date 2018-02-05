package com.zhongtie.work.ui.scan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ResourcesUtils;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
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
        return mScannerView = new ZXingScannerView(getActivity()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new CustomViewFinderView(context);
            }
        };
    }

    public void resumeCameraPreview() {
        mScannerView.resumeCameraPreview(this);
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

    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "将二维码放入框内，即可自动扫描";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 14;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            setSquareViewFinder(true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + ResourcesUtils.dip2px(10);
                tradeMarkLeft = framingRect.left;
            } else {
                tradeMarkTop = 10;
                tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - ResourcesUtils.dip2px(10);
            }
            canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft + ResourcesUtils.dip2px(26), tradeMarkTop, PAINT);
        }
    }
}