package com.zhongtie.work.task;

import android.os.AsyncTask;
import android.support.annotation.MainThread;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.db.CacheSafeEventTable;
import com.zhongtie.work.db.CacheSafeEventTable_Table;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.upload.UploadUtil;

import java.util.List;

import io.reactivex.Flowable;

/**
 * 上传离线的安全督导信息
 *
 * @author: Chaek
 * @date: 2018/1/18
 */

public class SyncOfficeEventTask extends AsyncTask<String, String, String> {
    private static final String TAG = "SyncOfficeEventTask";

    @MainThread
    public static void execute() {
        new SyncOfficeEventTask().executeOnExecutor(ExecutorUtil.newSingleThreadExecutor(), null, null, null);
    }

    @Override
    protected String doInBackground(String... strings) {
        L.d(TAG, "上传正在上传安全督导信息");
        List<CacheSafeEventTable> safeEventTables = SQLite.select().from(CacheSafeEventTable.class)
                .where(CacheSafeEventTable_Table.uploadStatus.eq(0))
                .queryList();
        if (!safeEventTables.isEmpty()) {
            //遍历上传
            Flowable.fromIterable(safeEventTables)
                    .flatMap(officeEvent ->
                            UploadUtil.uploadListJPGIDList(officeEvent.getImageList())
                                    .map(s -> {
                                        officeEvent.setPic(s);
                                        return officeEvent;
                                    })
                                    .map(CacheSafeEventTable::getOfficeEventMap)
                                    .flatMap(officeMap -> Http.netServer(SafeApi.class)
                                            .createEventList(officeMap)
                                            .map(new NetWorkFunc1<>())
                                            .map(eventId -> {
                                                officeEvent.setEventId(eventId);
                                                officeEvent.setUploadStatus(1);
                                                officeEvent.save();
                                                return officeEvent;
                                            })))
                    .toList()
                    .subscribe(cacheSafeEventTables -> {
                        L.d(TAG, "同步成功");
                    }, throwable -> {
                        L.d(TAG, "同步上传失败");
                    });
        }
        return "";
    }

}
