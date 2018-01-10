package com.zhongtie.work.ui.statistics;

import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.util.ViewUtils;

import java.util.ArrayList;

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
    private HorizontalBarChart mCompanyWorkLine;
    private LineChart mCompanyBelongLine;


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
        mCompanyWorkLine = (HorizontalBarChart) findViewById(R.id.company_work_line);
        mCompanyBelongLine = (LineChart) findViewById(R.id.company_belong_line);


    }

    @Override
    protected void initData() {
        initChart();

        initLineChart();


    }

    private void initLineChart() {
        mCompanyWorkLine.setDrawBarShadow(false);
        mCompanyWorkLine.setDrawValueAboveBar(true);
        mCompanyWorkLine.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mCompanyWorkLine.setMaxVisibleValueCount(100);

        // scaling can now only be done on x- and y-axis separately
        mCompanyWorkLine.setPinchZoom(true);

        // draw shadows for each bar that show the maximum value
        // mCompanyWorkLine.setDrawBarShadow(true);

        mCompanyWorkLine.setDrawGridBackground(false);

        XAxis xl = mCompanyWorkLine.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = mCompanyWorkLine.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mCompanyWorkLine.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        setData(5, 100);
        mCompanyWorkLine.setFitBars(true);
        mCompanyWorkLine.animateY(2500);
 

        Legend l = mCompanyWorkLine.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }
    private void setData(int count, float range) {

        float barWidth = 1f;
        float spaceForBar = 3f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yVals1.add(new BarEntry(i * spaceForBar, val));
        }

        BarDataSet set1;

        if (mCompanyWorkLine.getData() != null &&
                mCompanyWorkLine.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mCompanyWorkLine.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mCompanyWorkLine.getData().notifyDataChanged();
            mCompanyWorkLine.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet 1");
            set1.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(barWidth);
            mCompanyWorkLine.setData(data);
        }
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
