package com.zhongtie.work.event;

import org.greenrobot.eventbus.EventBus;

/**
 * date:2018.1.11
 * @author Chaek
 */

public interface BaseEvent {
    /**
     * 发送事件
     */
    default void post() {
        EventBus.getDefault().post(this);
    }
}
