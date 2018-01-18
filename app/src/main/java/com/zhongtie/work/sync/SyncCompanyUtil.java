package com.zhongtie.work.sync;

import android.support.annotation.WorkerThread;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.db.CompanySync;
import com.zhongtie.work.db.CompanySync_Table;
import com.zhongtie.work.db.CompanyUserGroupTable;
import com.zhongtie.work.db.SwitchCompanyUtil;
import com.zhongtie.work.db.ZhongtieDb;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.util.DownloadManager;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;

import java.util.List;

import io.reactivex.Flowable;

import static com.zhongtie.work.ui.login.LoginPresenter.SELECT_COMPANY_ID;

/**
 * Auth:Cheek
 * date:2018.1.16
 */

public class SyncCompanyUtil {
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
            int companyId = SharePrefUtil.getUserPre().getInt(SELECT_COMPANY_ID, 0);
            if (companyId == companyEntity.getId()) {
                SwitchCompanyUtil.switchCompany(companyEntity.getId())
                        .map(s -> {
                            FlowManager.init(App.getInstance());
                            return "";
                        }).blockingSingle();
                findUserLeaderGroup(Integer.valueOf(Cache.getUserID()));
            }
            L.e("-----------", "下载成功" + companyEntity.getDburl());

        }
    }

    /**
     * 数据查找用户所属分组
     *
     * @param userId 用户ID
     */
    @WorkerThread
    public static void findUserLeaderGroup(int userId) {
        List<CompanyUserGroupTable> userGroupTables = SQLite.select().from(CompanyUserGroupTable.class).queryList();
        for (int i = 0, count = userGroupTables.size(); i < count; i++) {
            CompanyUserGroupTable userGroupTable = userGroupTables.get(i);
            List<Integer> groupUser = userGroupTable.getUserList();
            //判断是否是leader
            if (groupUser.contains(userId)) {
                if (userGroupTable.getLeaderflag() == 1) {
                    //保存并退出循环
                    SharePrefUtil.getUserPre().putInt(Cache.KEY_IS_LEADER, 1);
                    break;
                } else {
                    SharePrefUtil.getUserPre().putInt(Cache.KEY_IS_LEADER, 0);
                }
            }
        }
    }
}
