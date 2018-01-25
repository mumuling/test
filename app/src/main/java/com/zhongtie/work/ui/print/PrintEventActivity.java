package com.zhongtie.work.ui.print;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.server.PrintDownServer;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.util.AppUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * 打印界面
 * date:2018.1.22
 *
 * @author Chaek
 */

public class PrintEventActivity extends BaseActivity {
    private PrintDownServer.DownloadBinder mDownloadBinder;
    public static final String KEY_EVENT_ID = "event_id";
    public static final String KEY_PRINT_TYPE = "print_type";

    public static void start(Context context, int printType, int eventId) {
        Intent starter = new Intent(context, PrintEventActivity.class);
        Bundle data = new Bundle();
        data.putInt(KEY_PRINT_TYPE, printType);
        data.putInt(KEY_EVENT_ID, eventId);
        starter.putExtras(data);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.common_activity;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.print_title));
        if (Build.VERSION.SDK_INT >= KITKAT) {
            //检测到安装过服务直接打开打印预览连接
            if (AppUtil.isPkgInstalled(PrintReviewFragment.PRINT_SERVICE)) {
                showPrintReview();
            } else {
                showDownLoadHPPrintServer();
            }
        } else {
            showPrintServerNotSupport();
        }
    }

    /**
     * 显示打印预览
     */
    private void showPrintReview() {
        Fragments.with(this)
                .fragment(PrintReviewFragment.class)
                .bundle(getIntent().getExtras())
                .into(R.id.fragment_content);
    }

    /**
     * 安卓版本不支持无线打印服务
     */
    public void showPrintServerNotSupport() {
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_tip_title)
                .content(R.string.tip_no_support_print_server)
                .positiveText(R.string.confirm)
                .cancelable(false)
                .onPositive((dialog, which) -> {
                    dialog.cancel();
                    finish();
                }).onNegative((dialog, which) -> dialog.dismiss())
                .build().show();
    }

    /**
     * 提示下载HP 无线打印服务
     */
    public void showDownLoadHPPrintServer() {
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_tip_title)
                .content(R.string.tip_download_print_server)
                .positiveText(R.string.download)
                .cancelable(false)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> downloadHPPrintPlugin())
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .build().show();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (PrintDownServer.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDownloadBinder = null;
        }
    };


    /**
     * 正在调用下载
     */
    private void downloadHPPrintPlugin() {
        Intent intent = new Intent(this, PrintDownServer.class);
        //下载服务
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        long id = mDownloadBinder.startDownload("http://f3.market.xiaomi.com/download/AppStore/09f835139320333b72748393bb9106e649e43a171/com.hp.android.printservice.apk");
        startCheckProgress(id);

    }

    /**
     * 开始监听进度
     *
     * @param downloadId 下载编号
     */
    private void startCheckProgress(long downloadId) {
        Observable
                .interval(100, 200, TimeUnit.MILLISECONDS, Schedulers.io())
                .filter(times -> mDownloadBinder != null)
                .map(i -> mDownloadBinder.getProgress(downloadId))
                //返回true就停止了,当进度>=100就是下载完成了
                .takeUntil(progress -> progress >= 100)
                .distinct()//去重复
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressObserver());
    }


    class ProgressObserver implements Observer<Integer> {

        @Override
        public void onSubscribe(Disposable d) {
            showLoadDialog(R.string.downloading);
        }

        @Override
        public void onNext(Integer progress) {
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
            cancelDialog();
            showToast(R.string.download_fail);
        }

        @Override
        public void onComplete() {
            cancelDialog();
            showPrintReview();
            showToast(R.string.download_complete);
        }
    }


}
