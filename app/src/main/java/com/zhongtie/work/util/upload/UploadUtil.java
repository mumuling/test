package com.zhongtie.work.util.upload;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.UploadData;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.api.UploadApi;

import java.util.List;

import io.reactivex.Flowable;

/**
 * 图片上传
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
    @WorkerThread
    public static Flowable<UploadData> uploadJPG(String filePath) {
        return Http.netServer(UploadApi.class)
                .uploadPic(Cache.getUserID(), filePath)
                .map(new NetWorkFunc1<>());
    }

    /**
     * 上传多张的照片
     *
     * @param filePathList 多张照片的路径
     * @return
     */
    @WorkerThread
    public static Flowable<List<UploadData>> uploadListJPG(List<String> filePathList) {
        return Flowable.fromIterable(filePathList)
                .flatMap(UploadUtil::uploadJPG)
                .toList()
                .toFlowable();
    }

    /**
     * 上传多张图片返回 id1,id2,id3等方式
     *
     * @param filePathList 多张图片文件路径
     * @return
     */
    @WorkerThread
    public static Flowable<String> uploadListJPGIDList(List<String> filePathList) {
        return Flowable.fromIterable(filePathList)
                .flatMap(UploadUtil::uploadJPG)
                .toList()
                .toFlowable()
                .map(UploadUtil::getUploadImageIDList);
    }

    @NonNull
    public static String getUploadImageIDList(List<UploadData> uploadData) {
        StringBuilder builder = new StringBuilder();
        for (UploadData data : uploadData) {
            builder.append(data.getPicid());
            builder.append(",");
        }
        if (builder.length() > 0)
            builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    @NonNull
    public static String getImageIDList(List<Integer> uploadData) {
        StringBuilder builder = new StringBuilder();
        for (Integer data : uploadData) {
            builder.append(data);
            builder.append(",");
        }
        if (builder.length() > 0)
            builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    /**
     * 上传签名文件
     *
     * @param filePath 本地图片地址
     * @return 返回上传成功的图片信息
     */
    public static Flowable<UploadData> uploadSignPNG(String filePath) {
        return Http.netServer(UploadApi.class)
                .uploadSignPng(Cache.getUserID(), filePath)
                .map(new NetWorkFunc1<>());
    }

    /**
     * 上传文件签认的文件
     *
     * @param filePath 文件路径
     */
    public static Flowable<UploadData> uploadSignFile(String filePath) {
        return Http.netServer(UploadApi.class)
                .uploadSignPng(Cache.getUserID(), filePath)
                .map(new NetWorkFunc1<>());
    }

}
