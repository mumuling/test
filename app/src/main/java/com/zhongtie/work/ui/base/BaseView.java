
package com.zhongtie.work.ui.base;

import android.content.Context;
import android.support.annotation.StringRes;

import io.reactivex.disposables.Disposable;

public interface BaseView {

    void showToast(String message);

    void showToast(@StringRes int strId);

    void showLoadDialog(String message);

    void showLoadDialog(int messageId);

    void cancelDialog();

    Context getAppContext();

    void addDispose(Disposable disposable);

    /**
     * 初始化加载界面展示效果
     */
    void initLoading();

    /**
     * 加载失败界面展示效果
     */
    void initFail();

    /**
     * 加载成功界面展示效果
     */
    void initSuccess();

}
