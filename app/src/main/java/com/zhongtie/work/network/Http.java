package com.zhongtie.work.network;


import java.util.List;

/**
 * 网络请求
 * Created by O on 2017.5.16.
 */

public class Http {
    static final String HOST = "http://47.100.3.212:82/api/api/";

    private static ZtRetrofit retrofit;
    private List<Class> apiList;

    public static <T> T netServer(Class<T> cl) {
        if (retrofit == null) {
            synchronized (Http.class) {
                retrofit = new ZtRetrofit();
            }
        }
        return retrofit.getApi(cl);
    }
}



