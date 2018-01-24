package com.zhongtie.work.event;

/**
 * Auth: Chaek
 * Date: 2018/1/17
 */

public class ExitEvent implements BaseEvent {
    private boolean isExit;

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }
}
