package com.zhongtie.work.ui.select;

import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.event.SelectCompanyEvent;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.select.item.SelectContentItemView;
import com.zhongtie.work.widget.SideBar;

import java.util.List;

/**
 * 选择单位名称
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class ProjectTeamSelectFragment extends BasePresenterFragment<ProjectTeamSelectContract.Presenter> implements ProjectTeamSelectContract.View, OnSearchContentListener,
        SideBar.OnTouchingLetterChangedListener, OnRecyclerItemClickListener<ProjectTeamEntity> {

    public static final String TYPE = "select_type";
    private RecyclerView mList;
    private CommonAdapter mCommonAdapter;
    private List<ProjectTeamEntity> mProjectTeamEntities;

    @Override
    public int getLayoutViewId() {
        return R.layout.company_search_fragment;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        SideBar sideBar = (SideBar) findViewById(R.id.side_bar);
        sideBar.setOnTouchingLetterChangedListener(this);
    }

    @Override
    protected void initData() {
        mCommonAdapter = new CommonAdapter().register(SelectContentItemView.class);
        mList.setAdapter(mCommonAdapter);
        mCommonAdapter.setOnItemClickListener(this);
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
    public int listType() {
        return getArguments().getInt(TYPE, 0);
    }

    @Override
    public void onSearch(String searchContent) {
        mPresenter.searchProjectTeamList(searchContent);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        if (mProjectTeamEntities != null) {
            for (int i = 0, len = mProjectTeamEntities.size(); i < len; i++) {
                if (mProjectTeamEntities.get(i).getCharacter() != null && mProjectTeamEntities.get(i).getCharacter().equals(s)) {
                    mList.smoothScrollToPosition(i);
                    break;
                }
            }

        }
    }

    @Override
    public void onClick(ProjectTeamEntity projectTeamEntity, int index) {
        if (projectTeamEntity.getCompanyID() == 0) {
            return;
        }
        new SelectCompanyEvent(listType(), projectTeamEntity).post();
        getActivity().finish();
    }
}
