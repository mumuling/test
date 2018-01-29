package com.zhongtie.work.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.zhongtie.work.ui.rewardpunish.detail.RPOrderDetailFragment.PUNISH_AGREE_TYPE;
import static com.zhongtie.work.ui.rewardpunish.detail.RPOrderDetailFragment.PUNISH_CANCEL_TYPE;
import static com.zhongtie.work.ui.rewardpunish.detail.RPOrderDetailFragment.PUNISH_RETURN_TYPE;
import static com.zhongtie.work.ui.rewardpunish.detail.RPOrderDetailFragment.PUNISH_SIGN_TYP;

/**
 * @author: Cheek
 * @date: 2018.1.29
 */

@Inherited
@IntDef(flag = true, value = {PUNISH_AGREE_TYPE, PUNISH_CANCEL_TYPE, PUNISH_RETURN_TYPE, PUNISH_SIGN_TYP})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignatureType {
}
