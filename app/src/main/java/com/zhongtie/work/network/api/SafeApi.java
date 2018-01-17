package com.zhongtie.work.network.api;


import android.support.v4.util.ArrayMap;

import com.zhongtie.work.data.Result;
import com.zhongtie.work.db.SafeSupervisionEntity;
import com.zhongtie.work.model.EventCountData;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Auth: Chaek
 * Date: 2018/1/16
 */

public interface SafeApi {

    /**
     * 获取当前公司以及用户的未上传的接口
     */
    @FormUrlEncoded
    @POST("?action=GetEventListMonthCount")
    Flowable<Result<List<EventCountData>>> safeEventListMonthCount(@Field("userid") String userId, @Field("companyid") int companyid);

    /**
     * 安全督导列表
     *
     * @param userId
     * @param companyid
     * @return
     */
    @FormUrlEncoded
    @POST("?action=GetEventList")
    Flowable<Result<List<SafeSupervisionEntity>>> safeEventList(@Field("userid") String userId, @Field("companyid") int companyid, @Field("time") String time, @Field("state") int state);

    /**
     * 创建安全督导
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("?action=EditEvent")
    Flowable<Result<Integer>> createEventList(@FieldMap ArrayMap<String, Object> data);

    /**
     * 回复
     *
     * @param data 回复内容
     * @return 回复编号
     */
    @FormUrlEncoded
    @POST("?action=ReplyEvent")
    Flowable<Result<Integer>> replyEvent(@FieldMap ArrayMap<String, Object> data);


}
