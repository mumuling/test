package com.zhongtie.work.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongtie.work.R;
import com.zhongtie.work.list.OnActivityKeyListener;
import com.zhongtie.work.list.OnChangeTitleListener;
import com.zhongtie.work.util.ToastUtil;
import com.zhongtie.work.util.parse.ParseData;
import com.zhongtie.work.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.Disposable;


/**
 * BaseFragment
 *
 * @author Chaek
 */
public abstract class BaseFragment extends Fragment implements BaseView, View.OnClickListener, OnActivityKeyListener {

    public Context mContext;
    public View fragmentView;
    public MultipleStatusView mStatusView;
    private boolean isInitView;
    private boolean isInitData;
    private BaseView activityBaseView;
    private OnChangeTitleListener mOnChangeTitleListener;
    private BaseActivity mBaseActivity;


    @Override
    public void onAttach(Context context) {
        new ParseData(this);

        if (context instanceof BaseView) {
            activityBaseView = (BaseView) context;
        }
        if (context instanceof OnChangeTitleListener) {
            mOnChangeTitleListener = (OnChangeTitleListener) context;
        }
        if (context instanceof BaseActivity) {
            if (isFetchBackEvent()) {
                mBaseActivity = (BaseActivity) context;
                mBaseActivity.setOnActivityKeyListener(this);
            }
        }

        super.onAttach(context);
        this.mContext = context;
        EventBus.getDefault().register(this);

    }

    protected View findViewById(@IdRes int id) {
        return fragmentView.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentView == null) {
            setFragmentView(container);
        }
        isInitView = true;
        if (mStatusView != null) {
            mStatusView.setOnRetryClickListener(this);
        }
        return fragmentView;
    }

    protected void setFragmentView(ViewGroup container) {
        fragmentView = createFragmentView(container);
        if (fragmentView == null) {
            fragmentView = LayoutInflater.from(getActivity()).inflate(getLayoutViewId(), container, false);
        }
        mStatusView = (MultipleStatusView) findViewById(R.id.status_view);
        initModel();
        initView();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //逻辑实现懒加载 initData 中执行加载方法即是懒加载
        if (getUserVisibleHint() && isInitView && !isInitData) {
            initData();
            isInitData = true;
        }
    }


    public void initModel() {
    }

    @Override
    public void showLoadDialog(String message) {
        if (activityBaseView != null) {
            activityBaseView.showLoadDialog(message);
        }
    }

    public boolean isInitView() {
        return isInitView;
    }

    public boolean isInitData() {
        return isInitData;
    }

    @Override
    public void showLoadDialog(int messageId) {
        if (activityBaseView != null) {
            activityBaseView.showLoadDialog(messageId);
        }
    }


    @Override
    public void cancelDialog() {
        if (activityBaseView != null) {
            activityBaseView.cancelDialog();
        }
    }

    /**
     * 获取一个Context 为Application 不可用作打开Activity获取弹出Dialog
     *
     * @return ApplicationContext
     */
    @Override
    public Context getAppContext() {
        if (activityBaseView != null) {
            activityBaseView.getAppContext();
        }
        return getActivity().getApplicationContext();
    }

    /**
     * 设置标题 调用 {@link BaseActivity#setTitle(String)} 实现
     * 所以Fragment要更改标题或设置标题请Activity 实现{@link OnChangeTitleListener}
     *
     * @param title 标题
     */
    protected void setTitle(String title) {
        if (mOnChangeTitleListener != null) {
            mOnChangeTitleListener.setTitle(title);
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题string id
     */
    protected void setTitle(@StringRes int title) {
        setTitle(getString(title));
    }


    /**
     * 可拦截拦截实现返回一个View 就可以不去实现{@link #getLayoutViewId()}
     */
    protected View createFragmentView(ViewGroup container) {
        return null;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isInitView && !isInitData) {
            initData();
            isInitData = true;
        }
    }

    /**
     * get view widget
     *
     * @param viewId view Id
     * @param <T>    view Type
     * @return view
     */
    public <T extends View> T getView(int viewId) {
        return (T) fragmentView.findViewById(viewId);
    }


    /**
     * EveBus CallBack
     *
     * @param event data
     */
    @Subscribe
    public void onEventThread(int event) {
    }


    @Override
    public void showToast(String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void showToast(int strId) {
        showToast(getString(strId));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_retry_view:
            case R.id.error_view_tv:
                onClickRefresh();
                break;
            default:
        }
    }

    /**
     * 实现点击刷新逻辑
     */
    public void onClickRefresh() {
    }

    /**
     * 初始化加载界面展示效果
     */
    @Override
    public void initLoading() {
        if (mStatusView != null) {
            mStatusView.showLoading();
        }
    }

    /**
     * 加载失败界面展示效果
     */
    @Override
    public void initFail() {
        if (mStatusView != null) {
            mStatusView.showError();
        }
    }


    @Override
    public void addDispose(Disposable disposable) {
        if (activityBaseView != null) {
            activityBaseView.addDispose(disposable);
        }
    }

    /**
     * 加载成功界面展示效果
     */
    @Override
    public void initSuccess() {
        if (mStatusView != null) {
            mStatusView.showContent();
        }
    }


    /**
     * 要监听点击返回重写本方法为true
     *
     * @return
     */
    public boolean isFetchBackEvent() {
        return false;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mBaseActivity != null) {
            mBaseActivity.setOnActivityKeyListener(null);
        }
        mBaseActivity = null;
    }

    /**
     * @return get root layout id
     */
    public abstract int getLayoutViewId();

    /**
     * initAttach view
     */
    public abstract void initView();

    /**
     * initAttach data
     */
    protected abstract void initData();

    @Override
    public void onClickBack() {

    }
}