
package com.zhongtie.work.ui.base;

import io.reactivex.disposables.Disposable;

public interface BasePresenter<T extends BaseView> {

    /**
     * Binds mPresenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this mPresenter
     */
    void takeView(T view);


    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();

    /**
     * 销毁
     */
    void destroy();

    void addDispose(Disposable disposable);

}
