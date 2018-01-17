package com.zhongtie.work.ui.statistics;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhongtie.work.R;
import com.zhongtie.work.data.StatisticsLineData;
import com.zhongtie.work.list.OnListPopupListener;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.util.ListPopupWindowUtil;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/10
 */

public class StatisticsFragment extends BasePresenterFragment<StatisticsContract.Presenter> implements OnChartValueSelectedListener, StatisticsContract.View {

    private static final int YEAR_COUNT = 4;
    private RelativeLayout mStatisticsYearView;
    private TextView mStatisticsYear;
    private RelativeLayout mStatisticsQuarterView;
    private TextView mStatisticsQuarter;
    private MultipleStatusView mStatusView;
    private TextView mSafeRatioTitle;
    private PieChart mSafeRatioChart;
    private LinearLayout mStatisticsWorkTeamLayout;
    private ChartLineView mCompanyWorkLine;
    private LinearLayout mStatisticsCompanyLayout;
    private ChartLineView mCompanyBelongLine;


    private String mSelectYear = TimeUtils.getCurrentYear();
    private int mPosition = 4;

    @Override
    public void onClickRefresh() {
        super.onClickRefresh();
        mPresenter.fetchYearData(mSelectYear, mPosition);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.statistics_fragment;
    }

    @Override
    public void initView() {
        mStatisticsYearView = (RelativeLayout) findViewById(R.id.statistics_year_view);
        mStatisticsYear = (TextView) findViewById(R.id.statistics_year);
        mStatisticsQuarterView = (RelativeLayout) findViewById(R.id.statistics_quarter_view);
        mStatisticsQuarter = (TextView) findViewById(R.id.statistics_quarter);
        mSafeRatioChart = (PieChart) findViewById(R.id.safe_ratio_chart);
        mCompanyWorkLine = (ChartLineView) findViewById(R.id.company_work_line);
        mCompanyBelongLine = (ChartLineView) findViewById(R.id.company_belong_line);
        mCompanyBelongLine.setLineColor(getResources().getColor(R.color.wechat_login_btn_start_color));
        mStatisticsYearView = (RelativeLayout) findViewById(R.id.statistics_year_view);
        mStatisticsYear = (TextView) findViewById(R.id.statistics_year);
        mStatisticsQuarterView = (RelativeLayout) findViewById(R.id.statistics_quarter_view);
        mStatisticsQuarter = (TextView) findViewById(R.id.statistics_quarter);
        mStatusView = (MultipleStatusView) findViewById(R.id.status_view);
        mSafeRatioTitle = (TextView) findViewById(R.id.safe_ratio_title);
        mSafeRatioChart = (PieChart) findViewById(R.id.safe_ratio_chart);
        mStatisticsWorkTeamLayout = (LinearLayout) findViewById(R.id.statistics_work_team_layout);
        mCompanyWorkLine = (ChartLineView) findViewById(R.id.company_work_line);
        mStatisticsCompanyLayout = (LinearLayout) findViewById(R.id.statistics_company_layout);
        mCompanyBelongLine = (ChartLineView) findViewById(R.id.company_belong_line);


        mStatisticsYearView.setOnClickListener(view -> showSelectYearPopup());
        mStatisticsQuarterView.setOnClickListener(view -> showSelectQuarterPopup());

        mStatisticsYear.setText(mSelectYear);
        mStatisticsYear.append("年");

        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String[] name = getResources().getStringArray(R.array.year_position);
        mPosition = month / 3 ;
        mStatisticsQuarter.setText(name[mPosition]);
    }

    private void showSelectQuarterPopup() {
        ListPopupWindowUtil.showListPopupWindow(mStatisticsQuarterView, Gravity.BOTTOM, getResources().getStringArray(R.array.year_position), (item, position) -> {
            mStatisticsQuarter.setText(item);
            mPosition = position;
            mPresenter.fetchYearData(mSelectYear, mPosition);
        });
    }

    private void showSelectYearPopup() {
        String[] year = new String[YEAR_COUNT];
        int nowYear = Integer.valueOf(TimeUtils.getCurrentYear());
        for (int i = 0; i < YEAR_COUNT; i++) {
            year[i] = nowYear - (YEAR_COUNT - 1 - i) + "年";
        }

        ListPopupWindowUtil.showListPopupWindow(mStatisticsYearView, Gravity.BOTTOM, year, (item, position) -> {
            mStatisticsYear.setText(item);
            mSelectYear = item.replace("年", "");
            mPresenter.fetchYearData(mSelectYear, mPosition);
        });

    }


    @Override
    protected void initData() {
        initChart();
        initCompany();
        mPresenter.fetchInitData();
    }

    private void initCompany() {

    }


    /**
     * 初始化饼状图绘制
     */
    private void initChart() {

        mSafeRatioChart.setDrawEntryLabels(true);
        mSafeRatioChart.setUsePercentValues(true);
        mSafeRatioChart.setRotationEnabled(false);
        Description description = new Description();
        description.setText("");
        mSafeRatioChart.setDescription(description);
        mSafeRatioChart.setNoDataText("暂无数据");
        mSafeRatioChart.setDrawHoleEnabled(false);

        mSafeRatioChart.setEntryLabelColor(getResources().getColor(R.color.white));
        mSafeRatioChart.setEntryLabelTextSize(14f);


    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    protected StatisticsContract.Presenter getPresenter() {
        return new PresenterImpl();
    }

    @Override
    public void setWorkTeam(List<StatisticsLineData> statisticsData) {
        if (statisticsData.isEmpty()) {
            mStatisticsWorkTeamLayout.setVisibility(View.GONE);
        } else {
            mStatisticsWorkTeamLayout.setVisibility(View.VISIBLE);
        }
        mCompanyBelongLine.setStatisticsLineDataList(statisticsData);
    }

    @Override
    public void setCompany(List<StatisticsLineData> statisticsData) {
        if (statisticsData.isEmpty()) {
            mStatisticsCompanyLayout.setVisibility(View.GONE);
        } else {
            mStatisticsCompanyLayout.setVisibility(View.VISIBLE);
        }
        mCompanyWorkLine.setStatisticsLineDataList(statisticsData);
    }

    @Override
    public void setSafeList(List<PieEntry> statisticsData) {
        mSafeRatioChart.clear();
        if (statisticsData.isEmpty()) {
            mSafeRatioTitle.setVisibility(View.GONE);
            mSafeRatioChart.setVisibility(View.GONE);
        } else {
            mSafeRatioChart.setVisibility(View.VISIBLE);
            mSafeRatioTitle.setVisibility(View.VISIBLE);
            PieDataSet dataSet = new PieDataSet(statisticsData, "Election Results");
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.parseColor("#FF9A1A5B"));
            colors.add(Color.parseColor("#FF2684C4"));
            colors.add(Color.parseColor("#FF13A89C"));
            colors.add(Color.parseColor("#FF38B54A"));
            colors.add(Color.parseColor("#FFF9B03E"));
            colors.add(Color.parseColor("#FFEE3E34"));
            colors.add(Color.parseColor("#FF9A1A5B"));

            for (int c : ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(c);
            }

            for (int c : ColorTemplate.JOYFUL_COLORS) {
                colors.add(c);
            }

            for (int c : ColorTemplate.COLORFUL_COLORS) {
                colors.add(c);
            }

            for (int c : ColorTemplate.LIBERTY_COLORS) {
                colors.add(c);
            }

            for (int c : ColorTemplate.PASTEL_COLORS) {
                colors.add(c);
            }

            dataSet.setColors(colors);
            dataSet.setSliceSpace(0f);
            PieData data = new PieData();
            data.setDataSet(dataSet);
            data.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> getString(R.string.station_format, entry.getY(), value));
            data.setHighlightEnabled(true);
            data.setValueTextSize(12f);
            data.setValueTextColor(Color.parseColor("#ffffff"));
            mSafeRatioChart.setData(data);
            mSafeRatioChart.animateX(1000);
            mSafeRatioChart.getLegend().setEnabled(false);
        }

    }

    @Override
    public void showNoData() {
        mStatusView.showEmpty();
    }

    @Override
    public void showContent() {
        mStatusView.showContent();
    }
}
