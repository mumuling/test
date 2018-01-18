package com.zhongtie.work.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.db.CompanyUserGroupTable;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.select.item.SelectTeamNameItemView;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.DividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

public class SelectLookGroupFragment extends BaseFragment implements OnRecyclerItemClickListener<TeamNameEntity> {
    private RelativeLayout mLookGroupTitle;
    private TextView mItemTeamTitle;
    private TextView mItemTeamSelectAll;
    private RecyclerView mCheckExamineList;
    private TextView mAdd;

    private List<TeamNameEntity> nameEntityList = new ArrayList<>();
    private List<TeamNameEntity> mAllTeamList = new ArrayList<>();

    private CommonAdapter mCommonAdapter;

    private boolean isSelect;

    @Override
    public int getLayoutViewId() {
        nameEntityList = (List<TeamNameEntity>) getArguments().getSerializable(LIST);
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

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.white));
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(5));
        mCheckExamineList.addItemDecoration(dividerItemDecoration);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击确定返回数据
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(TITLE, getArguments().getString(TITLE));
                bundle.putSerializable(LIST, (Serializable) nameEntityList);
                intent.putExtras(bundle);
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();
            }
        });

        mItemTeamSelectAll.setOnClickListener(v -> selectAllTeam());
    }

    private void selectAllTeam() {
        nameEntityList = new ArrayList<>();
        for (TeamNameEntity entity : mAllTeamList) {
            entity.setSelect(!isSelect);
            if (!isSelect) {
                nameEntityList.add(entity);
            } else {
                nameEntityList.clear();
            }
        }
        isSelect = !isSelect;
        mItemTeamSelectAll.setText(!isSelect ? "全选" : "取消全选");
        mCommonAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void userEntityEvent(TeamNameEntity createUserEntity) {
        if (nameEntityList.contains(createUserEntity)) {
            nameEntityList.remove(createUserEntity);
        } else {
            nameEntityList.add(createUserEntity);
        }
    }

    @Override
    protected void initData() {
        mCommonAdapter = new CommonAdapter().register(SelectTeamNameItemView.class);
        mCommonAdapter.setOnItemClickListener(this);

        Flowable.fromCallable(() -> SQLite.select().from(CompanyUserGroupTable.class).queryList())
                .flatMap(Flowable::fromIterable)
                .map(companyUserGroupTable -> {
                    TeamNameEntity teamNameEntity = new TeamNameEntity();
                    teamNameEntity.setTeamName(companyUserGroupTable.getGroupName());
                    teamNameEntity.setTeamId(companyUserGroupTable.getId());
                    for (int i = 0; i < nameEntityList.size(); i++) {
                        TeamNameEntity data = nameEntityList.get(i);
                        if (teamNameEntity.getTeamId() == data.getTeamId()) {
                            teamNameEntity.setSelect(true);
                            break;
                        }
                    }
                    return teamNameEntity;
                }).toList()
                .toFlowable()
                .compose(Network.networkIO())
                .subscribe(teamNameEntities -> {
                    mAllTeamList = teamNameEntities;
                    mCommonAdapter.setListData(teamNameEntities);
                    mCheckExamineList.setAdapter(mCommonAdapter);
                }, throwable -> {

                });
    }


    @Override
    public void onClick(TeamNameEntity teamNameEntity, int index) {

    }
}
