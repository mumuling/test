package com.zhongtie.work.network;


import com.zhongtie.work.data.Result;

import io.reactivex.functions.Function;


/**
 * 接口检测
 */
class NetWorkFunc1<T> implements Function<Result<T>, T> {

    @Override
    public T apply(Result result) throws Exception {
        return (T) result.getData();
    }
}
