package com.zhongtie.work.ui.main;

import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.14
 */

public interface MainContract {
    interface View extends BaseView {
        void setUserCompany(String company);

        void setAllCompanyList(List<CompanyEntity> allCompanyList);

        void setUserInfo(LoginUserInfoEntity userInfo);
    }


    interface Presenter extends BasePresenter<View> {

        void fetchInitData();
    }

}
