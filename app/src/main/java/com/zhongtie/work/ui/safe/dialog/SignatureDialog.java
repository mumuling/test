package com.zhongtie.work.ui.safe.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.zhongtie.work.R;
import com.zhongtie.work.util.ViewUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Auth:Cheek
 * date:2018.1.12
 */

public class SignatureDialog extends Dialog {
    private LinearLayout mDialog;
    private TextView mClearSignature;
    private SignaturePad mSignaturePad;
    private TextView mUpdateDownloadCancel;
    private TextView mUpdateBackGroundDownload;

    private OnSignatureListener onSignatureListener;

    public SignatureDialog(@NonNull Context context, OnSignatureListener onSignatureListener) {
        super(context, R.style.signature_dialog);
        this.onSignatureListener = onSignatureListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signature);
        initView();
    }

    private void initView() {

        mDialog = (LinearLayout) findViewById(R.id.dialog);
        mClearSignature = (TextView) findViewById(R.id.clear_signature);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mUpdateDownloadCancel = (TextView) findViewById(R.id.update_download_cancel);
        mUpdateBackGroundDownload = (TextView) findViewById(R.id.update_back_ground_download);

        mClearSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        mUpdateDownloadCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mUpdateBackGroundDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSignatureListener != null) {
                    onSignatureListener.onSignature(saveSignaturePic());
                }
                dismiss();
            }
        });

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mDialog.getLayoutParams();
        int width = (int) (ViewUtils.getScreenWidth(getContext()) * 9);
        mDialog.setLayoutParams(linearParams);
    }

    private String saveSignaturePic() {
        File imageFile = com.zhongtie.work.util.FileUtils.getOutputImageFilePath();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            mSignaturePad.getSignatureBitmap().compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageFile.toString();
    }
}
