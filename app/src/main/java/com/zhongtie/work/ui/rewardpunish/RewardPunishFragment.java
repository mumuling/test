package com.zhongtie.work.ui.rewardpunish;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.rewardpunish.detail.RPOrderDetailFragment;
import com.zhongtie.work.ui.rewardpunish.item.RewardPunishItemView;
import com.zhongtie.work.ui.rewardpunish.item.RewardPunishLookItemView;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.widget.RefreshRecyclerView;

import java.util.HashMap;
import java.util.List;

/**
 * 安全奖惩列表
 * Auth:Cheek
 * date:2018.1.9
 */

public class RewardPunishFragment extends BasePresenterFragment<RewardPunishContract.Presenter> implements RewardPunishContract.View, RefreshRecyclerView.RefreshPageConfig, OnRecyclerItemClickListener {

    public static final String TYPE = "type";

    private RefreshRecyclerView mList;
    private CommonAdapter commonAdapter;
    private int mType;

    public static RewardPunishFragment newInstance(int type) {
        Bundle args = new Bundle();
        RewardPunishFragment fragment = new RewardPunishFragment();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutViewId() {
        mType = getArguments().getInt(TYPE, 0);
        return R.layout.safe_supervision_fragment;
    }

    @Override
    public void initView() {
        mList = (RefreshRecyclerView) findViewById(R.id.list);
    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter();
        //区分列表
        if (mType == 0) {
            commonAdapter.register(RewardPunishItemView.class);
        } else {
            commonAdapter.register(RewardPunishLookItemView.class);
        }
        mList.setDivider(true);
        mList.initConfig(this);
        mList.setEmptyView(R.layout.placeholder_empty_view);
        commonAdapter.setOnItemClickListener(this);
    }

    @Override
    protected RewardPunishContract.Presenter getPresenter() {
        return new PresenterImpl();
    }

    @Override
    public void setSafeSupervisionList(List<SupervisorInfoEntity> supervisionList) {
        mList.setListData(supervisionList);
    }

    @Override
    public void setSafeEventCountList(HashMap<String, String> eventCountData) {
    }

    @Override
    public void fetchPageListData(int page) {
        if (page == 1) {
            mPresenter.fetchPageList("", 0, page);
        }
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return commonAdapter;
    }

    @Override
    public void onClick(Object t, int index) {
        SafeSupervisionCreateActivity.newInstance(getActivity(), RPOrderDetailFragment.class, getString(R.string.safe_supervision_title));

    }
}
