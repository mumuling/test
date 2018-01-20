package com.zhongtie.work.ui.safe.presenter;

import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * date:2018.1.9
 * @author Chaek
 */
public interface SafeCreateContract {
    interface View extends BaseView {
        /**
         * 设置安全督导显示的ITEM
         *
         * @param itemList item数据源
         */
        void setItemList(List<Object> itemList);

        /**
         * 选择日期
         * @return
         */
        String getSelectDate();

        /**
         * @return 所属单位
         */
        ProjectTeamEntity getCompanyUnitEntity();

        /**
         * @return 劳务关系公司
         */
        ProjectTeamEntity getCompanyTeamEntity();

        /**
         * @return true显示才验证劳务公司
         */
        boolean isShowWorkTeam();


        /**
         * 获取输入的地址
         * @return 地址
         */
        String getEditSite();

        void createSuccess();

        void setModifyInfo(SafeEventEntity titleUserInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void getItemList(int safeOrderID);

        void setSelectImageList(List<String> selectImgList);

        void setSelectUserInfoList(String title, List createUserEntities);

        void createSafeOrder();
    }
}
