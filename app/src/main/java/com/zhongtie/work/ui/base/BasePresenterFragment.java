package com.zhongtie.work.ui.base;


/**
 * Auth: Chaek
 * Date: 2017/12/21
 */

public abstract class BasePresenterFragment<T extends BasePresenter> extends BaseFragment {

    protected T mPresenter;

    @Override
    public void initModel() {
        super.initModel();
        mPresenter = getPresenter();
        mPresenter.takeView(this);
    }

    /**
     * 默认实现Presenter方法
     *
     * @return 注解的Presenter
     */
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
