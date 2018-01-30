package com.zhongtie.work.ui.safe;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.list.OnDateCallback;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.safe.item.SafeSupervisionItemView;
import com.zhongtie.work.ui.safe.detail.SafeOrderDetailFragment;
import com.zhongtie.work.ui.safe.presenter.SafeSupervisionContract;
import com.zhongtie.work.ui.safe.presenter.SafeSupervisionPresenterImpl;
import com.zhongtie.work.util.parse.BindKey;
import com.zhongtie.work.widget.RefreshRecyclerView;

import java.util.List;

import static com.zhongtie.work.ui.safe.SafeSupervisionActivity.KEY_IS_SELECT;

/**
 * 安全督导列表几个类别
 * date:2018.1.9
 *
 * @author Chaek
 */

public class SafeSupervisionFragment extends BasePresenterFragment<SafeSupervisionContract.Presenter>
        implements RefreshRecyclerView.RefreshPageConfig, OnRecyclerItemClickListener<SupervisorInfoEntity>, SafeSupervisionContract.View {
    public static final String TYPE = "type";

    private RefreshRecyclerView mList;
    private CommonAdapter commonAdapter;

    @BindKey(TYPE)
    private int mEventType;

    @BindKey(KEY_IS_SELECT)
    private boolean isSelect;

    private OnDateCallback mOnDateCallback;

    public static SafeSupervisionFragment newInstance(int type, boolean isSelect) {
        Bundle args = new Bundle();
        SafeSupervisionFragment fragment = new SafeSupervisionFragment();
        args.putInt(TYPE, type);
        args.putBoolean(KEY_IS_SELECT, isSelect);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateCallback) {
            mOnDateCallback = (OnDateCallback) context;
        }
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.safe_supervision_fragment;
    }

    @Override
    public void initView() {
        mList = (RefreshRecyclerView) findViewById(R.id.list);
        commonAdapter = new CommonAdapter(mList.getListData()).register(SafeSupervisionItemView.class);
    }


    @Override
    protected void initData() {
        mList.setDivider(true);
        mList.initConfig(this);
        mList.setEmptyView(R.layout.placeholder_empty_view);
        commonAdapter.setOnItemClickListener(this);
    }

    public void onRefresh() {
        if (mList != null) {
            mList.onRefresh();
        }
    }

    @Override
    public void fetchPageListData(int page) {
        if (page > 1) {
            return;
        }
        mPresenter.fetchPageList(mOnDateCallback.getSelectDate(), mEventType, page);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return commonAdapter;
    }

    @Override
    public void onClick(SupervisorInfoEntity safeSupervisionEntity, int index) {
        if (isSelect) {
            safeSupervisionEntity.post();
            getActivity().finish();
        } else {
            SafeOrderDetailFragment.start(getContext(), safeSupervisionEntity.getEventId());
        }
    }

    @Override
    protected SafeSupervisionContract.Presenter getPresenter() {
        return new SafeSupervisionPresenterImpl();
    }

    @Override
    public void setSafeSupervisionList(List<SupervisorInfoEntity> supervisionList, int type) {
        if (mList != null) {
            mList.setListData(supervisionList);
        }
    }

    @Override
    public void fetchPageFail(int type) {
        mList.onFail("");
    }
}
