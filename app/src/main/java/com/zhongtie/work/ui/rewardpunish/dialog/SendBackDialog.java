package com.zhongtie.work.ui.rewardpunish.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseDialog;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.ToastUtil;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class SendBackDialog extends BaseDialog implements View.OnClickListener {
    private EditText mSendBackReason;
    private TextView mCancel;
    private TextView mConfirm;
    private OnSendBackListener onSendBackListener;

    public SendBackDialog(@NonNull Context context, OnSendBackListener onSendBackListener) {
        super(context);
        this.onSendBackListener = onSendBackListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Window w = getWindow();
            assert w != null;
            w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.dialog_send_back);
        initView();
    }

    private void initView() {
        mSendBackReason = (EditText) findViewById(R.id.send_back_reason);
        mCancel = (TextView) findViewById(R.id.cancel);
        mConfirm = (TextView) findViewById(R.id.confirm);
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (onSendBackListener != null) {
            switch (view.getId()) {
                case R.id.cancel:
                    dismiss();
                    onSendBackListener.onSendBackCancel();
                    break;
                case R.id.confirm:
                    String reason = mSendBackReason.getText().toString();
                    if (TextUtil.isEmpty(reason)) {
                        ToastUtil.showToast(R.string.send_back_reason_hint);
                        return;
                    }
                    onSendBackListener.onSendBackSuccess(reason);
                    break;
                default:
            }

        } else {
            dismiss();
        }

    }

    public interface OnSendBackListener {
        void onSendBackCancel();

        void onSendBackSuccess(String reason);
    }
}
