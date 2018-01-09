package com.zhongtie.work.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.zhongtie.work.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Auth: Chaek
 * Date: 2017/11/27
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected CompositeDisposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutViewId());
        if (EventBus.getDefault().hasSubscriberForEvent(getClass())) {
            EventBus.getDefault().register(this);
        }
        initTitle();
        initView();
        initModel();
        initData();
    }

    protected void initModel() {

    }

    protected void setBaseTitle(String title) {

    }

    protected void setBaseTitle(@StringRes int title) {
        setTitle(getString(title));
    }

    @Subscribe
    public void baseEvent() {

    }

    private void initTitle() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(true);
        if (mDisposable != null) {
            mDisposable.clear();
        }

    }

    /**
     * @return get layout view id
     */
    protected abstract int getLayoutViewId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化赋值数据
     */
    protected abstract void initData();

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void showToast(int strId) {
        ToastUtil.showToast(strId);
    }


    @Override
    public void showLoadDialog(String message) {
    }

    @Override
    public void showLoadDialog(int messageId) {
    }

    @Override
    public void cancelDialog() {
    }

    @Override
    public void addDispose(Disposable disposable) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(disposable);
    }


    /**
     * 初始化加载界面展示效果
     */
    @Override
    public void initLoading() {

    }

    /**
     * 加载失败界面展示效果
     */
    @Override
    public void initFail() {

    }

    /**
     * 加载成功界面展示效果
     */
    @Override
    public void initSuccess() {

    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
