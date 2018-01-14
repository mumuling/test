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

public interface Api {

    /**
     * 登录
     *
     * @param phone 手机
     * @param pw    密码
     */
    @FormUrlEncoded
    @POST("?action=login")
    Flowable<Result<String>> login(@Field("username") String phone, @Field("password") String pw);

    @FormUrlEncoded
    @POST("?action=userinfo")
    Flowable<Result<LoginUserInfoEntity>> userInfo(@Field("userid") String userid);

    /**
     * 获取公司名称
     */
    @FormUrlEncoded
    @POST("?action=CompanyList")
    Flowable<Result<List<CompanyEntity>>> fetchCompanyList(@Field("userId") int userId);

}
