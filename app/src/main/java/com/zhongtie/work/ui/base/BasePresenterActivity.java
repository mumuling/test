package com.zhongtie.work.ui.base;


/**
 * Date: 2017/12/21
 *
 * @author Chaek
 */

public abstract class BasePresenterActivity<T extends BasePresenter> extends BaseActivity {

    protected T mPresenter;

    @Override
    public void initModel() {
        super.initModel();
        if (mPresenter == null) {
            mPresenter = getPresenter();
            mPresenter.takeView(this);
        }
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
