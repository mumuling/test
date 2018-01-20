package com.zhongtie.work.app;

import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.event.ExitEvent;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.util.TextUtil;

import static com.zhongtie.work.ui.login.LoginPresenter.SELECT_COMPANY_ID;
import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_ID;
import static com.zhongtie.work.ui.login.LoginPresenter.USER_COMPANY_ID;

/**
 * Date: 2018/1/15
 *
 * @author Chaek
 */

public class Cache {
    /**
     * 是否为leader key值
     */
    public static final String KEY_IS_LEADER = "is_leader";

    /**
     * 获取当前参数值
     * 用来区分接口请求 和输入判断
     *
     * @return 0 不是leader 1是leader
     */
    public static int getLeaderFlag() {
        return SharePrefUtil.getUserPre().getInt(KEY_IS_LEADER, 0);
    }

    /**
     * 是否是领导组
     *
     * @return true 是领导 false不是领导
     */
    public static boolean isLeader() {
        return getLeaderFlag() == 1;
    }

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

    /**
     * 获取用户
     *
     * @return 用户ID
     */
    public static int getUserIntID() {
        return Integer.valueOf(SharePrefUtil.getUserPre().getString(LOGIN_USER_ID, "0"));
    }

    /**
     * 获取登录用户的公司ID 只会在登录成功之后保存 之后只有切换账号才会更换
     *
     * @return 公司ID
     */
    public static int getUserUserCompany() {
        return SharePrefUtil.getUserPre().getInt(USER_COMPANY_ID, 0);
    }

    /**
     * 获取选择的公司ID 首页切换公司切换功能 每次更换会替换
     * 一般接口用到的公司ID 则为这个参数值
     *
     * @return 公司ID
     */
    public static int getSelectCompany() {
        return SharePrefUtil.getUserPre().getInt(SELECT_COMPANY_ID, 0);
    }

    /**
     * 获取登录用户的基本参数
     *
     * @return 基本参数
     */
    public static LoginUserInfoEntity getUser() {
        return App.getInstance().getUser();
    }

    /**
     * 退出登录 以是否存在userId为标识
     * 并发送退出事件通知会关闭所有的Activity 并退出
     */
    public static void exitLogin() {
        new ExitEvent().post();
        SharePrefUtil.getUserPre().putString(LOGIN_USER_ID, "");
    }
}
