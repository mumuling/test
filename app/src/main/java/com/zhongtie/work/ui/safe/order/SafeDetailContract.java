package com.zhongtie.work.ui.safe.order;

import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface SafeDetailContract {
    interface View extends BaseView {
        /**
         * @param itemList item数据源
         */
        void setItemList(List<Object> itemList);

        void setSafeDetailInfo(SafeEventEntity titleUserInfo);

        void setEventStatus(SafeEventEntity.ButstateBean status);

        void noLookAuthority();
    }

    interface Presenter extends BasePresenter<View> {
        void getItemList(int safeOrderID);

        void setSelectImageList(List<String> selectImgList);

        void setSelectUserInfoList(String title, List createUserEntities);
    }
}
