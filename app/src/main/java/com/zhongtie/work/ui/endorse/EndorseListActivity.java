package com.zhongtie.work.ui.endorse;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldf.calendar.model.CalendarDate;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.adapter.ZtFragmentAdapter;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.endorse.create.EndorseCreateFragment;
import com.zhongtie.work.ui.safe.dialog.calendar.CalendarDialog;
import com.zhongtie.work.util.ResourcesUtils;
import com.zhongtie.work.widget.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件签认
 *
 * @author Chaek
 * @date:2018.1.9
 */

public class EndorseListActivity extends BaseActivity implements CalendarDialog.OnSelectDateCallback {
    private TextView mSelectDate;
    private ImageView mSelectDateImg;
    private CaterpillarIndicator mProjectTitleBar;
    private ViewPager mViewPage;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, EndorseListActivity.class));
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.safe_supervision_activity;
    }

    @Override
    protected void initView() {
        setTitle(R.string.endorse_file_title);
        setRightText(getString(R.string.create_title));
        initDrawCreateIcon();
        mSelectDate = (TextView) findViewById(R.id.select_date);
        mSelectDateImg = (ImageView) findViewById(R.id.select_date_img);
        mProjectTitleBar = (CaterpillarIndicator) findViewById(R.id.project_title_bar);
        mViewPage = (ViewPager) findViewById(R.id.view_page);
        mSelectDateImg.setOnClickListener(v -> showSelectDate());
    }

    private void initDrawCreateIcon() {
        Drawable drawable = getResources().getDrawable(R.drawable.btn_talk);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mMenuTitle.setCompoundDrawablePadding(ResourcesUtils.dip2px(5));
        mMenuTitle.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 筛选日期选择
     */
    private void showSelectDate() {
        CalendarDialog calendarDialog = new CalendarDialog(this, this);
        calendarDialog.show();
    }

    @Override
    protected void initData() {
        List<BaseFragment> supervisionFragments = new ArrayList<>();
        supervisionFragments.add(EndorseListFragment.newInstance(0));
        supervisionFragments.add(EndorseListFragment.newInstance(1));
        ZtFragmentAdapter adapter = new ZtFragmentAdapter(getSupportFragmentManager(), supervisionFragments);
        mViewPage.setAdapter(adapter);
        mProjectTitleBar.initTitle(mViewPage, R.array.endorse_file_switch_title);
        initDate();
    }

    private void initDate() {
        CalendarDate calendarDate = new CalendarDate();
        mSelectDate.setText(getString(R.string.safe_select_title_date, calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDay()));
    }

    @Override
    protected void onClickRight() {
        super.onClickRight();
        EndorseCreateFragment.start(this);
    }

    @Override
    public void onSelectDate(String date) {
        mSelectDate.setText(date);
    }
}
