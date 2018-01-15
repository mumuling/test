package com.zhongtie.work.ui.scan.info;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.db.CompanyUserData;
import com.zhongtie.work.db.CompanyUserData_Table;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import io.reactivex.Flowable;

/**
 * Auth:Cheek
 * date:2018.1.10
 */

class ScanInfoPresenterImpl extends BasePresenterImpl<ScanQRCodeInfoContract.View> implements ScanQRCodeInfoContract.Presenter {
    @Override
    public void fetchQRCodeInfo(String qrcodeinfo) {
        int idIndex = qrcodeinfo.indexOf("id=");
        if (idIndex > 0) {
            String userId = qrcodeinfo.substring(idIndex + 3, qrcodeinfo.length());
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
