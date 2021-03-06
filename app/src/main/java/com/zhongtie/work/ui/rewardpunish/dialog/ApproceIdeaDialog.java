package com.zhongtie.work.ui.rewardpunish.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseDialog;
import com.zhongtie.work.ui.rewardpunish.detail.OnApproveListener;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class ApproceIdeaDialog extends BaseDialog implements View.OnClickListener {

    private OnApproveListener onApproveListener;

    public ApproceIdeaDialog(@NonNull Context context, OnApproveListener onApproveListener) {
        super(context);
        this.onApproveListener = onApproveListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_approve_idea);
        initView();
    }

    private void initView() {
        TextView sendBack = findViewById(R.id.send_back);
        TextView consent = findViewById(R.id.consent);
        sendBack.setOnClickListener(this);
        consent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (onApproveListener != null) {
            switch (view.getId()) {
                case R.id.send_back:
                    onApproveListener.onSendBack();
                    break;
                case R.id.consent:
                    onApproveListener.onConsent();
                    break;
                default:
            }
        }
    }
}
