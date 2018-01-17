package com.zhongtie.work.ui.safe.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.R;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.event.SelectCompanyEvent;
import com.zhongtie.work.db.WorkTeamEntity;
import com.zhongtie.work.ui.safe.dialog.SelectDateTimeDialog;
import com.zhongtie.work.ui.select.CommonSelectSearchActivity;
import com.zhongtie.work.ui.select.ProjectTeamSelectFragment;
import com.zhongtie.work.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 安全督导创建创建头部输入数据
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class SafeCreateEditHeadView extends LinearLayout implements View.OnClickListener, SelectDateTimeDialog.OnSelectDateTimeListener {
    public static final int UNIT = 0;
    public static final int WORK_TEAM = 1;
    /**
     * 创建时间
     */
    private TextView mCreateTime;
    /**
     * 输入地址
     */
    private EditText mCreateAddress;

    private RelativeLayout mCreateCompanySelect;
    /**
     * 单位名称
     */
    private TextView mCreateCompany;
    private RelativeLayout mCreateCompanyWorkSelect;
    /**
     * 劳务公司
     */
    private TextView mCreateCompanyWork;

    private ProjectTeamEntity unitEntity;
    private ProjectTeamEntity workTeamEntity;
    private String mSelectTime;

    private SelectDateTimeDialog mSelectDateTimeDialog;

    public SafeCreateEditHeadView(Context context) {
        this(context, null);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_safe_create_base_info_title, this, true);
        mCreateTime = findViewById(R.id.create_time);
        mCreateAddress = findViewById(R.id.create_address);
        mCreateCompanySelect = findViewById(R.id.create_company_select);
        mCreateCompany = findViewById(R.id.create_company);
        mCreateCompanyWorkSelect = findViewById(R.id.create_company_work_select);
        mCreateCompanyWork = findViewById(R.id.create_company_work);
        findViewById(R.id.create_time_select).setOnClickListener(this);
        mCreateCompanySelect.setOnClickListener(this);
        mCreateCompanyWorkSelect.setOnClickListener(this);
        setCreateTime(TimeUtils.formatYDate(System.currentTimeMillis()));
    }


    private void setCreateTime(String time) {
        mSelectTime = time;
        mCreateTime.setText(mSelectTime);
    }


    /**
     * @return 所属单位
     */
    public ProjectTeamEntity getCompanyUnitEntity() {
        return unitEntity;
    }

    /**
     * @return 劳务关系公司
     */
    public ProjectTeamEntity getCompanyOfferEntity() {
        return workTeamEntity;
    }

    /**
     * @return 获取选择的日期
     */
    public String getSelectDateTime() {
        return mCreateTime.getText().toString();
    }

    /**
     * @return 获取输入的地址
     */
    public String getEditSite() {
        return mCreateAddress.getText().toString();
    }

    public SafeCreateEditHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
        initWorkTeam();
    }

    private void initWorkTeam() {
        //没有劳务公司 不展示选择
        long workTeamCount = SQLite.selectCountOf().from(WorkTeamEntity.class).count();
        if (workTeamCount <= 0) {
            mCreateCompanyWorkSelect.setVisibility(GONE);
        } else {
            mCreateCompanyWorkSelect.setVisibility(VISIBLE);
        }
    }

    @Subscribe
    public void selectCompanyEvent(SelectCompanyEvent selectCompanyEvent) {
        if (selectCompanyEvent.getType() == UNIT) {
            unitEntity = selectCompanyEvent.getData();
            mCreateCompany.setText(unitEntity.getProjectTeamName());
        } else {
            workTeamEntity = selectCompanyEvent.getData();
            mCreateCompanyWork.setText(workTeamEntity.getProjectTeamName());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_time_select:
                showSelectDateTime();
                break;
            case R.id.create_company_select:
                startSelectCompany(UNIT);
                break;
            case R.id.create_company_work_select:
                startSelectCompany(WORK_TEAM);
                break;
            default:
        }
    }

    private Fragment getFragment(Context activity) {
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            return fragmentManager.getFragments().get(0);
        }
        return null;
    }

    /**
     * @param type 0选择单位 1选择劳务公司
     */
    private void startSelectCompany(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(ProjectTeamSelectFragment.TYPE, type);
        if (type == 0) {
            bundle.putString(CommonSelectSearchActivity.SEARCH_HINT, "输入单位名称");
        } else {
            bundle.putString(CommonSelectSearchActivity.SEARCH_HINT, "输入公司名称");
        }
        CommonSelectSearchActivity.newInstance(getFragment(getContext()), ProjectTeamSelectFragment.class, "输入名称", bundle);


    }

    /**
     * 选择日期
     */
    private void showSelectDateTime() {
        if (mSelectDateTimeDialog == null) {
            SelectDateTimeDialog.Build build = new SelectDateTimeDialog.Build(getContext());
            build.setSelectDateTime(this);
            build.setSelectDateTime(new SelectDateTimeDialog.OnSelectDateTimeListener() {
                @Override
                public void setTimeDate(String datetime, int type) {
                    setCreateTime(datetime);
                }

                @Override
                public void setSelectType(int[] type, int buildType) {
                }
            });
            build.setType(SelectDateTimeDialog.BIRTH_DATE).setDataModel(SelectDateTimeDialog.Build.BIRTH);
            mSelectDateTimeDialog = build.create();
        }
        mSelectDateTimeDialog.show();
    }

    @Override
    public void setTimeDate(String datetime, int type) {
        mCreateTime.setText(datetime);
    }

    @Override
    public void setSelectType(int[] type, int buildType) {
    }
}
