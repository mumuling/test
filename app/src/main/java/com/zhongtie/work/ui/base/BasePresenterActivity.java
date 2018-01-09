package com.zhongtie.work.ui.base;


/**
 * Auth: Chaek
 * Date: 2017/12/21
 */

public abstract class BasePresenterActivity<T extends BasePresenter> extends BaseActivity {

    protected T mPresenter;

    @Override
    public void initModel() {
        super.initModel();
        mPresenter = getPresenter();
        mPresenter.takeView(this);
    }

    protected abstract T getPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.dropView();
            mPresenter.destroy();
        }
    }
}
