package com.zhongtie.work.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by O on 2017.5.17.
 */

class FanShuRetrofit {

    private OkHttpClient client;
    private Retrofit.Builder builder;
    private Retrofit retrofit;

    FanShuRetrofit() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new SignInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        builder = new Retrofit.Builder()
                .baseUrl(NetworkFactory.HOST)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create());
        retrofit = builder.build();
    }


    <T> T getApi(Class api) {
        return (T) retrofit.create(api);
    }

}
