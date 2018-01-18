package com.zhongtie.work.task;

import android.os.AsyncTask;
import android.support.annotation.MainThread;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.db.CacheAddWrongTable;
import com.zhongtie.work.db.CacheAddWrongTable_Table;
import com.zhongtie.work.db.CacheSafeEventTable;
import com.zhongtie.work.db.CacheSafeEventTable_Table;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.upload.UploadUtil;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 上传离线的安全督导信息
 *
 * @author: Chaek
 * @date: 2018/1/18
 */

public class SyncWrongTask extends AsyncTask<String, String, String> {
    private static final String TAG = "SyncWrongTask";

    @MainThread
    public static void execute() {
        new SyncWrongTask().executeOnExecutor(ExecutorUtil.newSingleThreadExecutor(), null, null, null);
    }

    @Override
    protected String doInBackground(String... strings) {
        L.e(TAG, "正在执行同步违规操作....");
        Flowable.fromCallable(() -> SQLite.select().from(CacheAddWrongTable.class).where(CacheAddWrongTable_Table.status.eq(0)).queryList())
                .flatMap(Flowable::fromIterable)
                .flatMap(cacheAddWrongTable -> Http.netServer(UserApi.class)
                        .addWrong(cacheAddWrongTable.getUserId(), cacheAddWrongTable.getByUserId() + "", cacheAddWrongTable.getContent())
                        .map(integerResult -> {
                            cacheAddWrongTable.setAddWrongId(integerResult.getData());
                            cacheAddWrongTable.setStatus(1);
                            cacheAddWrongTable.save();
                            return integerResult.getCode();
                        }))
                .toList()
                .toFlowable()
                .subscribe(integers -> L.e(TAG, "违规操作上传成功"), throwable -> L.e(TAG, "违规操作上传失败"));

        return "";
    }

}
