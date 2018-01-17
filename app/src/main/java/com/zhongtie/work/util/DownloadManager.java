package com.zhongtie.work.util;

import android.os.Handler;
import android.os.Looper;

import com.zhongtie.work.app.App;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.network.HttpException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 下载Manager
 * 支持断点下载
 *
 * @author Chaek
 */
public class DownloadManager {
    private static final String TAG = "DownloadManager";

    private static DownloadManager downloadManager;

    public static DownloadManager getInstance() {
        if (downloadManager == null) {
            downloadManager = new DownloadManager();
        }
        return downloadManager;
    }

    public DownloadManager() {
        mDelivery = new Handler(Looper.getMainLooper());
    }


    /**
     * 主线程返回
     */
    private Handler mDelivery;
    /**
     * 下载的call
     */
    private Call downCall;
    private OkHttpClient mOkHttpClient;
    private long downLen = 0;

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


    /**
     * 异步下载文件
     *
     * @param callback
     */
    public void download(CompanyEntity company, final BaseResultCallback callback) {
        downLen = 0;
        //文件夹名称
        File file = App.getInstance().getDatabasePath("company_" + company.getId() + ".db");
        String downUrl = company.getDburl();
        final Request request = new Request.Builder()
                .get()
                .url(downUrl)
                .build();
        Call call = getOkHttpClient().newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                InputStream is = null;
                byte[] buf = new byte[10 * 1024];
                int len;
                FileOutputStream fos = null;
                double total = response.body().contentLength();
                if (total <= 0) {
                    sendFailedStringCallback(response.request(), new NullPointerException(""), callback);
                    return;
                }
                is = response.body().byteStream();
                fos = new FileOutputStream(file, false);
                BufferedInputStream input = new BufferedInputStream(is);
                while ((len = input.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    downLen += len;
                }
                input.close();
                fos.flush();
                is.close();
                fos.close();
                response.close();
            } else {
                L.e(TAG, "下载失败---------");
                response.close();
                call.cancel();
                throw new HttpException("同步失败", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpException("同步失败", 0);
        }


    }

    /**
     * 下载失败ui线程回调
     */
    private void sendFailedStringCallback(final Request request, final Exception e, final BaseResultCallback callback) {
        mDelivery.post(() -> {
            if (callback != null) {
                callback.onError(request, e);
            }
        });
    }

    /**
     * 下载成功ui线程回调
     */
    private void sendSuccessResultCallback(final File object, final BaseResultCallback<File> callback) {
        mDelivery.post(() -> {
            if (callback != null) {
                callback.onResponse(object);
            }
        });
    }


    public static abstract class BaseResultCallback<T> {

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);

        public abstract void onProgress(double total, double current);

    }


    private <T> void sendProgressCallBack(final double total, final double current, final BaseResultCallback<T> callBack) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onProgress(total, current);
                }
            }
        });
    }




    /**
     * 取消下载
     */
    public void cancel() {
        if (downCall != null) {
            downCall.cancel();
        }
    }

    private String md532(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return result.toLowerCase();
    }


}