package com.zhongtie.work.network.api;

import android.support.annotation.StringRes;
import android.support.v4.util.ArrayMap;

import com.zhongtie.work.data.Result;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.data.RewardPunishEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.SelectSafeEventList;
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
     * @return 列表参数
     */
    @FormUrlEncoded
    @POST("?action=GetTaxListByMy")
    Flowable<Result<List<RewardPunishEntity>>> punishMeCreateList(@Field("userid") String userId, @Field("companyid") int companyid, @Field("year") String year, @Field("month") String month);

    /**
     * 安全处罚列表 我可查看的
     *
     * @param userId    登录用户ID
     * @param companyid 公司ID
     * @return 列表参数
     */
    @FormUrlEncoded
    @POST("?action=GetTaxListByReader")
    Flowable<Result<List<RewardPunishEntity>>> punishMeReadList(@Field("userid") String userId, @Field("companyid") int companyid, @Field("year") String year, @Field("month") String month);

    /**
     * 创建安全处罚
     *
     * @param data 参数
     * @return 返回创建ID
     */
    @FormUrlEncoded
    @POST("?action=AddTax")
    Flowable<Result<Integer>> createPunishEvent(@FieldMap ArrayMap<String, Object> data);


    /**
     * 安全处罚详情
     *
     * @param userId  用户ID
     * @param taxid ID
     * @return 返回详情
     */
    @FormUrlEncoded
    @POST("?action=GetTaxDetails")
    Flowable<Result<RewardPunishDetailEntity>> punishDetails(@Field("userid") String userId, @Field("taxid") int taxid);

    /**
     * 获取安全督导列表
     * @param userId
     * @param companyid
     * @return
     */
    @FormUrlEncoded
    @POST("?action=GetEventListTwoMonths")
    Flowable<Result<List<SelectSafeEventList>>> getPunisnSafeEvent(@Field("userid") String userId, @Field("companyid") int companyid);

    /**
     * 安全处罚签认操作
     *
     * @param userId  用户ID
     * @param eventid ID
     * @return 返回详情
     */
    @FormUrlEncoded
    @POST("?action=TaxSign")
    Flowable<Result<Integer>> signPunish(@Field("userid") String userId, @Field("taxid") int eventid, @Field("signurl") String img);

    /**
     * 安全处罚作废
     *
     * @param userId  用户ID
     * @param eventid ID
     * @return 返回详情
     */
    @FormUrlEncoded
    @POST("?action=TaxCancel")
    Flowable<Result<RewardPunishDetailEntity>> cancelPunish(@Field("userid") String userId, @Field("taxid") int eventid );

    /**
     * 同意
     *
     * @param userId 操作用户ID
     * @param taxid  用户ID
     * @param img    签名地址
     * @return
     */
    @FormUrlEncoded
    @POST("?action=TaxAgree")
    Flowable<Result<RewardPunishDetailEntity>> consentPunish(@Field("userid") String userId, @Field("taxid") int taxid, @Field("signurl") String img);

    /**
     * 退回操作
     *
     * @param userId
     * @param eventid
     * @param img
     * @return
     */
    @FormUrlEncoded
    @POST("?action=TaxReturn")
    Flowable<Result<RewardPunishDetailEntity>> sendBackPunish(@Field("userid") String userId, @Field("taxid") int eventid, @Field("signurl") String img, @Field("reason") String content);
}
