package com.zhongtie.work.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongtie.work.R;
import com.zhongtie.work.util.ToastUtil;
import com.zhongtie.work.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.Disposable;


/**
 * Base
 */
public abstract class BaseFragment extends Fragment implements BaseView, View.OnClickListener {

    public View fragmentView;
    /**
     * 是否初始
     */
    private boolean isInitView;
    public Context mContext;
    public boolean isInitData;
    public MultipleStatusView mStatusView;
    private BaseView activityBaseView;


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

    @Override
    public Context getAppContext() {
        if (activityBaseView != null) {
            activityBaseView.getAppContext();
        }
        return getActivity().getApplicationContext();
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof BaseView) {
            activityBaseView = (BaseView) context;
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

    /**
     * 可拦截layout id 获取View
     */
    protected View createFragmentView(ViewGroup container) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && isInitView && !isInitData) {
            initData();
            isInitData = true;
        }
    }

    public void initModel() {
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

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    /**
     * EveBus CallBack
     *
     * @param event data
     */
    @Subscribe
    public void onEventThread(int event) {
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

}