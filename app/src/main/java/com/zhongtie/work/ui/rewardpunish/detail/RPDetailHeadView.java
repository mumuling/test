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
import com.zhongtie.work.widget.BaseImageView;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * 创建头部数据
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class RPDetailHeadView extends LinearLayout implements View.OnClickListener {


    private ImageView mOrderStateImg;
    private BaseImageView mSafeOrderReplyHead;
    private BaseImageView mSafeOrderReplySign;
    private TextView mSafeOrderName;
    private TextView mSafeOrderReplyTime;
    private TextView mDetailCode;
    private TextView mPunishProjectTeam;
    private TextView mPunishAmount;
    private RelativeLayout mSupervisorLookLayout;


    public RPDetailHeadView(Context context) {
        this(context, null);
    }

    public RPDetailHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rp_detail_head_view, this, true);

        mOrderStateImg = (ImageView) findViewById(R.id.order_state_img);
        mSafeOrderReplyHead = (BaseImageView) findViewById(R.id.safe_order_reply_head);
        mSafeOrderReplySign = (BaseImageView) findViewById(R.id.safe_order_reply_sign);
        mSafeOrderName = (TextView) findViewById(R.id.safe_order_name);
        mSafeOrderReplyTime = (TextView) findViewById(R.id.safe_order_reply_time);
        mDetailCode = (TextView) findViewById(R.id.detail_code);
        mPunishProjectTeam = (TextView) findViewById(R.id.punish_project_team);
        mPunishAmount = (TextView) findViewById(R.id.punish_amount);
        mSupervisorLookLayout = (RelativeLayout) findViewById(R.id.supervisor_look_layout);
        mSupervisorLookLayout.setOnClickListener(this);

    }

    public void initData() {
        mSafeOrderReplyHead.loadImage(imageUrls[0]);
        mSafeOrderName.setText("刘玉梅");
        mSafeOrderReplyTime.setText("2017-06-20 11:02");
        mDetailCode.setText("重庆2017-61");
        mPunishProjectTeam.setText("城建部");
        mPunishAmount.setText("500");

    }


    @Override
    public void onClick(View view) {

    }
}
