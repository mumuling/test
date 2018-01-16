package com.zhongtie.work.ui.scan.info;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.db.CompanyUserData;
import com.zhongtie.work.db.CompanyUserData_Table;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import io.reactivex.Flowable;

/**
 * Auth:Cheek
 * date:2018.1.10
 */

class ScanInfoPresenterImpl extends BasePresenterImpl<ScanQRCodeInfoContract.View> implements ScanQRCodeInfoContract.Presenter {
    private String userId;

    @Override
    public void fetchQRCodeInfo(String qrcodeinfo) {
        int idIndex = qrcodeinfo.indexOf("id=");
        if (idIndex > 0) {
            userId = qrcodeinfo.substring(idIndex + 3, qrcodeinfo.length());
            Flowable.just(userId)
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
//                        mView.initFail();
                    });
        }

    }

    @Override
    public void addWrong(String content) {
        addDispose(Http.netServer(UserApi.class)
                .addWrong(userId, Cache.getUserID(), content)
                .compose(Network.networkConvertDialog(mView))
                .subscribe(s -> {
                }, throwable -> {
                }));


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
    }
}
