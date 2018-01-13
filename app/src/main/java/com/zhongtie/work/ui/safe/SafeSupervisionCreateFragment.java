package com.zhongtie.work.ui.safe;

import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.model.SafeSupervisionEntity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.widget.RefreshRecyclerView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeSupervisionCreateFragment extends BasePresenterFragment<SafeSupervisionContract.Presenter> implements SafeSupervisionContract.View, RefreshRecyclerView.RefreshPageConfig {


    @Override
    public int getLayoutViewId() {
        return R.layout.safe_supervision_create_fragment;
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
    public void setSafeSupervisionList(List<SafeSupervisionEntity> supervisionList) {
    }

    @Override
    public void fetchPageListData(int page) {
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return null;
    }
}
