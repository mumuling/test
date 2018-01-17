package com.zhongtie.work.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.event.ExitEvent;
import com.zhongtie.work.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @author Chaek
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView, View.OnClickListener {

    protected CompositeDisposable mDisposable;
    protected TextView mToolbarTitle;
    protected TextView mMenuTitle;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutViewId());
        if (EventBus.getDefault().hasSubscriberForEvent(getClass())) {
            EventBus.getDefault().register(this);
        }
        initTitle();
        initView(savedInstanceState);
        initView();
        initModel();
        initData();
    }

    protected void initView(Bundle savedInstanceState) {

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

    @Subscribe
    public void exitLoginAppEvent(ExitEvent exitEvent) {
        finish();
    }

    private void initTitle() {
        mToolbarTitle = findViewById(R.id.title);
        if (mToolbarTitle != null) {
            findViewById(R.id.title_back).setOnClickListener(this);
        }
        mMenuTitle = findViewById(R.id.title_right_btn);
        if (mMenuTitle != null) {
            mMenuTitle.setOnClickListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(true);
        if (mDisposable != null) {
            mDisposable.clear();
        }
        cancelDialog();
        mLoadingDialog = null;
    }

    public void setRightText(String text) {
        mMenuTitle.setText(text);
        mMenuTitle.setVisibility(View.VISIBLE);
    }

    public void setRightText(int text) {
        setRightText(getString(text));
    }

    public void setTitle(String title, String right) {
        setTitle(title, true);
        setRightText(right);
    }

    /**
     * 设置标题
     *
     * @param title 标题内容
     */
    public void setTitle(String title) {
        setTitle(title, true);
    }

    public void setTitle(String title, boolean isShowUp) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
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
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.setText(message);
            } else {
                mLoadingDialog = new LoadingDialog(this, message);
                mLoadingDialog.setCancelable(false);
                mLoadingDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoadDialog(int messageId) {
    }

    @Override
    public void cancelDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
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
     * 右边按钮检测
     */
    protected void onClickRight() {

    }

    /**
     * 需要拦截时间则需要 重写{@link #onClickLeft()}
     */
    protected void onClickLeft() {
        finish();
    }

    /**
     * 刷新界面
     */
    protected void onClickRefresh() {
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

    /**
     * 隐藏键盘
     */
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            try {
                IBinder iBinder = getWindow().getDecorView().getWindowToken();
                if (iBinder != null) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                onClickLeft();
                break;
            case R.id.title_right_btn:
                onClickRight();
                break;
        }
    }

}
