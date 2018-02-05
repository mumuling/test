package com.zhongtie.work.ui.select;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.db.CompanyUnitEntity;
import com.zhongtie.work.db.CompanyWorkTeamTable;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.TextUtil;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

class ProjectTeamSelectPresenterImpl extends BasePresenterImpl<ProjectTeamSelectContract.View> implements ProjectTeamSelectContract.Presenter {
    private List<ProjectTeamEntity> mProjectTeamEntities;

    @Override
    public void getProjectTeamListData() {
        //单位
        Flowable<ProjectTeamEntity> unit = Flowable.fromCallable(() -> SQLite.select().from(CompanyUnitEntity.class).queryList())
                .flatMap(Flowable::fromIterable)
                .map(CompanyUnitEntity::convert);

        //劳务公司
        Flowable<ProjectTeamEntity> workTeam = Flowable.fromCallable(() -> SQLite.select().from(CompanyWorkTeamTable.class).queryList())
                .flatMap(Flowable::fromIterable)
                .map(CompanyWorkTeamTable::convert);

        Flowable<ProjectTeamEntity> baseFlowable = mView.listType() == 0 ? unit : workTeam;
        addDispose(baseFlowable
                .toList()
                .map(projectTeamEntities -> {
                    //排序
                    Collections.sort(projectTeamEntities, new PinyinComparator());
                    mProjectTeamEntities = projectTeamEntities;
                    return projectTeamEntities;
                })
                .toFlowable()
                .concatMap(this::convertList)
                .toList()
                .toFlowable()
                .compose(Network.networkIO())
                .subscribe(projectTeamEntities -> mView.setProjectTeamListData(projectTeamEntities), throwable -> {
                    throwable.printStackTrace();
                }));
    }

    /**
     * 为list按照字幕分组并加上
     */
    private Flowable<ProjectTeamEntity> convertList(List<ProjectTeamEntity> projectTeamEntities) {
        return Flowable.fromIterable(projectTeamEntities)
                .map(ProjectTeamEntity::getCharacter)
                .distinct()
                .toList()
                .toFlowable()
                .flatMap(Flowable::fromIterable)
                .concatMap(s -> Flowable.fromIterable(projectTeamEntities)
                        .filter(projectTeamEntity -> projectTeamEntity.getCharacter().equals(s))
                        .toList()
                        .toFlowable()
                        .concatMap(projectTeamEntities1 -> {
                            Log.d("-----------", "getProjectTeamListData: " + s + "====" + projectTeamEntities1.size());
                            ProjectTeamEntity title = new ProjectTeamEntity(s);
                            title.setProjectTeamID(0);
                            projectTeamEntities1.add(0, title);
                            return Flowable.fromIterable(projectTeamEntities1);
                        }));
    }

    @Override
    public void searchProjectTeamList(String searchContent) {
        addDispose(Observable.fromIterable(this.mProjectTeamEntities)
                .filter(projectTeamEntity -> TextUtil.isEmpty(searchContent) || projectTeamEntity.getProjectTeamName().contains(searchContent))
                .toList()
                .toFlowable()
                .concatMap(this::convertList)
                .toList()
                .toFlowable()
                .compose(Network.networkIO())
                .subscribe(projectTeamEntities -> mView.setProjectTeamListData(projectTeamEntities), throwable -> {
                }));
    }
}
