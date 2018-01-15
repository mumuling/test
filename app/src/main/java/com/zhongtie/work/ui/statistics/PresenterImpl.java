package com.zhongtie.work.ui.statistics;

import android.support.annotation.NonNull;

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
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_COMPANY;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public class PresenterImpl extends BasePresenterImpl<StatisticsContract.View> implements StatisticsContract.Presenter {
    private StatisticsData workTeam;
    private StatisticsData company;
    private ChartData mCharData;

    private String year;
    private String[] title = {"安全防护", "文明施工", "操作规程", "临时用电", "消防管理", "高处作业", "安全管理", "其他"};

    @Override
    public void fetchInitData() {
        String year = TimeUtils.getCurrentYear();
        int position = 1;
        fetchYearData(year, position);
        this.year = year;
//        setver.SafetyWorkerteamStatistics(company, year)
//                .map(statisticsData -> getStatisticsLineData(position, statisticsData))
//                .compose(Network.netorkIO())
//                .subscribe(statisticsData -> {
//                    mView.setWorkTeam(statisticsData);
//                });
//        setver.SafetyUnitStatistics(company, year)
//                .map(statisticsData -> getStatisticsLineData(position, statisticsData))
//                .compose(Network.netorkIO())
//                .subscribe(statisticsData -> {
//                    mView.setCompany(statisticsData);
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });
//
//        setver.SafetyStatistics(company, year)
//                .map(chartData -> {
//                    mCharData = chartData;
//                    return chartData.getPart(position).all;
//                })
//                .map(strings -> {
//                    List<PieEntry> pieEntries = new ArrayList<>();
//                    for (int i = 0; i < strings.size(); i++) {
//                        PieEntry pieEntry = new PieEntry(Integer.valueOf(strings.get(i)), title[i]);
//                        pieEntries.add(pieEntry);
//                    }
//                    return pieEntries;
//                }).compose(Network.netorkIO())
//                .subscribe(pieEntries -> mView.setSafeList(pieEntries), throwable -> {
//                });

    }

    @Override
    public void fetchYearData(String year, int position) {
        StatisticsApi setver = Http.netSetver(StatisticsApi.class);
        int company = SharePrefUtil.getUserPre().getInt(LOGIN_USER_COMPANY, 0);

        setver.SafetyWorkerteamStatistics(company, year)
                .map(statisticsData -> getStatisticsLineData(position, statisticsData))
                .compose(Network.netorkIO())
                .subscribe(statisticsData -> {
                    mView.setWorkTeam(statisticsData);
                });
        setver.SafetyUnitStatistics(company, year)
                .map(statisticsData -> getStatisticsLineData(position, statisticsData))
                .compose(Network.netorkIO())
                .subscribe(statisticsData -> {
                    mView.setCompany(statisticsData);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        setver.SafetyStatistics(company, year)
                .map(chartData -> {
                    mCharData = chartData;
                    return chartData.getPart(position).all;
                })
                .map(strings -> {
                    List<PieEntry> pieEntries = new ArrayList<>();
                    for (int i = 0; i < strings.size(); i++) {
                        int l = Integer.valueOf(strings.get(i));
                        if (l <= 0)
                            continue;
                        PieEntry pieEntry = new PieEntry(l, title[i]);
                        pieEntries.add(pieEntry);
                    }
                    return pieEntries;
                }).compose(Network.netorkIO())
                .subscribe(pieEntries -> mView.setSafeList(pieEntries), throwable -> {
                });

    }

    @NonNull
    private List<StatisticsLineData> getStatisticsLineData(int position, StatisticsData statisticsData) {
        workTeam = statisticsData;
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
            lineDat.setTotal(itemCount);
            lineDat.setCompany(statisticsData.names.get(i));
            lineDat.setPercent((float) itemCount / (float) count);
            statisticsLineData.add(lineDat);
        }
        return statisticsLineData;
    }

}
