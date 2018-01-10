package com.zhongtie.work.ui.statistics;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
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
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.util.ListPopupWindowUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/10
 */

public class StatisticsFragment extends BaseFragment implements OnChartValueSelectedListener {

    private RelativeLayout mStatisticsYearView;
    private TextView mStatisticsYear;
    private RelativeLayout mStatisticsQuarterView;
    private TextView mStatisticsQuarter;
    private PieChart mSafeRatioChart;

    private ChartLineView mCompanyWorkLine;
    private ChartLineView mCompanyBelongLine;


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
        mStatisticsYearView.setOnClickListener(view -> showSelectYearPopup());
        mStatisticsQuarterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectQuarterPopup();
            }
        });

    }

    private void showSelectQuarterPopup() {
        ListPopupWindowUtil.showListPopupWindow(mStatisticsQuarterView, Gravity.BOTTOM, new String[]{"第一季度", "第二季度", "第三季度","第四季度","全部"}, new OnListPopupListener() {
            @Override
            public void onItemClick(String item, int position) {

            }
        });
    }

    private void showSelectYearPopup() {
        ListPopupWindowUtil.showListPopupWindow(mStatisticsYearView, Gravity.BOTTOM, new String[]{"2016年", "2017年", "2018年"}, new OnListPopupListener() {
            @Override
            public void onItemClick(String item, int position) {

            }
        });

    }

    @Override
    protected void initData() {
        initChart();
        initCompany();
    }

    private void initCompany() {

        List<StatisticsLineData> statisticsLineData = new ArrayList<>();

        statisticsLineData.add(new StatisticsLineData(0.2f, "xxx公司", 12));
        statisticsLineData.add(new StatisticsLineData(0.3f, "xxx公司", 12));
        statisticsLineData.add(new StatisticsLineData(0.4f, "xxx公司", 12));
        statisticsLineData.add(new StatisticsLineData(0.1f, "xxx公司", 12));
        statisticsLineData.add(new StatisticsLineData(0.53f, "xxx公司", 12));
        statisticsLineData.add(new StatisticsLineData(0.23f, "xxx公司", 12));
        statisticsLineData.add(new StatisticsLineData(0.43f, "xxx公司", 12));
        statisticsLineData.add(new StatisticsLineData(0.54f, "xxx公司", 12));
        statisticsLineData.add(new StatisticsLineData(0.23f, "xxx公司xxx公司xxx公司", 12));
        mCompanyWorkLine.setStatisticsLineDataList(statisticsLineData);
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

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(12, "消防管理"));
        yvalues.add(new PieEntry(13, "操作流程"));
        yvalues.add(new PieEntry(15, "文明施工"));
        yvalues.add(new PieEntry(16, "文明施工2"));


        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.parseColor("#FF9A1A5B"));
        colors.add(Color.parseColor("#FF2684C4"));
        colors.add(Color.parseColor("#FF13A89C"));
        colors.add(Color.parseColor("#FF38B54A"));
        colors.add(Color.parseColor("#FFF9B03E"));
        colors.add(Color.parseColor("#FFEE3E34"));
        colors.add(Color.parseColor("#FF9A1A5B"));

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);
        dataSet.setSliceSpace(0f);
        PieData data = new PieData();
        data.setDataSet(dataSet);

        data.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> getString(R.string.station_format, entry.getY(), value));
        data.setHighlightEnabled(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.parseColor("#ffffff"));
        mSafeRatioChart.setData(data);

        mSafeRatioChart.getLegend().setEnabled(false);

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
