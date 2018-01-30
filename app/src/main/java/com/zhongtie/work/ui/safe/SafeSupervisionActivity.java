package com.zhongtie.work.ui.safe;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldf.calendar.model.CalendarDate;
import com.zhongtie.work.R;
import com.zhongtie.work.list.OnDateCallback;
import com.zhongtie.work.ui.adapter.ZtFragmentAdapter;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.safe.dialog.calendar.CalendarDialog;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.util.parse.BindKey;
import com.zhongtie.work.widget.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全督导列表切换
 * date:2018.1.9
 *
 * @author Chaek
 */

public class SafeSupervisionActivity extends BaseActivity implements CalendarDialog.OnSelectDateCallback,
        OnDateCallback {
    public static final String KEY_IS_SELECT = "is_select";
    private TextView mSelectDate;
    private CaterpillarIndicator mProjectTitleBar;
    private ViewPager mViewPage;
    private List<BaseFragment> supervisionFragments;

    private String mSelectTime;
    private CalendarDialog calendarDialog;

    @BindKey(KEY_IS_SELECT)
    private boolean isSelect = false;

    public static void newInstance(Context context) {
        newInstance(context, false);
    }

    public static void newInstance(Context context, boolean isSelect) {
        Intent starter = new Intent(context, SafeSupervisionActivity.class);
        starter.putExtra(KEY_IS_SELECT, isSelect);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.safe_supervision_activity;
    }

    @Override
    protected void initView() {
        mSelectDate = findViewById(R.id.select_date);
        ImageView selectDateImg = findViewById(R.id.select_date_img);
        mProjectTitleBar = findViewById(R.id.project_title_bar);
        mViewPage = findViewById(R.id.view_page);
        selectDateImg.setOnClickListener(v -> showSelectDate());

        setTitle(getString(R.string.safe_supervision_title));
        if (!isSelect) {
            setRightText(getString(R.string.create_title));
            Drawable drawable = getResources().getDrawable(R.drawable.btn_talk);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mMenuTitle.setCompoundDrawablePadding(ViewUtils.dip2px(5));
            mMenuTitle.setCompoundDrawables(drawable, null, null, null);
        } else {
            //安全处罚 创建选择安全督导
            findViewById(R.id.bottom).setVisibility(View.GONE);
            mProjectTitleBar.setVisibility(View.GONE);
        }

    }

    /**
     * 筛选日期选择
     */
    private void showSelectDate() {
        if (calendarDialog == null) {
            calendarDialog = new CalendarDialog(this, this);
        }
        calendarDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (calendarDialog != null) {
            calendarDialog.cancel();
        }
    }

    @Override
    protected void initData() {
        supervisionFragments = new ArrayList<>();
        supervisionFragments.add(SafeSupervisionFragment.newInstance(0, isSelect));
        if (!isSelect) {
            supervisionFragments.add(SafeSupervisionFragment.newInstance(1, isSelect));
            supervisionFragments.add(SafeSupervisionFragment.newInstance(2, isSelect));
        }

        ZtFragmentAdapter adapter = new ZtFragmentAdapter(getSupportFragmentManager(), supervisionFragments);
        mViewPage.setAdapter(adapter);
        mProjectTitleBar.initTitle(mViewPage, R.array.safe_list_title);
        initDate();

    }

    private void initDate() {
        CalendarDate calendarDate = new CalendarDate();
        mSelectTime = getString(R.string.safe_select_title_date, calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDay());
        mSelectDate.setText(mSelectTime);
    }

    @Override
    protected void onClickRight() {
        super.onClickRight();
        SafeSupervisionCreateActivity.newInstance(this, SafeSupervisionCreateFragment.class, getString(R.string.safe_supervision_title));
    }

    @Override
    public void onSelectDate(String date) {
        mSelectDate.setText(date);
        mSelectTime = date;
        for (Fragment fragment : supervisionFragments) {
            if (fragment instanceof SafeSupervisionFragment) {
                ((SafeSupervisionFragment) fragment).onRefresh();
            }
        }
    }


    @Override
    public String getSelectDate() {
        return mSelectTime;
    }
}
