package com.zhongtie.work.ui.user;

import android.util.Pair;

import com.zhongtie.work.data.KeyValueEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public interface UserContract {
    interface View extends BaseView {

        void modifyPasswordSuccess();

        void modifyPasswordFail();

        void setListUserInfoList(List<KeyValueEntity<String, String>> listUserInfoList);

        String getModifyPassword();

    }

    interface Presenter extends BasePresenter<View> {
        void modifyPassword();

        void fetchUserInfo();
    }
}
