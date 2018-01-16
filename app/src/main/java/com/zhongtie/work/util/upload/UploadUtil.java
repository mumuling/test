package com.zhongtie.work.util.upload;

import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.Result;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.api.UploadApi;

import io.reactivex.Flowable;

/**
 * Auth: Chaek
 * Date: 2018/1/16
 */

public class UploadUtil {

    /**
     * 上传JPG图片 发布任务
     *
     * @param filePath 图片地址
     * @return 返回上传成功的图片信息
     */
    public static Flowable<UploadData> uploadJPG(String filePath) {
        return Http.netServer(UploadApi.class)
                .uploadPic(Cache.getUserID(), filePath)
                .map(new NetWorkFunc1<>());
    }

    /**
     * 上传JPG图片 发布任务
     *
     * @param filePath 图片地址
     * @return 返回上传成功的图片信息
     */
    public static Flowable<UploadData> uploadSignPNG(String filePath) {
        return Http.netServer(UploadApi.class)
                .uploadSignPng(Cache.getUserID(), filePath)
                .map(new NetWorkFunc1<>());
    }

}
