package com.zhongtie.work.ui.safe;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.safe.item.SafeSupervisionItemView;
import com.zhongtie.work.ui.safe.order.SafeOrderDetailFragment;
import com.zhongtie.work.widget.RefreshRecyclerView;

import java.util.List;

/**
 * 安全督导列表几个类别
 * date:2018.1.9
 *
 * @author Chaek
 */

public class SafeSupervisionFragment extends BaseFragment implements RefreshRecyclerView.RefreshPageConfig, OnRecyclerItemClickListener {
    public static final String TYPE = "type";

    private RefreshRecyclerView mList;
    private CommonAdapter commonAdapter;
    private OnSafePageListener mOnSafePageListener;

    private int mEventType;

    public static SafeSupervisionFragment newInstance(int type) {
        Bundle args = new Bundle();
        SafeSupervisionFragment fragment = new SafeSupervisionFragment();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSafePageListener) {
            mOnSafePageListener = (OnSafePageListener) context;
        }
    }

    @Override
    public int getLayoutViewId() {
        mEventType = getArguments().getInt(TYPE);
        return R.layout.safe_supervision_fragment;
    }

    @Override
    public void initView() {
        mList = (RefreshRecyclerView) findViewById(R.id.list);
        commonAdapter = new CommonAdapter(mList.getListData()).register(SafeSupervisionItemView.class);
        mList.setDivider(true);
        mList.initConfig(this);
        mList.setEmptyView(R.layout.placeholder_empty_view);
        commonAdapter.setOnItemClickListener(this);
    }


    @Override
    protected void initData() {
    }

    public void onRefresh() {
        if (mList != null) {
            mList.onRefresh();
        }
    }

    public void setSafeSupervisionList(List<SupervisorInfoEntity.SafeSupervisionEntity> supervisionList) {
        if (mList != null) {
            mList.setListData(supervisionList);
        }

    }

    @Override
    public void fetchPageListData(int page) {
        mOnSafePageListener.getSafeTypeList(mEventType);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return commonAdapter;
    }

    @Override
    public void onClick(Object t, int index) {
        SafeSupervisionCreateActivity.newInstance(getActivity(), SafeOrderDetailFragment.class, getString(R.string.safe_supervision_title));
    }

    public void fetchPageFail() {
        mList.onFail("");
    }
}
