package com.zhongtie.work.ui.endorse.create;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface EndorseCreateContract {
    interface View extends BaseView {

        void setItemList(List<Object> itemList);

        String getEndorseTitle();


        void createSuccess();

        void modifySuccess();

    }

    interface Presenter extends BasePresenter<View> {

        void getItemList(int safeOrderID);

        /**
         * 选择的人
         *
         * @param title              标题用作对应界面的数据更新
         * @param createUserEntities list数据源 一般为选择的人物或者是查阅组
         */
        void setSelectList(String title, List createUserEntities);

        /**
         * 创建或修改文件签认
         */
        void createEndorse();
    }
}
