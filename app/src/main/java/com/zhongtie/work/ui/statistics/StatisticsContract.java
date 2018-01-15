package com.zhongtie.work.ui.statistics;

import com.github.mikephil.charting.data.PieEntry;
import com.zhongtie.work.data.StatisticsLineData;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public interface StatisticsContract {

    interface View extends BaseView {

        void setWorkTeam(List<StatisticsLineData> statisticsData);

        void setCompany(List<StatisticsLineData> statisticsData);

        void setSafeList(List<PieEntry> statisticsData);
    }

    interface Presenter extends BasePresenter<View> {

        void fetchInitData();

        void fetchYearData(String year, int position);


    }
}
