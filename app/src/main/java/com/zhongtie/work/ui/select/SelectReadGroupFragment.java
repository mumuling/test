package com.zhongtie.work.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.zhongtie.work.util.ResourcesUtils;
import com.zhongtie.work.util.parse.BindKey;
import com.zhongtie.work.widget.DividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Flowable;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 选择查阅组
 * <p>
 * date:2018.1.11
 *
 * @author Chaek
 */
public class SelectReadGroupFragment extends BaseFragment implements OnRecyclerItemClickListener<TeamNameEntity> {
    private TextView mItemTeamSelectAll;
    private RecyclerView mCheckExamineList;

    @BindKey(LIST)
    private List<TeamNameEntity> nameEntityList = new ArrayList<>();
    private List<TeamNameEntity> mAllTeamList = new ArrayList<>();

    private CommonAdapter mCommonAdapter;

    private boolean isSelect;

    @Override
    public int getLayoutViewId() {
        return R.layout.look_group_fragment;
    }

    @Override
    public void initView() {
        mItemTeamSelectAll = (TextView) findViewById(R.id.item_team_select_all);
        mCheckExamineList = (RecyclerView) findViewById(R.id.check_examine_list);
        mCheckExamineList.setLayoutManager(new LinearLayoutManager(getAppContext()));
        TextView add = (TextView) findViewById(R.id.add);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.white));
        dividerItemDecoration.setDividerHeight(ResourcesUtils.dip2px(5));
        mCheckExamineList.addItemDecoration(dividerItemDecoration);

        add.setOnClickListener(view -> {
            //点击确定返回数据
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(TITLE, getArguments().getString(TITLE));
            bundle.putSerializable(LIST, (Serializable) nameEntityList);
            intent.putExtras(bundle);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
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
        mItemTeamSelectAll.setText(!isSelect ? R.string.select_all : R.string.select_cancel_all);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void userEntityEvent(TeamNameEntity createUserEntity) {
        Iterator<TeamNameEntity> nameEntities = nameEntityList.iterator();
        boolean isExist = false;
        while (nameEntities.hasNext()) {
            TeamNameEntity teamNameEntity = nameEntities.next();
            if (teamNameEntity.getTeamId() == createUserEntity.getTeamId()) {
                nameEntities.remove();
                isExist = true;
                break;
            }
        }
        if (!isExist) {
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
