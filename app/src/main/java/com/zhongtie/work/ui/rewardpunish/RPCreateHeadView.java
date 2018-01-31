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
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.SelectSafeEventEntity;
import com.zhongtie.work.event.SelectCompanyEvent;
import com.zhongtie.work.ui.select.CommonSelectSearchActivity;
import com.zhongtie.work.ui.select.ProjectTeamSelectFragment;
import com.zhongtie.work.ui.select.SelectSupervisorFragment;
import com.zhongtie.work.util.TextUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.zhongtie.work.ui.safe.view.SafeCreateEditHeadView.UNIT;

/**
 * 创建头部数据
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class RPCreateHeadView extends LinearLayout implements View.OnClickListener {

    private EditText mCreateCode;
    private TextView mPunishTeam;
    private EditText mPunishAmount;
    private TextView mSupervisorInfo;

    private ProjectTeamEntity unitEntity;
    private SelectSafeEventEntity supervisorInfoEntity;

    public RPCreateHeadView(Context context) {
        this(context, null);
    }

    public RPCreateHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //注册单位选择回调
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe
    public void selectCompanyEvent(SelectCompanyEvent selectCompanyEvent) {
        if (selectCompanyEvent.getType() == UNIT) {
            unitEntity = selectCompanyEvent.getData();
            mPunishTeam.setText(unitEntity.getProjectTeamName());
        }
    }

    /**
     * 选择的安全督导信息
     *
     * @param supervisorInfoEntity 督导信息
     */
    @Subscribe
    public void selectSupervisorInfo(SelectSafeEventEntity supervisorInfoEntity) {
        this.supervisorInfoEntity = supervisorInfoEntity;
        mSupervisorInfo.setText(String.valueOf(supervisorInfoEntity.getUnit()));
    }

    /**
     * 安全奖罚 选择的单位
     *
     * @return 选择的单位明细 创建时候为必填项
     */
    public ProjectTeamEntity getUnitEntity() {
        return unitEntity;
    }

    /**
     * 安全奖惩编号为必填
     *
     * @return 输入的安全编号没有固定规则 随意填写但不能为空
     */
    public String getCreateSafeCode() {
        return mCreateCode.getText().toString();
    }


    /**
     * @return 返回选择的安全督导信息 可以不选择
     */
    public SelectSafeEventEntity getSupervisorInfoEntity() {
        return supervisorInfoEntity;
    }


    /**
     * 安全惩罚金额
     *
     * @return 金额为必填 必须大于0
     */
    public int getInputPunishAmount() {
        String amount = mPunishAmount.getText().toString();
        if (TextUtil.isEmpty(amount)) {
            return 0;
        }
        return Integer.valueOf(amount);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rp_create_head, this, true);
        mCreateCode = findViewById(R.id.create_code);
        RelativeLayout punishTeamLayout = findViewById(R.id.punish_team_layout);
        mPunishTeam = findViewById(R.id.punish_team);
        mPunishAmount = findViewById(R.id.punish_amount);
        RelativeLayout supervisorInfoSelectLayout = findViewById(R.id.supervisor_info_select_layout);
        mSupervisorInfo = findViewById(R.id.supervisor_info);
        punishTeamLayout.setOnClickListener(this);
        supervisorInfoSelectLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.punish_team_layout:
                //跳转到单位选择
                CommonSelectSearchActivity.newInstance(getContext(), ProjectTeamSelectFragment.class, getContext().getString(R.string.select_unit));
                break;
            case R.id.supervisor_info_select_layout:
                SelectSupervisorFragment.start(getContext());
                break;
            default:
        }

    }
}
