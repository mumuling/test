package com.zhongtie.work.util.image;

/**
 * Created by Cheek on 2017.5.17.
 */

public interface ImageLoaderListener {
    void loaderSuccess(String url);

    void loaderFail();
}
