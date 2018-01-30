package com.zhongtie.work.ui.safe.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.zhongtie.work.R;
import com.zhongtie.work.enums.SignatureType;
import com.zhongtie.work.list.OnSignatureTypeListener;
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
    private OnSignatureListener onSignatureListener;

    @SignatureType
    private int signatursType;

    private OnSignatureTypeListener mOnSignatureTypeListener;

    public SignatureDialog(@NonNull Context context, @SignatureType int signatursType, OnSignatureTypeListener onSignatureTypeListener) {
        super(context, R.style.signature_dialog);
        this.signatursType = signatursType;
        mOnSignatureTypeListener = onSignatureTypeListener;
    }

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

        mDialog = findViewById(R.id.dialog);
        mClearSignature = findViewById(R.id.clear_signature);
        mSignaturePad = findViewById(R.id.signature_pad);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mSignaturePad.getLayoutParams();
        linearParams.width = (int) (ViewUtils.getScreenWidth(getContext()) * 0.82);
        linearParams.height = (int) (linearParams.width * 0.55f);
        mSignaturePad.setLayoutParams(linearParams);

        TextView cancel = findViewById(R.id.update_download_cancel);
        TextView updateBackGroundDownload = findViewById(R.id.update_back_ground_download);

        mClearSignature.setOnClickListener(view -> mSignaturePad.clear());

        cancel.setOnClickListener(view -> dismiss());

        updateBackGroundDownload.setOnClickListener(view -> {
            if (onSignatureListener != null) {
                onSignatureListener.onSignature(saveSignaturePic());
            } else if (mOnSignatureTypeListener != null) {
                mOnSignatureTypeListener.onSignature(signatursType, saveSignaturePic());
            }
            dismiss();
        });


    }

    private String saveSignaturePic() {
        File imageFile = com.zhongtie.work.util.FileUtils.getOutputImageFilePath();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            mSignaturePad.getTransparentSignatureBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
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
