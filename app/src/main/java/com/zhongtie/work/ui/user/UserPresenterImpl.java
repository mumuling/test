package com.zhongtie.work.ui.user;

import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.KeyValueEntity;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Auth:Cheek
 * date:2018.1.10
 */

public class UserPresenterImpl extends BasePresenterImpl<UserContract.View> implements UserContract.Presenter {

    @Override
    public void modifyPassword() {

        String passWord = mView.getModifyPassword();
        if (TextUtil.isEmpty(passWord)) {
            mView.showToast("请输入密码");
            return;
        }
        if (passWord.length() < 6) {
            mView.showToast("请输入大于6位数的密码");
            return;
        }

        addDispose(Http.netServer(UserApi.class)
                .modifyPassword(Cache.getUserID(), passWord)
                .delay(200, TimeUnit.MILLISECONDS)
                .compose(Network.convertDialogTip(mView, "正在修改密码"))
                .subscribe(s -> mView.modifyPasswordSuccess(), throwable -> {
                            mView.modifyPasswordFail();
                        }
                ));

    }

    @Override
    public void fetchUserInfo() {
        if (Cache.isUserLogin()) {
            if (Cache.getUser() != null) {
                mapUserInfoList(Cache.getUser());
            }
            mView.initLoading();
            Http.netServer(UserApi.class).userInfo(Cache.getUserID())
                    .map(new NetWorkFunc1<>())
                    .map(loginUserInfoEntity -> {
                        loginUserInfoEntity.save();
                        return loginUserInfoEntity;
                    })
                    .compose(Network.networkIO())
                    .subscribe(this::mapUserInfoList
                            , throwable -> {
                            });
        }

    }

    private void mapUserInfoList(LoginUserInfoEntity user) {
        List<KeyValueEntity<String, String>> userInfoList = new ArrayList<>();
        userInfoList.add(new KeyValueEntity<>("姓名", user.getName()));
        userInfoList.add(new KeyValueEntity<>("登录名", user.getAccount()));
        userInfoList.add(new KeyValueEntity<>("密码", ""));
        userInfoList.add(new KeyValueEntity<>("性别", user.getSex()));
        userInfoList.add(new KeyValueEntity<>("身份证", user.getIdentity()));
        userInfoList.add(new KeyValueEntity<>("职务", user.getCompanyname()));
        userInfoList.add(new KeyValueEntity<>("工种", user.getWorktype()));
        mView.setListUserInfoList(userInfoList);
        mView.initSuccess();
    }


}
