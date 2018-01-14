package com.zhongtie.work.network;


import com.zhongtie.work.data.Result;

import io.reactivex.functions.Function;


/**
 * 接口检测
 */
public class NetWorkFunc1<T> implements Function<Result<T>, T> {

    @Override
    public T apply(Result result) throws Exception {
        if (result.isSuccess() || result.getCode() == 1) {
            if (result.getData() != null)
                return (T) result.getData();
            return (T) result.getList();
        }
        throw new HttpException(result.getMsg(), result.getCode());
    }
}
