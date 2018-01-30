package com.zhongtie.work.ui.main.presenter;

import com.zhongtie.work.db.CacheCompanyTable;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * @author Chaek
 * @date:2018.1.14
 */

public interface MainContract {
    interface View extends BaseView {
        void setUserCompany(String company);

        void setAllCompanyList(List<CacheCompanyTable> allCompanyList);

        void setUserInfo(LoginUserInfoEntity userInfo);

        void onSyncCompanySuccess();

        void onSyncCompanyFail();
    }


    interface Presenter extends BasePresenter<View> {

        void fetchInitData();

        void switchSelectCompany(CacheCompanyTable companyEntity);
    }

}
