package com.zhongtie.work.network.api;

import com.zhongtie.work.data.ChartData;
import com.zhongtie.work.data.StatisticsData;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public interface StatisticsApi {

    /**
     * 劳务公司统计
     */
    @FormUrlEncoded
    @POST("?action=SafetyWorkerteamStatistics")
    Flowable<StatisticsData> SafetyWorkerteamStatistics(@Field("companyId") int companyId, @Field("year") String year);

    /**
     * 单位统计
     *
     * @param companyId
     * @param year
     * @return
     */
    @FormUrlEncoded
    @POST("?action=SafetyWorkerteamStatistics")
    Flowable<StatisticsData> SafetyUnitStatistics(@Field("companyId") int companyId, @Field("year") String year);

    /**
     * 问题类型统计
     *
     * @param companyId
     * @param year
     * @return
     */
    @FormUrlEncoded
    @POST("?action=SafetyStatistics")
    Flowable<ChartData> SafetyStatistics(@Field("companyId") int companyId, @Field("year") String year);

}
