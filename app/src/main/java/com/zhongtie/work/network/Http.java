package com.zhongtie.work.network;


import android.support.v4.util.ArrayMap;

/**
 * 网络请求
 * Created by O on 2017.5.16.
 */

public class Http {
    static final String HOST = "http://47.100.3.212:82/api/api/";

    private static ZtRetrofit retrofit;
    private static ArrayMap<String, Object> mCacheServer;

    public static <T> T netServer(Class<T> cl) {
        if (retrofit == null) {
            synchronized (Http.class) {
                retrofit = new ZtRetrofit();
            }
        }
        if (mCacheServer == null) {
            mCacheServer = new ArrayMap<>();
        }
        Object cache = mCacheServer.get(cl.getName());
        if (cache == null) {
            cache = retrofit.getApi(cl);
            mCacheServer.put(cl.getName(), cache);
        }
        return retrofit.getApi(cl);
    }
}



