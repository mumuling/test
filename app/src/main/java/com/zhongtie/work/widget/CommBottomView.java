package com.zhongtie.work.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.endorse.EndorseListActivity;
import com.zhongtie.work.ui.rewardpunish.RewardPunishActivity;
import com.zhongtie.work.ui.safe.SafeSupervisionActivity;
import com.zhongtie.work.ui.scan.ScanQRCodeActivity;
import com.zhongtie.work.util.ListPopupWindowUtil;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class CommBottomView extends LinearLayout {
    public CommBottomView(Context context) {
        super(context);
    }

    public CommBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_menu, this, true);
        findViewById(R.id.qr_code_scan).setOnClickListener(view -> startQrCodeView());
        findViewById(R.id.safe).setOnClickListener(this::showSafePopup);
        findViewById(R.id.quality).setOnClickListener(this::showQualityPopup);
    }

    private void startQrCodeView() {
        ScanQRCodeActivity.newInstance(getContext());
    }

    /**
     * 质量弹窗选择
     */
    private void showQualityPopup(View v) {
        ListPopupWindowUtil.showListPopupWindow(v, Gravity.TOP, new String[]{"质量督导", "质量控制"}, (item, position) -> {
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
            }

        });
    }

    /**
     * 安全弹窗选择
     */
    private void showSafePopup(View v) {
        ListPopupWindowUtil.showListPopupWindow(v, Gravity.TOP, new String[]{"安全督导", "奖惩流程", "文件签认"}, (item, position) -> {
            switch (item) {
                case "安全督导":
                    SafeSupervisionActivity.newInstance(getContext());
                    break;
                case "文件签认":
                    EndorseListActivity.newInstance(getContext());
                    break;
                case "奖惩流程":
                    RewardPunishActivity.newInstance(getContext());
            }
        });
    }

}
