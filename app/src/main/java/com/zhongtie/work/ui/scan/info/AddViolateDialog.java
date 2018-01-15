package com.zhongtie.work.ui.scan.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseDialog;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.ToastUtil;

/**
 * Auth:Cheek
 * date:2018.1.10
 */

public class AddViolateDialog extends BaseDialog implements View.OnClickListener {
    private TextView mCancel;
    private TextView mFinish;
    private EditText mViolationContent;
    private OnAddWrongListener onAddWrongListener;

    public AddViolateDialog(@NonNull Context context, OnAddWrongListener onAddWrongListener) {
        super(context);
        this.onAddWrongListener = onAddWrongListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_user_violate_view);
        initView();
    }

    private void initView() {
        mCancel = (TextView) findViewById(R.id.cancel);
        mFinish = (TextView) findViewById(R.id.finish);
        mViolationContent = (EditText) findViewById(R.id.violation_content);
        mCancel.setOnClickListener(this);
        mFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.finish:
                verifyContent();
                break;
        }
    }

    private void verifyContent() {
        String userContent = mViolationContent.getText().toString();
        if (TextUtil.isEmpty(userContent)) {
            ToastUtil.showToast("请输入内容");
            return;
        }
        if (onAddWrongListener != null) {
            onAddWrongListener.onAddWrong(userContent);
        } else {
            dismiss();
        }
    }

    public interface OnAddWrongListener {

        void onAddWrong(String content);
    }
}
