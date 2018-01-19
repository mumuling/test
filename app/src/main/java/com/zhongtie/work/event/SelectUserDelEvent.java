package com.zhongtie.work.event;

import com.zhongtie.work.data.CommonUserEntity;

/**
 * Auth: Chaek
 * Date: 2018/1/15
 */

public class SelectUserDelEvent implements BaseEvent {

    private CommonUserEntity mCreateUserEntity;

    public SelectUserDelEvent(CommonUserEntity createUserEntity) {
        mCreateUserEntity = createUserEntity;
    }

    public CommonUserEntity getCreateUserEntity() {
        return mCreateUserEntity;
    }

    public void setCreateUserEntity(CommonUserEntity createUserEntity) {
        mCreateUserEntity = createUserEntity;
    }
}
