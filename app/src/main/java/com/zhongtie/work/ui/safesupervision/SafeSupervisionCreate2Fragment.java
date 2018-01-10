package com.zhongtie.work.ui.safesupervision;

import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.model.SafeSupervisionEnity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.widget.RefreshRecyclerView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeSupervisionCreate2Fragment extends BasePresenterFragment<SafeSupervisionContract.Presenter> implements SafeSupervisionContract.View, RefreshRecyclerView.RefreshPageConfig {



    @Override
    public int getLayoutViewId() {
        return R.layout.base_recyclerview;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected SafeSupervisionContract.Presenter getPresenter() {
        return new SafeSupervisionPresenterImpl();
    }

    @Override
    public void setSafeSupervisionList(List<SafeSupervisionEnity> supervisionList) {
    }

    @Override
    public void fetchPageListData(int page) {
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return null;
    }
}
