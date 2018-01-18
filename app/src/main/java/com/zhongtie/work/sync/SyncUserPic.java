package com.zhongtie.work.sync;

import android.os.Environment;

import com.zhongtie.work.app.Cache;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.NetworkUtil;
import com.zhongtie.work.network.api.SyncApi;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Flowable;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Date: 2018/1/17
 * @author Chaek
 */

public class SyncUserPic {

    private static final String TAG = "UserPicSync";
    private static SyncUserPic sUserPicSync;

    private ExecutorService mDownloadServer;
    private int companyId;
    private volatile boolean isRun;
    private File cacheFolderName;
    private volatile int downPosition;
    private String cacheFile = Environment.getExternalStorageDirectory().getPath() + "/zhongtie/user_image/";

    public static SyncUserPic getInstance() {
        if (sUserPicSync == null) {
            synchronized (SyncUserPic.class) {
                sUserPicSync = new SyncUserPic();
            }
        }
        return sUserPicSync;
    }

    public SyncUserPic() {
        mDownloadServer = Executors.newFixedThreadPool(3);
        cacheFolderName = new File(cacheFile);
        if (!cacheFolderName.exists()) {
            cacheFolderName.mkdirs();
        }
    }

    private OkHttpClient mOkHttpClient;

    private synchronized OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            try {
                mOkHttpClient = newOkHttpClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mOkHttpClient;
    }

    private OkHttpClient newOkHttpClient() throws Exception {
        return new OkHttpClient();
    }


    public void startDownload() {
        if (!isRun) {

            if (NetworkUtil.isWifi()) {
                L.e(TAG, "正在开始下载....");
                isRun = true;
                this.companyId = Cache.getSelectCompany();
                Flowable<List<String>> picList = Http.netServer(SyncApi.class).syncHeadPortraitUrl(companyId)
                        .map(new NetWorkFunc1<>());
                Flowable<List<String>> cacheList = Flowable.just(cacheFolderName)
                        .map(File::listFiles)
                        .flatMap(Flowable::fromArray)
                        .map(File::getName)
                        .toList()
                        .toFlowable();

                //执行下载 并比对本地是否存在
                Flowable.zip(picList, cacheList, (imgUrl, strings2) -> Flowable.fromIterable(imgUrl)
                        .filter(s -> {
                            String md5 = Util.md532(Util.extractFileNameWithoutSuffix(s));
                            return !strings2.contains(md5);
                        })
                        .toList()
                        .toFlowable()
                        .blockingSingle()).compose(Network.networkIO())
                        .subscribe(this::executeDownload, throwable -> isRun = false);
            } else {
                L.e(TAG, "不是wifi连接不进行照片同步");
            }
        }

    }

    private void executeDownload(List<String> strings) {
        L.e(TAG, "正在开始下载...." + strings.size() + "条数据");
//        for (int i = 0, count = Math.min(2, strings.size()); i < count; i++) {
//            mDownloadServer.execute(new DownRunnable(strings.get(i), new OnDownLoadCallBack() {
//                @Override
//                public void onDownComplete() {
//                    nextDown();
//                }
//
//                private void nextDown() {
//                    synchronized (this) {
//                        downPosition++;
//                        L.e(TAG, "正在开始下载第" + downPosition + "条数据");
//                        if (downPosition < strings.size()) {
//                            mDownloadServer.execute(new DownRunnable(strings.get(downPosition), this));
//                        } else {
//                            isRun = false;
//                        }
//                    }
//                }
//
//                @Override
//                public void onDownFail() {
//                    nextDown();
//                }
//            }));
//        }
    }

    private interface OnDownLoadCallBack {
        void onDownComplete();

        void onDownFail();
    }

    private class DownRunnable implements Runnable {
        private String downUrl;
        private OnDownLoadCallBack mOnDownLoadCallBack;

        public DownRunnable(String downUrl, OnDownLoadCallBack onDownLoadCallBack) {
            this.downUrl = downUrl;
            mOnDownLoadCallBack = onDownLoadCallBack;
        }

        @Override
        public void run() {
            L.e(TAG, downUrl + "正在下载");

            String url = downUrl.replace(".jpg", "_mini.jpg");
            final Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            Call call = getOkHttpClient().newCall(request);
            try {
                Response response = call.execute();
                if (response.isSuccessful()) {
                    //文件夹名称
                    File file = new File(cacheFolderName, Util.md532(Util.extractFileNameWithoutSuffix(downUrl)));
                    InputStream is;
                    byte[] buf = new byte[10 * 1024];
                    int len;
                    FileOutputStream fos;
                    double total = response.body().contentLength();
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file, false);
                    BufferedInputStream input = new BufferedInputStream(is);
                    while ((len = input.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    input.close();
                    fos.flush();
                    is.close();
                    fos.close();
                    response.close();
                    mOnDownLoadCallBack.onDownComplete();
                    L.e(TAG, downUrl + "下载成功、、、、、、、、");
                } else {
                    L.e(TAG, downUrl + "下载失败、、、、、、、、");
                    response.close();
                    mOnDownLoadCallBack.onDownFail();
                }
            } catch (Exception e) {
                e.printStackTrace();
                mOnDownLoadCallBack.onDownFail();
            }
        }
    }

}

