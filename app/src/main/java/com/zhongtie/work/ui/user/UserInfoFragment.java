package com.zhongtie.work.ui.user;

import android.util.Pair;

import com.zhongtie.work.ui.base.BasePresenterFragment;

import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class UserInfoFragment extends BasePresenterFragment<UserContract.Presenter> implements UserContract.View {

    @Override
    protected UserContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public int getLayoutViewId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void modifyPasswordSuccess() {

    }

    @Override
    public void modifyPasswordFail() {

    }

    @Override
    public void setListUserInfoList(List<Pair<String, String>> listUserInfoList) {

    }
}
