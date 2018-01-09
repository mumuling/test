package com.zhongtie.work.ui.safesupervision;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.adapter.ZtFragmentAdapter;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.widget.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeSupervisionActivity extends BaseActivity {
    private TextView mSelectDate;
    private ImageView mSelectDateImg;
    private CaterpillarIndicator mProjectTitleBar;
    private ViewPager mViewPage;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, SafeSupervisionActivity.class));
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.safe_supervision_activity;
    }

    @Override
    protected void initView() {
        mSelectDate = (TextView) findViewById(R.id.select_date);
        mSelectDateImg = (ImageView) findViewById(R.id.select_date_img);
        mProjectTitleBar = (CaterpillarIndicator) findViewById(R.id.project_title_bar);
        mViewPage = (ViewPager) findViewById(R.id.view_page);
    }

    @Override
    protected void initData() {
        List<BaseFragment> supervisionFragments = new ArrayList<>();
        supervisionFragments.add(SafeSupervisionFragment.newInstance(0));
        supervisionFragments.add(SafeSupervisionFragment.newInstance(1));
        supervisionFragments.add(SafeSupervisionFragment.newInstance(2));

        ZtFragmentAdapter adapter = new ZtFragmentAdapter(getSupportFragmentManager(), supervisionFragments);
        mViewPage.setAdapter(adapter);
        mProjectTitleBar.initTitle(mViewPage, "全部", "未完结", "已完结");
    }
}
