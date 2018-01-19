package com.zhongtie.work.ui.scan.info;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.db.CacheCompanyTable;
import com.zhongtie.work.db.CacheAddWrongTable;
import com.zhongtie.work.db.CacheAddWrongTable_Table;
import com.zhongtie.work.db.CacheCompanyTable_Table;
import com.zhongtie.work.db.CompanyUserData;
import com.zhongtie.work.db.CompanyUserData_Table;
import com.zhongtie.work.db.CompanyUserWrongTable;
import com.zhongtie.work.db.CompanyUserWrongTable_Table;
import com.zhongtie.work.db.CompanyWorkTeamTable;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Auth:Cheek
 * date:2018.1.10
 */

class ScanInfoPresenterImpl extends BasePresenterImpl<ScanQRCodeInfoContract.View> implements ScanQRCodeInfoContract.Presenter {
    private Integer userId;

    @Override
    public void fetchQRCodeInfo(String qrcodeinfo) {
        int idIndex = qrcodeinfo.indexOf("id=");
        if (idIndex > 0) {
            userId = Integer.valueOf(qrcodeinfo.substring(idIndex + 3, qrcodeinfo.length()));
            addDispose(Flowable.just(userId)
                    .map(s -> SQLite.select().from(CompanyUserData.class)
                            .where(CompanyUserData_Table.id.eq(userId))
                            .querySingle())
                    .compose(Network.networkIO())
                    .subscribe(companyUserData -> {
                        if (companyUserData == null) {
                            mView.noFindUserInfo();
                        } else {
                            setUserInfo(companyUserData);
                        }
                    }, throwable -> {
                        mView.noFindUserInfo();
                    }));
        } else {
            mView.noFindUserInfo();
        }

    }

    @Override
    public void addWrong(String content) {
        CacheAddWrongTable addWrongTable = new CacheAddWrongTable();
        addWrongTable.setUserId(userId);
        addWrongTable.setByUserId(Integer.valueOf(Cache.getUserID()));
        addWrongTable.setContent(content);
        addWrongTable.setTime(TimeUtils.getFormatDateTime());

        addDispose(Http.netServer(UserApi.class)
                .addWrong(userId, Cache.getUserID(), content)
                .map(integerResult -> {
                    addWrongTable.setAddWrongId(integerResult.getData());
                    addWrongTable.save();
                    return integerResult;
                })
                .compose(Network.convertDialogTip(mView))
                .onErrorReturn(throwable -> {
                    addWrongTable.setStatus(0);
                    addWrongTable.save();
                    return 0;
                })
                .compose(Network.networkIO())
                .subscribe(s -> {
                    mView.showToast("添加成功");
                    mView.addWrongSuccess();
                    getUserWrongMessage();
                }, throwable -> mView.showToast(HttpException.getErrorMessage(throwable))));
    }

    private String getInsure(String insure) {
        String[] date = insure.split(",");
        if (date.length > 0) {
            StringBuffer string = new StringBuffer();
            for (int i = 0; i < date.length; i += 2) {
                string.append(date[i].trim());
                if (i + 1 < date.length) {
                    string.append("~");
                    string.append(date[i + 1].trim());
                    string.append("\n");
                }
            }
            if (string.length() > 0) {
                string.delete(string.length() - 2, string.length());
            }
            return string.toString();
        }
        return insure;
    }

    private void getCompanyName(int companyId) {
        addDispose(Flowable.fromCallable(() -> {
            CacheCompanyTable company = SQLite.select().from(CacheCompanyTable.class).where(CacheCompanyTable_Table.id.eq(companyId)).querySingle();
            if (company == null) {
                return "未知";
            }
            return company.getName();
        }).compose(Network.networkIO()).subscribe(s -> mView.setUserWorkTeam(s), throwable -> {

        }));
    }

    /**
     * 获取劳务公司
     *
     * @param companyId
     */
    private void getCompanyWorkName(String companyId) {
        if (TextUtil.isEmpty(companyId)) {
            mView.setUserUnit("");
            return;
        }
        addDispose(Flowable.fromCallable(() -> {
            CompanyWorkTeamTable company = SQLite.select().from(CompanyWorkTeamTable.class).where(CacheCompanyTable_Table.id.eq(Integer.valueOf(companyId))).querySingle();
            if (company == null) {
                return "未知";
            }
            return company.getName();
        }).compose(Network.networkIO()).subscribe(s -> mView.setUserUnit(s), throwable -> {
        }));
    }

    private void setUserInfo(CompanyUserData userData) {
        mView.setUserCardCode(userData.getIdencode());
        mView.setUserDuty(userData.getDuty());
//        mView.setUserHead(userData.getPhoto());
        mView.setUserHealth(userData.getHealth());
        mView.setUserInsure(getInsure(userData.getInsure()));
        mView.setUserLearn(TimeUtils.formatWrongTime(userData.getLearn()) + "至" + TimeUtils.formatWrongTime(userData.getLearn2()));
        mView.setUserName(userData.getName());
        mView.setUserOnJob(userData.getOnjob() == 1 ? "在职" : "离职");

        getCompanyName(userData.getCompany());
        getCompanyWorkName(userData.getWorkteam());
        mView.setUserWorkType(userData.getWorktype());
        mView.initSuccess();
        getUserWrongMessage();


    }

    private void getUserWrongMessage() {
        //读取=本地离线数据 以及离线添加未上次的信息
        Flowable<List<CompanyUserWrongTable>> cache = Flowable.fromCallable(() ->
                SQLite.select().from(CompanyUserWrongTable.class).where(CompanyUserWrongTable_Table.wrong_userid.eq(userId)).queryList());

        Flowable<List<CacheAddWrongTable>> localCache = Flowable.fromCallable(() ->
                SQLite.select().from(CacheAddWrongTable.class).where(CacheAddWrongTable_Table.userId.eq(userId)).queryList());

        addDispose(Flowable.zip(cache, localCache, (companyUserWrongTables, cacheAddWrongTables) -> {
            StringBuilder builder = getUserWrongAllList(companyUserWrongTables, cacheAddWrongTables);
            return builder.toString();
        }).compose(Network.networkIO())
                .subscribe(s -> mView.setUserWrongMessage(s), throwable -> {
                    mView.setUserWrongMessage(App.getInstance().getString(R.string.not_wrong_text));
                }));
    }

    /**
     * 获取违规情况 包括在线和离线情况
     *
     * @param onLineWrongList 在线的违规情况
     * @param officeWrongList 离线的违规情况
     * @return 获取去重之后的离线数据
     */
    @NonNull
    private StringBuilder getUserWrongAllList(List<CompanyUserWrongTable> onLineWrongList, List<CacheAddWrongTable> officeWrongList) {
        StringBuilder wrongSB = new StringBuilder();
        List<Integer> onLineWrongIdList = new ArrayList<>();
        for (int i = 0; i < onLineWrongList.size(); i++) {
            CompanyUserWrongTable onLineWrong = onLineWrongList.get(i);
            onLineWrongIdList.add(onLineWrong.getId());
            wrongSB.append(onLineWrong.getContent());
            wrongSB.append("\t\t(");
            wrongSB.append(TimeUtils.formatWrongTime(onLineWrong.getTime()));
            wrongSB.append(")\n");
            wrongSB.append("\n");

        }

        for (int i = 0; i < officeWrongList.size(); i++) {
            CacheAddWrongTable item = officeWrongList.get(i);
            if (onLineWrongIdList.contains(item.getAddWrongId())) {
                continue;
            }
            wrongSB.append(item.getContent());
            wrongSB.append("\t\t(");
            wrongSB.append(TimeUtils.formatWrongTime2(item.getTime()));
            wrongSB.append(")\n");
            wrongSB.append("\n");
        }
        if (wrongSB.length() == 0) {
            wrongSB.append(App.getInstance().getString(R.string.not_wrong_text));
        }
        return wrongSB;
    }
}
