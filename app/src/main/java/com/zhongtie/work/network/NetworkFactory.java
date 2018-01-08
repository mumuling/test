package com.zhongtie.work.network;


import java.util.List;

/**
 * 网络请求
 * Created by O on 2017.5.16.
 */

public class NetworkFactory {
    static final String HOST = "http://47.100.3.212:82/api/api/";

    private static FanShuRetrofit retrofit;
    private List<Class> apiList;

    public static <T> T getApi(Class cl) {
        if (retrofit == null) {
            synchronized (NetworkFactory.class) {
                retrofit = new FanShuRetrofit();
            }
        }
        return retrofit.getApi(cl);
    }
}



