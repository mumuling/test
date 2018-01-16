package com.zhongtie.work.ui.scan.info;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.CompanyEntity_Table;
import com.zhongtie.work.db.CacheAddWrongTable;
import com.zhongtie.work.db.CacheAddWrongTable_Table;
import com.zhongtie.work.db.CompanyUserData;
import com.zhongtie.work.db.CompanyUserData_Table;
import com.zhongtie.work.db.CompanyUserWrongTable;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.TimeUtils;

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
                            .where(CompanyUserData_Table.id.eq(1))
                            .querySingle())
                    .compose(Network.netorkIO())
                    .subscribe(companyUserData -> {
                        if (companyUserData == null) {
                            mView.noFindUserInfo();
                        } else {
                            setUserInfo(companyUserData);
                        }
                    }, throwable -> {
                        mView.noFindUserInfo();
                    }));
        }

    }

    @Override
    public void addWrong(String content) {
        CacheAddWrongTable addWrongTable = new CacheAddWrongTable();
        addWrongTable.setId(userId);
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
                .compose(Network.networkConvertDialog(mView))
                .onErrorReturn(throwable -> {
                    addWrongTable.setStatus(0);
                    addWrongTable.save();
                    return 0;
                })
                .compose(Network.netorkIO())
                .subscribe(s -> {
                    mView.showToast("添加成功");
                    mView.addWrongSuccess();
                    getUserWrongMessage();
                }, throwable -> mView.showToast(HttpException.getErrorMessage(throwable))));
    }

    private void setUserInfo(CompanyUserData userData) {
        mView.setUserCardCode(userData.getIdencode());
        mView.setUserDuty(userData.getDuty());
        mView.setUserHead(userData.getPhoto());
        mView.setUserHealth(userData.getHealth());
        mView.setUserInsure(userData.getInsure());
        mView.setUserLearn(userData.getLearn());
        mView.setUserName(userData.getName());
        mView.setUserOnJob(userData.getOnjob() == 1 ? "在职" : "离职");
        mView.setUserUnit(userData.getUnit());
        mView.setUserWorkTeam(userData.getWorkteam());
        mView.setUserWorkType(userData.getWorktype());
        mView.initSuccess();
        getUserWrongMessage();


    }

    private void getUserWrongMessage() {
        //读取=本地离线数据 以及离线添加未上次的信息
        Flowable<List<CompanyUserWrongTable>> cache = Flowable.fromCallable(() -> SQLite.select().from(CompanyUserWrongTable.class).where(CompanyEntity_Table.id.eq(userId)).queryList());
        Flowable<List<CacheAddWrongTable>> localCache = Flowable.fromCallable(() -> SQLite.select().from(CacheAddWrongTable.class).where(CacheAddWrongTable_Table.userId.eq(userId)).queryList());


        addDispose(Flowable.zip(cache, localCache, (companyUserWrongTables, cacheAddWrongTables) -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < companyUserWrongTables.size(); i++) {
                CompanyUserWrongTable userWrongTable = companyUserWrongTables.get(i);
                builder.append(userWrongTable.getContent());
                builder.append(TimeUtils.formatWrongTime(userWrongTable.getTime()));
                builder.append("\n");
            }
            for (int i = 0; i < cacheAddWrongTables.size(); i++) {
                CacheAddWrongTable item = cacheAddWrongTables.get(i);
                boolean exist = false;
                for (int j = 0; j < companyUserWrongTables.size(); j++) {
                    CompanyUserWrongTable userWrongTable = companyUserWrongTables.get(i);
                    if (userWrongTable.getId() == item.getAddWrongId()) {
                        exist = true;
                    }
                }
                if (exist) {
                    continue;
                }
                builder.append(item.getContent());
                builder.append(TimeUtils.formatWrongTime2(item.getTime()));
                builder.append("\n");
            }
            if (builder.length() == 0) {
                builder.append(App.getInstance().getString(R.string.not_wrong_text));
            }
            return builder.toString();
        }).compose(Network.netorkIO())
                .subscribe(s -> mView.setUserWrongMessage(s), throwable -> {
                    mView.setUserWrongMessage(App.getInstance().getString(R.string.not_wrong_text));
                }));
    }
}
