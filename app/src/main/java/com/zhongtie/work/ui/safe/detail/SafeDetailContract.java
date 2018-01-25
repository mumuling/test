package com.zhongtie.work.ui.safe.detail;

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
         * @param itemList       item数据源
         * @param isHideNullItem
         */
        void setItemList(List<Object> itemList, boolean isHideNullItem);

        void setSafeDetailInfo(SafeEventEntity titleUserInfo);

        void setEventStatus(SafeEventEntity.ButstateBean status);

        /**
         * 记录check 记录获取数据 设置分割线
         *
         * @param checkCount check数量
         */
        void setCheckCount(int checkCount);

        void noLookAuthority();

        int fetchEventId();
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 改变check user显示状态
         */
        void changeCheckListState();

        /**
         * 初始化获取数据
         *
         * @param safeOrderID eventID
         */
        void getEventDetailItemList(int safeOrderID);

        void checkUserSign(String imagePath);

        /**
         * 验证审核人事件
         * @param imagePath 签名文件地址
         */
        void validateEvent(String imagePath);
    }
}
