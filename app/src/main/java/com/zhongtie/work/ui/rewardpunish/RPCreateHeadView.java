package com.zhongtie.work.ui.rewardpunish;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.select.CommonSelectSearchActivity;
import com.zhongtie.work.ui.select.ProjectTeamSelectFragment;
import com.zhongtie.work.ui.select.SelectSupervisorFragment;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;

/**
 * 创建头部数据
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class RPCreateHeadView extends LinearLayout implements View.OnClickListener {

    private LinearLayout mCreateCodeLayout;
    private TextView mCreateCode;
    private RelativeLayout mPunishTeamLayout;
    private TextView mPunishTeamTitle;
    private TextView mPunishTeam;
    private EditText mPunishAmount;
    private RelativeLayout mSupervisorInfoSelectLayout;
    private TextView mSupervisorInfoTitle;
    private TextView mSupervisorInfo;

    public RPCreateHeadView(Context context) {
        this(context, null);
    }

    public RPCreateHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rp_create_head, this, true);
        mCreateCodeLayout = findViewById(R.id.create_code_layout);
        mCreateCode = findViewById(R.id.create_code);
        mPunishTeamLayout = findViewById(R.id.punish_team_layout);
        mPunishTeamTitle = findViewById(R.id.punish_team_title);
        mPunishTeam = findViewById(R.id.punish_team);
        mPunishAmount = findViewById(R.id.punish_amount);
        mSupervisorInfoSelectLayout = findViewById(R.id.supervisor_info_select_layout);
        mSupervisorInfoTitle = findViewById(R.id.supervisor_info_title);
        mSupervisorInfo = findViewById(R.id.supervisor_info);
        mPunishTeamLayout.setOnClickListener(this);
        mSupervisorInfoSelectLayout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.punish_team_layout:
                CommonSelectSearchActivity.newInstance(getContext(), ProjectTeamSelectFragment.class, "输入单位名称");
                break;
            case R.id.supervisor_info_select_layout:
                CommonFragmentActivity.newInstance(getContext(), SelectSupervisorFragment.class, "安全奖罚");
                break;
        }

    }
}
