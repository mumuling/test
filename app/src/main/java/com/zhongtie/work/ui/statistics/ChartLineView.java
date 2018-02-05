package com.zhongtie.work.ui.statistics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.StatisticsLineData;
import com.zhongtie.work.util.ResourcesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.10
 */

public class ChartLineView extends LinearLayout {
    private RecyclerView mList;

    private View mChartHeadView;
    private View mFooterView;
    private List<StatisticsLineData> statisticsLineDataList = new ArrayList<>();

    private CommonAdapter commonAdapter;
    private View mEmptyView;
    private int mLineColor;


    public ChartLineView(Context context) {
        super(context);
    }

    public ChartLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ChartLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mLineColor = getResources().getColor(R.color.app_color);
        setOrientation(VERTICAL);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.base_recyclerview, this, true);
        mList = v.findViewById(R.id.list);
        mList.setNestedScrollingEnabled(false);
        mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.placeholder_empty_view, this, false);
        addView(mEmptyView);
        mEmptyView.getLayoutParams().height = ResourcesUtils.dip2px(200);
        mEmptyView.setVisibility(VISIBLE);
        mList.setVisibility(GONE);
        mChartHeadView = LayoutInflater.from(getContext()).inflate(R.layout.statistics_head_view, this, false);
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.statistics_bottom_view, this, false);

    }

    public void setStatisticsLineDataList(List<StatisticsLineData> statisticsLineDataList) {
        this.statisticsLineDataList.clear();
        this.statisticsLineDataList.addAll(statisticsLineDataList);
        if (commonAdapter == null) {
            ChartLineItemView lineItemView = new ChartLineItemView(mLineColor);
            commonAdapter = new CommonAdapter(this.statisticsLineDataList).register(lineItemView);
            commonAdapter.addFooterView(mFooterView);
            commonAdapter.addHeaderView(mChartHeadView);
            mList.setAdapter(commonAdapter);
        }
        commonAdapter.notifyDataSetChanged();
        if (this.statisticsLineDataList.isEmpty()) {
            mEmptyView.setVisibility(VISIBLE);
            mList.setVisibility(GONE);
        } else {
            mEmptyView.setVisibility(GONE);
            mList.setVisibility(VISIBLE);
        }
        requestLayout();
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
    }


    @BindItemData(StatisticsLineData.class)
    public static class ChartLineItemView extends AbstractItemView<StatisticsLineData, ChartLineItemView.ViewHolder> {
        int width;

        private int lineColor;

        public ChartLineItemView() {
        }

        public ChartLineItemView(int lineColor) {
            this.lineColor = lineColor;
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.statistics_item_view;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull StatisticsLineData data) {
            vh.mChartPercent.setText(vh.mContext.getString(R.string.station_line_format, data.getTotal(), data.getPercent() * 100));
            vh.mChartTitle.setText(data.getCompany());
            if (width == 0) {
                width = ResourcesUtils.getScreenWidth(vh.mContext) - ResourcesUtils.dip2px(80);
            }

            vh.mChartView.getLayoutParams().width = (int) (width * data.getPercent());
            vh.mChartView.requestLayout();
        }


        @Override
        public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
            return new ViewHolder(view);
        }

        public class ViewHolder extends CommonViewHolder {
            private TextView mChartPercent;
            private View mChartView;
            private TextView mChartTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                mChartPercent = findViewById(R.id.chart_percent);
                mChartView = findViewById(R.id.chart_view);
                mChartTitle = findViewById(R.id.chart_title);
                mChartView.setBackgroundColor(lineColor);
            }
        }
    }
}
