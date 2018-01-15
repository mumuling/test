package com.zhongtie.work.network.api;

import com.zhongtie.work.data.Result;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Auth: Chaek
 * Date: 2018/1/15
 */

public interface SyncApi {
    /**
     * 获取下载地址
     */
    @FormUrlEncoded
    @POST("?action=login")
    Flowable<Result<String>> login(@Field("userId") String userId, @Field("password") String pw);
}
