package com.zhongtie.work.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Inherited;

import static com.zhongtie.work.ui.safe.dialog.calendar.CalendarDialog.SAFE_EVENT_COUNT;
import static com.zhongtie.work.ui.safe.dialog.calendar.CalendarDialog.SAFE_PUNISH_COUNT;


/**
 * @author: Chaek
 * @date: 2018/1/30
 */
@Inherited
@IntDef(flag = true, value = {SAFE_EVENT_COUNT, SAFE_PUNISH_COUNT})
public @interface CalendarType {
}
