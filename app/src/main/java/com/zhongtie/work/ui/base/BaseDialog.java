package com.zhongtie.work.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.zhongtie.work.R;


/**
 * Created by O on 2017.6.16.
 */

public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        super(context, R.style.dialog_share);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColorIfPossible(getContext().getResources().getColor(R.color.transparent));
    }

    private void setStatusBarColorIfPossible(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getWindow() != null) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(color);
            }
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        dismiss();
        return super.onTouchEvent(event);
    }
}
