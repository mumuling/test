package com.zhongtie.work.list;

import com.zhongtie.work.enums.SignatureType;

/**
 * Auth:Cheek
 * date:2018.1.12
 */

public interface OnSignatureTypeListener {
    void onSignature(@SignatureType int type, String imagePath);
}
