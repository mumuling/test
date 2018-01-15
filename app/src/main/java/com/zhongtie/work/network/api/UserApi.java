package com.zhongtie.work.network.api;

import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.data.Result;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Auth:Cheek
 * date:2018.1.8
 */

public interface UserApi {

    /**
     * 登录
     *
     * @param phone 手机
     * @param pw    密码
     */
    @FormUrlEncoded
    @POST("?action=login")
    Flowable<Result<String>> login(@Field("username") String phone, @Field("password") String pw);

    /**
     * 获取用户基本信息
     *
     * @param userid 用户ID
     * @return 基本信息
     */
    @FormUrlEncoded
    @POST("?action=userinfo")
    Flowable<Result<LoginUserInfoEntity>> userInfo(@Field("userid") String userid);

    /**
     * 获取公司名称
     */
    @FormUrlEncoded
    @POST("?action=CompanyList")
    Flowable<Result<List<CompanyEntity>>> fetchCompanyList(@Field("userId") int userId);


    /**
     * 修改密码
     *
     * @param userid      用户ID
     * @param newPassword 新密码
     */
    @FormUrlEncoded
    @POST("?action=CompanyList")
    Flowable<Result<String>> modifyPassword(@Field("userid") String userid, @Field("password") String newPassword);

    /**
     * 添加违规情况
     *
     * @param wronguserid 违规用户
     * @param wrongByid   记录人
     * @param detail      违规内容
     * @return
     */
    @FormUrlEncoded
    @POST("?action=AddWrong")
    Flowable<Result<String>> addWrong(@Field("wronguserid") String wronguserid, @Field("wrongByid") String wrongByid, @Field("detail") String detail);


}
