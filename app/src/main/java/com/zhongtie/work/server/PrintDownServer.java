package com.zhongtie.work.server;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LongSparseArray;

import com.zhongtie.work.ui.print.SystemManager;
import com.zhongtie.work.util.IOUtils;
import com.zhongtie.work.util.InstallUtil;
import com.zhongtie.work.util.Util;

import java.io.File;

/**
 * 下载打印
 * date:2018.1.22
 *
 * @author Chaek
 */

public class PrintDownServer extends Service {
    private DownloadManager mDownloadManager;
    private DownloadBinder mBinder = new DownloadBinder();
    private LongSparseArray<String> mApkPaths;
    private boolean mIsRoot = false;
    private DownloadFinishReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        mApkPaths = new LongSparseArray<>();
        //注册下载完成的广播
        mReceiver = new DownloadFinishReceiver();
        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public class DownloadBinder extends Binder {
        /**
         * 下载
         *
         * @param apkUrl 下载的url
         */
        public long startDownload(String apkUrl) {
            IOUtils.clearApk(getApplication(), Util.md532(apkUrl) + ".apk");
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), Util.md532(apkUrl) + ".apk");
            request.setDestinationUri(Uri.fromFile(file));

            //添加请求 开始下载
            long downloadId = mDownloadManager.enqueue(request);
            Log.d("DownloadBinder", file.getAbsolutePath());
            mApkPaths.put(downloadId, file.getAbsolutePath());
            return downloadId;
        }

        public void setInstallMode(boolean isRoot) {
            mIsRoot = isRoot;
        }

        /**
         * 获取进度信息
         *
         * @param downloadId 要获取下载的id
         * @return 进度信息 max-100
         */
        public int getProgress(long downloadId) {
            //查询进度
            DownloadManager.Query query = new DownloadManager.Query()
                    .setFilterById(downloadId);
            Cursor cursor = null;
            int progress = 0;
            try {
                cursor = mDownloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {
                    //当前的下载量
                    int downloadSoFar = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    //文件总大小
                    int totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    progress = (int) (downloadSoFar * 1.0f / totalBytes * 100);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return progress;
        }

    }

    private class DownloadFinishReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //下载完成的广播接收者
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            String apkPath = mApkPaths.get(completeDownloadId);
            Log.d("DownloadFinishReceiver", apkPath);
            if (!apkPath.isEmpty()) {
                //提升读写权限,否则可能出现解析异常
                SystemManager.setPermission(apkPath);
                InstallUtil.install(context, apkPath, mIsRoot);
            } else {
                Log.e("DownloadFinishReceiver", "apkPath is null");
            }
        }
    }
}
