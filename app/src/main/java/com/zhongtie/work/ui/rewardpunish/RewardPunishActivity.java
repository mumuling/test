package com.zhongtie.work.ui.rewardpunish;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldf.calendar.model.CalendarDate;
import com.zhongtie.work.R;
import com.zhongtie.work.list.OnDateCallback;
import com.zhongtie.work.ui.adapter.ZtFragmentAdapter;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.rewardpunish.dialog.SelectDateTimeDialog;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.ui.safe.dialog.calendar.CalendarDialog;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全处罚
 *
 * @author Chaek
 * @date:2018.1.9
 */

public class RewardPunishActivity extends BaseActivity implements  OnDateCallback, SelectDateTimeDialog.OnSelectDateTimeListener {
    private TextView mSelectDate;
    private CaterpillarIndicator mProjectTitleBar;
    private ViewPager mViewPage;
    private String createTime;
    private SelectDateTimeDialog mSelectDateTimeDialog;
    private List<BaseFragment> mFragments;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, RewardPunishActivity.class));
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.safe_supervision_activity;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.safe_reward_punish));
        setRightText(getString(R.string.create_title));
        initDrawCreateIcon();
        mSelectDate = findViewById(R.id.select_date);
        ImageView selectDateImg = findViewById(R.id.select_date_img);
        mProjectTitleBar = findViewById(R.id.project_title_bar);
        mViewPage = findViewById(R.id.view_page);
        selectDateImg.setOnClickListener(v -> showSelectDate());
    }

    private void initDrawCreateIcon() {
        Drawable drawable = getResources().getDrawable(R.drawable.btn_talk);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mMenuTitle.setCompoundDrawablePadding(ViewUtils.dip2px(5));
        mMenuTitle.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 筛选日期选择
     */
    private void showSelectDate() {
        if (mSelectDateTimeDialog == null) {
            SelectDateTimeDialog.Build build = new SelectDateTimeDialog.Build(this);
            build.setSelectDateTime(this);
            build.setType(SelectDateTimeDialog.BIRTH_DATE).setDataModel(SelectDateTimeDialog.Build.BIRTH);
            mSelectDateTimeDialog = build.create();
        }
        mSelectDateTimeDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSelectDateTimeDialog != null && mSelectDateTimeDialog.isShowing()) {
            mSelectDateTimeDialog.dismiss();
        }
    }

    @Override
    protected void initData() {
        initDate();
        mFragments = new ArrayList<>();
        mFragments.add(RewardPunishFragment.newInstance(0));
        mFragments.add(RewardPunishFragment.newInstance(1));

        ZtFragmentAdapter adapter = new ZtFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPage.setAdapter(adapter);

        mProjectTitleBar.initTitle(mViewPage, R.array.punish_title_list);
    }

    private void initDate() {
        CalendarDate calendarDate = new CalendarDate();
        setCreateTime(getString(R.string.punish_select_title_date, calendarDate.getYear(), calendarDate.getMonth()));
    }

    @Override
    protected void onClickRight() {
        super.onClickRight();
        RewardPunishCreateFragment.start(this);
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
        mSelectDate.setText(createTime);
    }


    @Override
    public String getSelectDate() {
        return createTime;
    }

    @Override
    public void setTimeDate(String datetime, int type) {
        setCreateTime(datetime);
        for (BaseFragment fragment : mFragments) {
            if (fragment instanceof RewardPunishFragment) {
                ((RewardPunishFragment) fragment).onRefresh();
            }
        }
    }

    @Override
    public void setSelectType(int[] type, int buildType) {

    }
}
