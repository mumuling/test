package com.zhongtie.work.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.util.ViewUtils;


public class LoadingDialog extends Dialog {
    private Context context;
    private String text;
    public TextView content;

    public LoadingDialog(Context context) {
        super(context, R.style.load_dialog);
        this.context = context;
    }

    public LoadingDialog(Context context, String text) {
        super(context, R.style.load_dialog);
        this.context = context;
        this.text = text;
    }

    public void setText(String text) {
        this.text = (text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        LinearLayout widthLineLayout = findViewById(R.id.loading_width_layout);
        content = findViewById(R.id.toas_text);
        content.setText(text);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) widthLineLayout.getLayoutParams();
        linearParams.width = (int) (ViewUtils.getScreenWidth(context) * 0.8);
        widthLineLayout.setLayoutParams(linearParams);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (event.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
            dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
