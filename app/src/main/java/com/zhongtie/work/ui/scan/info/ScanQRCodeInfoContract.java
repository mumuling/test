package com.zhongtie.work.ui.scan.info;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface ScanQRCodeInfoContract {

    interface View extends BaseView {

        void setInfoList(List<Object> objectList);

    }

    interface Presenter extends BasePresenter<View> {

        void fetchQRCodeInfo(String QRCodeInfo);
    }

}
