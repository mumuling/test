package com.zhongtie.work.app;

import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.event.ExitEvent;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.util.TextUtil;

import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_COMPANY;
import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_ID;

/**
 * Auth: Chaek
 * Date: 2018/1/15
 */

public class Cache {

    /**
     * 是否登录
     *
     * @return true 登录 false 未登录
     */
    public static boolean isUserLogin() {
        String userId = SharePrefUtil.getUserPre().getString(LOGIN_USER_ID, "");
        return !TextUtil.isEmpty(userId);
    }

    /**
     * 获取用户
     *
     * @return 用户ID
     */
    public static String getUserID() {
        return SharePrefUtil.getUserPre().getString(LOGIN_USER_ID, "");
    }
    public static int getUserUserCompany() {
        return SharePrefUtil.getUserPre().getInt(LOGIN_USER_COMPANY, 0);
    }
    public static int getSelectCompany() {
        return SharePrefUtil.getUserPre().getInt(LOGIN_USER_COMPANY, 0);
    }

    public static LoginUserInfoEntity getUser() {
        return App.getInstance().getUser();
    }

    public static void exitLogin() {
        new ExitEvent().post();
        SharePrefUtil.getUserPre().putString(LOGIN_USER_ID, "");
    }
}
