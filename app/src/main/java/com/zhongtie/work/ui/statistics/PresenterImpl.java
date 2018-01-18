package com.zhongtie.work.ui.statistics;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.github.mikephil.charting.data.PieEntry;
import com.zhongtie.work.data.StatisticsLineData;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.StatisticsApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Flowable;

import static com.zhongtie.work.ui.login.LoginPresenter.SELECT_COMPANY_ID;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public class PresenterImpl extends BasePresenterImpl<StatisticsContract.View> implements StatisticsContract.Presenter {

    private String[] title = {"安全防护", "文明施工", "操作规程", "临时用电", "消防管理", "高处作业", "安全管理", "其他"};

    public ArrayMap<String, Object[]> mCacheData;

    @Override
    public void fetchInitData() {
        mCacheData = new ArrayMap<>();
        String year = TimeUtils.getCurrentYear();
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int position = month / 3;
        fetchYearData(year, position);
    }

    @Override
    public void fetchYearData(String year, int position) {

        StatisticsApi netServer = Http.netServer(StatisticsApi.class);
        int company = SharePrefUtil.getUserPre().getInt(SELECT_COMPANY_ID, 0);
        Object[] cacheList = mCacheData.get(year);
        if (cacheList == null) {
            mView.initLoading();
            Object[] temp = new Object[3];
            //劳务公司信息
            Flowable<List<StatisticsLineData>> workTeamObservable = netServer.SafetyWorkerteamStatistics(company, year)
                    .map(statisticsData -> {
                        temp[0] = statisticsData;
                        return getStatisticsLineData(position, statisticsData);
                    });
            //所属单位信息
            Flowable<List<StatisticsLineData>> companyObservable = netServer.SafetyUnitStatistics(company, year)
                    .map(statisticsData -> {
                        temp[1] = statisticsData;
                        return getStatisticsLineData(position, statisticsData);
                    });
            //统计信息
            Flowable<List<PieEntry>> statisticsObservable = netServer.SafetyStatistics(company, year)
                    .map(chartData -> {
                        temp[2] = chartData;
                        return getPieEntries(position, chartData);
                    });
            Flowable.zip(workTeamObservable, companyObservable, statisticsObservable, (statisticsLineData, statisticsLineData2, pieEntries) ->
                    new Object[]{statisticsLineData, statisticsLineData2, pieEntries})
                    .compose(Network.networkIO())
                    .subscribe(o -> {
                        mCacheData.put(year, temp);
                        setStatisticsData(o);
                    }, throwable -> mView.initFail());
        } else {
            List<StatisticsLineData> workTeamStatistics = getStatisticsLineData(position, (StatisticsData) cacheList[0]);
            List<StatisticsLineData> companyStatistics = getStatisticsLineData(position, (StatisticsData) cacheList[1]);
            List<PieEntry> mPieList = getPieEntries(position, (ChartData) cacheList[2]);
            setStatisticsData(new Object[]{workTeamStatistics, companyStatistics, mPieList});
        }
    }

    private void setStatisticsData(Object[] cacheList) {
        List<StatisticsLineData> w = (List<StatisticsLineData>) cacheList[0];
        List<StatisticsLineData> c = (List<StatisticsLineData>) cacheList[1];
        List<PieEntry> p = (List<PieEntry>) cacheList[2];
        if (w.isEmpty() && c.isEmpty() && p.isEmpty()) {
            mView.showNoData();
        } else {
            mView.showContent();
            mView.setWorkTeam(w);
            mView.setCompany(c);
            mView.setSafeList(p);
        }
    }

    @NonNull
    private List<PieEntry> getPieEntries(int position, ChartData chartData) {
        List<String> strings = chartData.getPart(position).all;
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            int l = Integer.valueOf(strings.get(i));
            if (l <= 0) {
                continue;
            }
            PieEntry pieEntry = new PieEntry(l, title[i]);
            pieEntries.add(pieEntry);
        }
        return pieEntries;
    }

    @NonNull
    private List<StatisticsLineData> getStatisticsLineData(int position, StatisticsData statisticsData) {
        List<StatisticsLineData> statisticsLineData = new ArrayList<>();
        StatisticsData.ValuesBean valuesBean = statisticsData.values.get(position);
        String[] len = valuesBean.data.split(",");
        int count = 0;
        for (String aLen : len) {
            if (TextUtil.isEmpty(aLen))
                continue;
            int v = Integer.valueOf(aLen.trim());
            count += v;
        }
        if (count == 0) {
            count = 1;
        }
        for (int i = 0; i < statisticsData.names.size(); i++) {
            StatisticsLineData lineDat = new StatisticsLineData();
            int itemCount = Integer.valueOf(len[i].trim());
            if (itemCount <= 0) {
                continue;
            }
            lineDat.setTotal(itemCount);
            lineDat.setCompany(statisticsData.names.get(i));
            lineDat.setPercent((float) itemCount / (float) count);
            statisticsLineData.add(lineDat);
        }
        return statisticsLineData;
    }

}
