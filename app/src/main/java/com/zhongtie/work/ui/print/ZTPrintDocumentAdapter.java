package com.zhongtie.work.ui.print;

import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.support.annotation.RequiresApi;

/**
 * Auth:Cheek
 * date:2018.1.22
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ZTPrintDocumentAdapter extends PrintDocumentAdapter {
    private OnPrintListener onPrintListener;
    private PrintDocumentAdapter printDocumentAdapter;


    public ZTPrintDocumentAdapter(PrintDocumentAdapter printDocumentAdapter, OnPrintListener onPrintListener) {
        this.onPrintListener = onPrintListener;
        this.printDocumentAdapter = printDocumentAdapter;
    }

    @Override
    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes1, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {

    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (onPrintListener != null) {
            onPrintListener.onPrintStart();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (onPrintListener != null) {
            onPrintListener.onPrintFinish();
        }
    }
}
