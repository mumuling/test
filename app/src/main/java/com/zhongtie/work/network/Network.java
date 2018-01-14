package com.zhongtie.work.network;


import com.zhongtie.work.data.Result;
import com.zhongtie.work.ui.base.BaseView;
import com.zhongtie.work.util.ToastUtil;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Chaek
 */
public class Network {

    /**
     * impl netWork io
     * <p>
     * <p>
     * network thread {@link Schedulers#io()}
     * <p>
     * ui thread   {@link AndroidSchedulers#mainThread()}
     * </p>
     */
    public static <T> FlowableTransformer<T, T> netorkIO() {
        return network -> network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<T, T> networkFlowableIO() {
        return network -> network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<Result<T>, T> convertIO() {
        return network -> network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new NetWorkFunc1<>())
                .doOnError(throwable -> {
                    ToastUtil.showToast(HttpException.getErrorMessage(throwable));
                });
    }

    public static <T> FlowableTransformer<Result<T>, T> convertFlowableIO() {
        return network -> network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new NetWorkFunc1<>())
                .doOnError(throwable -> {
                    ToastUtil.showToast(HttpException.getErrorMessage(throwable));
                });
    }

    public static <T> FlowableTransformer<Result<T>, T> networkConvertDialog(BaseView baseView,String title) {
        return network -> network.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> baseView.showLoadDialog(title))
                .observeOn(AndroidSchedulers.mainThread())
                .map(new NetWorkFunc1<>())
                .doFinally(baseView::cancelDialog)
                .doOnError(throwable -> {
                    baseView.cancelDialog();
                    baseView.showToast(HttpException.getErrorMessage(throwable));
                });
    }
    public static <T> FlowableTransformer<T, T> networkDialog(BaseView baseView,String title) {
        return network -> network.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> baseView.showLoadDialog(title))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(baseView::cancelDialog)
                .doOnError(throwable -> {
                    baseView.cancelDialog();
                    baseView.showToast(HttpException.getErrorMessage(throwable));
                });
    }

    public static <T> FlowableTransformer<Result<T>, T> networkConvertDialog(BaseView baseView) {
        return networkConvertDialog(baseView,"请稍后");
    }


}
