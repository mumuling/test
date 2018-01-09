package com.zhongtie.work.ui.base;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {

    private CompositeDisposable mSubscription;
    protected T mView;

    @Override
    public void takeView(BaseView view) {
        mView = (T) view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void destroy() {
        if (mSubscription != null) {
            mSubscription.clear();
        }
    }

    @Override
    public void addDispose(Disposable disposable) {
        if (mSubscription == null) {
            synchronized (this) {
                mSubscription = new CompositeDisposable();
            }
        }
        mSubscription.add(disposable);
    }


}
