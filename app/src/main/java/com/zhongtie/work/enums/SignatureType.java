package com.zhongtie.work.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.zhongtie.work.ui.rewardpunish.detail.PunishDetailFragment.PUNISH_CONSENT_TYPE;
import static com.zhongtie.work.ui.rewardpunish.detail.PunishDetailFragment.PUNISH_CANCEL_TYPE;
import static com.zhongtie.work.ui.rewardpunish.detail.PunishDetailFragment.PUNISH_SEND_BACK_TYPE;
import static com.zhongtie.work.ui.rewardpunish.detail.PunishDetailFragment.PUNISH_SIGN_TYP;

/**
 * @author: Cheek
 * @date: 2018.1.29
 */

@Inherited
@IntDef(flag = true, value = {PUNISH_CONSENT_TYPE, PUNISH_CANCEL_TYPE, PUNISH_SEND_BACK_TYPE, PUNISH_SIGN_TYP})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignatureType {
}
