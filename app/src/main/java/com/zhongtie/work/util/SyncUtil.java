package com.zhongtie.work.util;

import android.support.annotation.WorkerThread;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.db.CompanySync;
import com.zhongtie.work.db.CompanySync_Table;
import com.zhongtie.work.db.SwitchCompanyUtil;
import com.zhongtie.work.db.ZhongtieDb;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.widget.DownloadManager;

import java.util.List;

import io.reactivex.Flowable;

import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_COMPANY;

/**
 * Auth:Cheek
 * date:2018.1.16
 */

public class SyncUtil {
    private static final String TAG = "SyncUtil";

    /**
     * 同步公司数据
     * 并同步下载数据库文件
     */
    public static Flowable<List<CompanyEntity>> syncCompanyList() {
        L.e(TAG, "正在同步公司信息--------");
        return Http.netServer(UserApi.class).fetchCompanyList(0)
                .map(new NetWorkFunc1<>())
                .flatMap(companyEntities -> {
                    FlowManager.getDatabase(ZhongtieDb.class).executeTransaction(databaseWrapper ->
                            FastStoreModelTransaction.saveBuilder(FlowManager.getModelAdapter(CompanyEntity.class)).
                                    addAll(companyEntities).build().execute(databaseWrapper));
                    return Flowable.fromIterable(companyEntities);
                })
                .map(companyEntity -> {
                    //读取数据库同步时间
                    CompanySync companySyn = SQLite.select().from(CompanySync.class).where(CompanySync_Table.companyID.eq(companyEntity.getId())).querySingle();
                    if (companySyn != null) {
                        long oldTime = companySyn.getSynTime();
                        long newTime = TimeUtils.converter(companyEntity.getDbupdatetime());
                        L.e("--------", newTime + " 数据库转换时间");
                        if (newTime > oldTime) {
                            downCompanyDB(companyEntity);
                        }
                        SQLite.update(CompanySync.class)
                                .set(CompanySync_Table.synTime.eq(newTime))
                                .where(CompanySync_Table.companyID.eq(companySyn.getCompanyID()))
                                .execute();
                    } else {
                        downCompanyDB(companyEntity);
                        companySyn = new CompanySync();
                        companySyn.setCompanyID(companyEntity.getId());
                        companySyn.setSynTime(System.currentTimeMillis());
                        companySyn.save();
                    }
                    return companyEntity;
                }).toList().toFlowable();
    }

    /**
     * 下载 最新的数据库
     * 如果下载的是本地选择的最新的数据库
     *
     * @param companyEntity 公司信息
     */
    @WorkerThread
    private static void downCompanyDB(CompanyEntity companyEntity) {
        if (!TextUtil.isEmpty(companyEntity.getDburl())) {
            L.e("-----------", "正在下载" + companyEntity.getDburl());
            DownloadManager.getInstance().download(companyEntity, null);
            int companyId = SharePrefUtil.getUserPre().getInt(LOGIN_USER_COMPANY, 0);
            if (companyId == companyEntity.getId()) {
                SwitchCompanyUtil.switchCompany(companyEntity.getId())
                        .map(s -> {
                            FlowManager.init(App.getInstance());
                            return "";
                        }).blockingFirst();
            }
            L.e("-----------", "下载成功" + companyEntity.getDburl());

        }
    }
}
