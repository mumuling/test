package com.zhongtie.work.network.api;

import com.zhongtie.work.data.Result;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Date: 2018/1/17
 */
public interface SyncApi {

    /**
     * 获取本公司所有在职头像地址并下载到本地
     */
    @FormUrlEncoded
    @POST("?action=GetHeadPortraitUrl")
    Flowable<Result<List<String>>> syncHeadPortraitUrl(@Field("companyid") int companyid);

    /**
     * 获取打印地址
     *
     * @param type 类型
     * @param id   事件ID
     * @return 返回URL地址
     */
    @FormUrlEncoded
    @POST("?action=GetPrintUrl")
    Flowable<Result<String>> getPrintUrl(@Field("type") int type, @Field("id") int id);

}
