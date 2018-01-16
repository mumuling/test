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

        void noFindUserInfo();

        void setUserName(String name);

        void setUserHead(String userHead);

        void setUserCardCode(String userCardCode);

        void setUserDuty(String userDuty);

        void setUserWorkType(String userWorkType);

        void setUserUnit(String userUnit);

        void setUserLearn(String userLearn);

        void setUserHealth(String userHealth);

        void setUserOnJob(String userOnJob);

        void setUserInsure(String userInsure);

        void setUserWorkTeam(String workTeam);

        void addWrongSuccess();

        void setUserWrongMessage(String wrong);
    }

    interface Presenter extends BasePresenter<View> {

        void fetchQRCodeInfo(String QRCodeInfo);

        void addWrong(String content);
    }

}
