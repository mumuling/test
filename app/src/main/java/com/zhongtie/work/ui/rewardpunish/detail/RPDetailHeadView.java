package com.zhongtie.work.ui.rewardpunish.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.ui.safe.detail.SafeOrderDetailFragment;
import com.zhongtie.work.widget.BaseImageView;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * 创建头部数据
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class RPDetailHeadView extends LinearLayout implements View.OnClickListener {


    private ImageView mOrderStateImg;
    private BaseImageView mCreateUserPic;
    private BaseImageView mSafeOrderReplySign;
    private TextView mCreateUserName;
    private TextView mCreateTime;
    private TextView mDetailCode;
    private TextView mPunishProjectTeam;
    private TextView mPunishAmount;
    private RelativeLayout mSupervisorReadLayout;
    private RewardPunishDetailEntity detailInfo;


    public RPDetailHeadView(Context context) {
        this(context, null);
    }

    public RPDetailHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rp_detail_head_view, this, true);
        mDetailCode = findViewById(R.id.detail_code);
        mOrderStateImg = findViewById(R.id.order_state_img);
        mCreateUserPic = findViewById(R.id.safe_order_reply_head);
        mSafeOrderReplySign = findViewById(R.id.safe_order_reply_sign);
        mCreateUserName = findViewById(R.id.safe_order_name);
        mCreateTime = findViewById(R.id.safe_order_reply_time);
        mPunishProjectTeam = findViewById(R.id.punish_project_team);
        mPunishAmount = findViewById(R.id.punish_amount);
        mSupervisorReadLayout = findViewById(R.id.supervisor_look_layout);
        mSupervisorReadLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        SafeOrderDetailFragment.start(getContext(), detailInfo.getSafeEventId());
    }

    public void setDetailInfo(RewardPunishDetailEntity detailInfo) {
        this.detailInfo = detailInfo;
        mCreateUserPic.loadImage(detailInfo.getCreateUserPic());
        mCreateUserName.setText(detailInfo.getCreateUserName());
        mCreateTime.setText(com.zhongtie.work.util.TimeUtils.formatEventTime(detailInfo.getCreateTime()));
        mDetailCode.setText(detailInfo.getPunishCode());
        mPunishProjectTeam.setText(detailInfo.getPunishCompany());
        mPunishAmount.setText(String.valueOf(detailInfo.getPunishAmount()));
        if (detailInfo.getSafeEventId() <= 0) {
            mSupervisorReadLayout.setVisibility(GONE);
        } else {
            mSupervisorReadLayout.setVisibility(VISIBLE);
        }

        switch (detailInfo.getPunishState()) {
            case "审批中":
                mOrderStateImg.setImageResource(R.drawable.status_approve_loading);
                break;
            case "已审批":
                mOrderStateImg.setImageResource(R.drawable.status_approve);
                break;
            case "已取消":
                mOrderStateImg.setImageResource(R.drawable.status_cancel);
                break;
            case "已退回":
                mOrderStateImg.setImageResource(R.drawable.status_exit);
                break;
            case "已完结":
                mOrderStateImg.setImageResource(R.drawable.status_over);
                break;
            case "已通过":
                mOrderStateImg.setImageResource(R.drawable.status_pass);
                break;
            default:

        }
    }
}
