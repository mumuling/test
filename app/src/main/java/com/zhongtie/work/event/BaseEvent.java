package com.zhongtie.work.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

public class BaseEvent {
    public void post() {
        EventBus.getDefault().post(this);
    }
}