package com.zhongtie.work.ui.rewardpunish;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.RewardPunishEntity;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.list.OnDateCallback;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.rewardpunish.detail.PunishDetailFragment;
import com.zhongtie.work.ui.rewardpunish.adapter.RewardPunishItemView;
import com.zhongtie.work.ui.rewardpunish.adapter.RewardPunishReadItemView;
import com.zhongtie.work.ui.rewardpunish.presenter.RewardPunishContract;
import com.zhongtie.work.ui.rewardpunish.presenter.RewardPunishListPresenterImpl;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.util.parse.BindKey;
import com.zhongtie.work.widget.RefreshRecyclerView;

import java.util.HashMap;
import java.util.List;

/**
 * 安全奖惩列表
 *
 * @author Chaek
 * @date:2018.1.9
 */

public class RewardPunishFragment extends BasePresenterFragment<RewardPunishContract.Presenter>
        implements RewardPunishContract.View, RefreshRecyclerView.RefreshPageConfig, OnRecyclerItemClickListener {

    public static final String TYPE = "type";
    private RefreshRecyclerView mList;
    private CommonAdapter commonAdapter;
    private OnDateCallback mOnDateCallback;

    @BindKey(TYPE)
    private int mType = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateCallback) {
            mOnDateCallback = (OnDateCallback) context;
        }
    }

    public static RewardPunishFragment newInstance(int type) {
        Bundle args = new Bundle();
        RewardPunishFragment fragment = new RewardPunishFragment();
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
        commonAdapter = new CommonAdapter();
        //区分列表
        if (mType == 0) {
            commonAdapter.register(RewardPunishItemView.class);
        } else {
            commonAdapter.register(RewardPunishReadItemView.class);
        }
        mList.setDivider(true);
        mList.initConfig(this);
        mList.setEmptyView(R.layout.placeholder_empty_view);
        commonAdapter.setOnItemClickListener(this);
    }

    @Override
    protected RewardPunishContract.Presenter getPresenter() {
        return new RewardPunishListPresenterImpl();
    }


    public void onRefresh() {
        if (mList != null) {
            mList.onRefresh();
        }
    }

    @Override
    public void setPunishList(List<RewardPunishEntity> list) {
        mList.setListData(list);
    }

    @Override
    public void loadListFail() {
        mList.onFail("");
    }

    @Override
    public void fetchPageListData(int page) {
        if (page == 1) {
            mPresenter.fetchPageList(mOnDateCallback.getSelectDate(), mType, page);
        }
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return commonAdapter;
    }

    @Override
    public void onClick(Object t, int index) {
        SafeSupervisionCreateActivity.newInstance(getActivity(), PunishDetailFragment.class, getString(R.string.safe_punish_title));
    }
}
