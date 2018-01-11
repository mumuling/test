package com.zhongtie.work.ui.select;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.select.item.TeamNameItemView;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

public class SelectLookGroupFragment extends BaseFragment {
    private RelativeLayout mLookGroupTitle;
    private TextView mItemTeamTitle;
    private TextView mItemTeamSelectAll;
    private RecyclerView mCheckExamineList;
    private TextView mAdd;

    @Override
    public int getLayoutViewId() {
        return R.layout.look_group_fragment;
    }

    @Override
    public void initView() {
        mLookGroupTitle = (RelativeLayout) findViewById(R.id.look_group_title);
        mItemTeamTitle = (TextView) findViewById(R.id.item_team_title);
        mItemTeamSelectAll = (TextView) findViewById(R.id.item_team_select_all);
        mCheckExamineList = (RecyclerView) findViewById(R.id.check_examine_list);
        mCheckExamineList.setLayoutManager(new LinearLayoutManager(getAppContext()));
        mAdd = (TextView) findViewById(R.id.add);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.white));
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(5));
        mCheckExamineList.addItemDecoration(dividerItemDecoration);

    }

    @Override
    protected void initData() {
        CommonAdapter adapter = new CommonAdapter().register(TeamNameItemView.class);

        List<TeamNameEntity> teamNameEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            teamNameEntities.add(new TeamNameEntity());
        }
        adapter.setListData(teamNameEntities);
        mCheckExamineList.setAdapter(adapter);

    }
}
