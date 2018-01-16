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

public interface UploadApi {
    /**
     * 获取下载地址
     */
    @FormUrlEncoded
    @POST("?action=UploadPic")
    Flowable<Result<String>> uploadPic(@Field("userid") String userId, @Field("picfile") String picFile);

}
