package com.zhongtie.work.network.api;

import android.support.v4.util.ArrayMap;

import com.zhongtie.work.data.Result;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.data.RewardPunishEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.SupervisorInfoEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 安全处罚Api接口列表
 *
 * @author: Cheek
 * @date: 2018.1.29
 */
public interface RewardPunishApi {


    /**
     * 安全处罚列表 我发起的
     *
     * @param userId    登录用户ID
     * @param companyid 公司ID
     * @param time      选择时间月份
     * @return 列表参数
     */
    @FormUrlEncoded
    @POST("?action=GetEventList")
    Flowable<Result<List<RewardPunishEntity>>> punishMeCreateList(@Field("userid") String userId, @Field("companyid") int companyid, @Field("time") String time);

    /**
     * 安全处罚列表 我可查看的
     *
     * @param userId    登录用户ID
     * @param companyid 公司ID
     * @param time      选择时间月份
     * @return 列表参数
     */
    @FormUrlEncoded
    @POST("?action=GetEventList")
    Flowable<Result<List<RewardPunishEntity>>> punishMeReadList(@Field("userid") String userId, @Field("companyid") int companyid, @Field("time") String time);

    /**
     * 创建安全处罚
     *
     * @param data 参数
     * @return 返回创建ID
     */
    @FormUrlEncoded
    @POST("?action=EditEvent")
    Flowable<Result<Integer>> createPunishEvent(@FieldMap ArrayMap<String, Object> data);


    /**
     * 安全处罚详情
     *
     * @param userId  用户ID
     * @param eventid ID
     * @return 返回详情
     */
    @FormUrlEncoded
    @POST("?action=EventDetails")
    Flowable<Result<RewardPunishDetailEntity>> punishDetails(@Field("userid") String userId, @Field("eventid") int eventid);

    /**
     * 安全处罚签认操作
     *
     * @param userId  用户ID
     * @param eventid ID
     * @return 返回详情
     */
    @FormUrlEncoded
    @POST("?action=EventDetails")
    Flowable<Result<RewardPunishDetailEntity>> signPunish(@Field("userid") String userId, @Field("eventid") int eventid, @Field("img") String img);

    /**
     * 安全处罚作废
     *
     * @param userId  用户ID
     * @param eventid ID
     * @return 返回详情
     */
    @FormUrlEncoded
    @POST("?action=EventDetails")
    Flowable<Result<RewardPunishDetailEntity>> cancelPunish(@Field("userid") String userId, @Field("eventid") int eventid, @Field("img") String img);

    /**
     * 同意
     *
     * @param userId
     * @param eventid
     * @param img
     * @return
     */
    @FormUrlEncoded
    @POST("?action=EventDetails")
    Flowable<Result<RewardPunishDetailEntity>> agreePunish(@Field("userid") String userId, @Field("eventid") int eventid, @Field("img") String img);

    /**
     * 退回操作
     *
     * @param userId
     * @param eventid
     * @param img
     * @return
     */
    @FormUrlEncoded
    @POST("?action=EventDetails")
    Flowable<Result<RewardPunishDetailEntity>> returnPunish(@Field("userid") String userId, @Field("eventid") int eventid, @Field("img") String img);
}
