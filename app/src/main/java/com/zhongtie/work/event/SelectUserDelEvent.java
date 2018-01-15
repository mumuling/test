package com.zhongtie.work.event;

import com.zhongtie.work.data.create.CreateUserEntity;

/**
 * Auth: Chaek
 * Date: 2018/1/15
 */

public class SelectUserDelEvent implements BaseEvent {

    private CreateUserEntity mCreateUserEntity;

    public SelectUserDelEvent(CreateUserEntity createUserEntity) {
        mCreateUserEntity = createUserEntity;
    }

    public CreateUserEntity getCreateUserEntity() {
        return mCreateUserEntity;
    }

    public void setCreateUserEntity(CreateUserEntity createUserEntity) {
        mCreateUserEntity = createUserEntity;
    }
}
