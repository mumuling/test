package com.zhongtie.work.network;


import android.support.annotation.StringRes;

import com.zhongtie.work.R;
import com.zhongtie.work.data.Result;
import com.zhongtie.work.ui.base.BaseView;
import com.zhongtie.work.util.ToastUtil;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Chaek
 */
public class Network {

    /**
     * io->mainThread
     */
    public static <T> FlowableTransformer<T, T> networkIO() {
        return network -> network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * io->  map-> mainThread
     */
    public static <T> FlowableTransformer<Result<T>, T> convertIO() {
        return network -> network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new NetWorkFunc1<>());
    }

    /**
     * rxjava 转换 有toast错误提示
     *
     * @param <T> Result data
     */
    public static <T> FlowableTransformer<Result<T>, T> convertIOTip() {
        return network -> network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new NetWorkFunc1<>())
                .doOnError(throwable -> {
                    ToastUtil.showToast(HttpException.getErrorMessage(throwable));
                });
    }


    /**
     * {@link #convertDialogTip(BaseView, String)}
     */
    public static <T> FlowableTransformer<Result<T>, T> convertDialogTip(BaseView baseView, @StringRes int stringID) {
        return convertDialogTip(baseView, baseView.getAppContext().getString(stringID));
    }

    /**
     * {@link #convertDialogTip(BaseView, String)}
     */
    public static <T> FlowableTransformer<Result<T>, T> convertDialogTip(BaseView baseView) {
        return convertDialogTip(baseView, R.string.loading_title);
    }

    /**
     * 请求 显示loadDialog
     *
     * @param baseView {@link BaseView}
     * @param title    dialog 显示标题
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<Result<T>, T> convertDialogTip(BaseView baseView, String title) {
        return convertDialogTip(baseView, title, true);
    }


    /**
     * 网络请求 使用baseView的dialog 不弹toast
     *
     * @param baseView
     * @param titleID
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<Result<T>, T> convertDialog(BaseView baseView, @StringRes int titleID) {
        return convertDialog(baseView, baseView.getAppContext().getString(titleID));
    }

    /**
     * 网络请求 使用baseView的dialog 不弹toast
     *
     * @param baseView
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<Result<T>, T> convertDialog(BaseView baseView) {
        return convertDialog(baseView, R.string.loading_title);
    }

    /**
     * 网络请求 使用baseView的dialog 不弹toast
     *
     * @param baseView view
     * @param title    标题
     * @param <T>      转换类型
     */
    public static <T> FlowableTransformer<Result<T>, T> convertDialog(BaseView baseView, String title) {
        return convertDialogTip(baseView, title, false);
    }

    /**
     * 网络请求 使用baseView的dialog
     *
     * @param baseView view
     * @param title    标题
     * @param <T>      转换类型
     */
    public static <T> FlowableTransformer<Result<T>, T> convertDialogTip(BaseView baseView, String title, boolean isShowToast) {
        return network -> network.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> baseView.showLoadDialog(title))
                .observeOn(AndroidSchedulers.mainThread())
                .map(new NetWorkFunc1<>())
                .doFinally(baseView::cancelDialog)
                .doOnError(throwable -> {
                    baseView.cancelDialog();
                    if (isShowToast) {
                        baseView.showToast(HttpException.getErrorMessage(throwable));
                    }
                });
    }

    /**
     * 网络请求不带{@link NetWorkFunc1}
     */
    public static <T> FlowableTransformer<T, T> networkDialog(BaseView baseView) {
        return networkDialog(baseView, R.string.loading_title);
    }

    /**
     * 网络请求不带{@link NetWorkFunc1}
     */
    public static <T> FlowableTransformer<T, T> networkDialog(BaseView baseView, @StringRes int titleID) {
        return networkDialog(baseView, baseView.getAppContext().getString(titleID));
    }

    /**
     * 网络请求不带{@link NetWorkFunc1}
     */
    public static <T> FlowableTransformer<T, T> networkDialog(BaseView baseView, String title) {
        return network -> network.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> baseView.showLoadDialog(title))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(baseView::cancelDialog)
                .doOnError(throwable -> {
                    baseView.cancelDialog();
                    baseView.showToast(HttpException.getErrorMessage(throwable));
                });
    }


}
