package com.zhongtie.work.ui.rewardpunish.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
    private TextView mDetailCode = (TextView) findViewById(R.id.detail_code);
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

    public void initData() {
        mCreateUserPic.loadImage(imageUrls[0]);
        mCreateUserName.setText("刘玉梅");
        mCreateTime.setText("2017-06-20 11:02");
        mDetailCode.setText("重庆2017-61");
        mPunishProjectTeam.setText("城建部");
        mPunishAmount.setText("500");
    }


    @Override
    public void onClick(View view) {
        SafeOrderDetailFragment.start(getContext(), detailInfo.getSupervisionId());
    }

    public void setDetailInfo(RewardPunishDetailEntity detailInfo) {
        this.detailInfo = detailInfo;
        mCreateUserPic.loadImage(detailInfo.getCreateUserPic());
        mCreateUserName.setText(detailInfo.getCreateUserName());
        mCreateTime.setText(detailInfo.getCreateTime());
        mDetailCode.setText(detailInfo.getPunishCode());
        mPunishProjectTeam.setText(detailInfo.getPunishCompany());
        mPunishAmount.setText(String.valueOf(detailInfo.getPunishAmount()));
        if (detailInfo.getSafeEventId() <= 0) {
            mSupervisorReadLayout.setVisibility(GONE);
        } else {
            mSupervisorReadLayout.setVisibility(VISIBLE);
        }

    }
}
