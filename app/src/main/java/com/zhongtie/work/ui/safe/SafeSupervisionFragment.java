package com.zhongtie.work.ui.safe;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.model.SafeSupervisionEnity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.safe.item.SafeSupervisionItemView;
import com.zhongtie.work.ui.safe.order.SafeOrderDetailFragment;
import com.zhongtie.work.widget.RefreshRecyclerView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeSupervisionFragment extends BasePresenterFragment<SafeSupervisionContract.Presenter> implements SafeSupervisionContract.View, RefreshRecyclerView.RefreshPageConfig, OnRecyclerItemClickListener {

    public static final String TYPE = "type";

    private RefreshRecyclerView mList;
    private CommonAdapter commonAdapter;

    public static SafeSupervisionFragment newInstance(int type) {
        Bundle args = new Bundle();
        SafeSupervisionFragment fragment = new SafeSupervisionFragment();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.safe_supervision_fragment;
    }

    @Override
    public void initView() {
        mList = (RefreshRecyclerView) findViewById(R.id.list);

    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter().register(SafeSupervisionItemView.class);
        mList.setDivider(true);
        mList.initConfig(this);
        mList.setEmptyView(R.layout.placeholder_empty_view);
        commonAdapter.setOnItemClickListener(this);
    }

    @Override
    protected SafeSupervisionContract.Presenter getPresenter() {
        return new SafeSupervisionPresenterImpl();
    }

    @Override
    public void setSafeSupervisionList(List<SafeSupervisionEnity> supervisionList) {
        mList.setListData(supervisionList);
    }

    @Override
    public void fetchPageListData(int page) {
        mPresenter.fetchPageList("", 0, page);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return commonAdapter;
    }

    @Override
    public void onClick(Object t, int index) {
        SafeSupervisionCreateActivity.newInstance(getAppContext(), SafeOrderDetailFragment.class, getString(R.string.safe_supervision_title));
    }
}
