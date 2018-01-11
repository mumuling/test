package com.zhongtie.work.ui.select;

import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.select.item.SelectContentItemView;
import com.zhongtie.work.widget.SideBar;

import java.util.List;

/**
 * 单位名称
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class ProjectTeamSelectFragment extends BasePresenterFragment<ProjectTeamSelectContract.Presenter> implements ProjectTeamSelectContract.View, OnSearchContentListener, SideBar.OnTouchingLetterChangedListener {
    private RecyclerView mList;
    private SideBar mSideBar;

    private CommonAdapter mCommonAdapter;
    private List<ProjectTeamEntity> mProjectTeamEntities;

    @Override
    public int getLayoutViewId() {
        return R.layout.company_search_fragment;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mSideBar = (SideBar) findViewById(R.id.side_bar);
        mSideBar.setOnTouchingLetterChangedListener(this);
    }

    @Override
    protected void initData() {
        mCommonAdapter = new CommonAdapter().register(SelectContentItemView.class);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getProjectTeamListData();
    }

    @Override
    protected ProjectTeamSelectContract.Presenter getPresenter() {
        return new ProjectTeamSelectPresenterImpl();
    }

    @Override
    public void setProjectTeamListData(List<ProjectTeamEntity> supervisionList) {
        this.mProjectTeamEntities = supervisionList;
        mCommonAdapter.setListData(supervisionList);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearch(String searchContent) {
        mPresenter.searchProjectTeamList(searchContent);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        for (int i = 0, len = mProjectTeamEntities.size(); i < len; i++) {
            if (mProjectTeamEntities.get(i).getCharacter() != null && mProjectTeamEntities.get(i).getCharacter().equals(s)) {
                mList.smoothScrollToPosition(i);
                break;
            }
        }

    }
}
