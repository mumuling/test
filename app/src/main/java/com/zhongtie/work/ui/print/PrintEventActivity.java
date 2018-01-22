package com.zhongtie.work.ui.print;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * 打印界面
 * Auth:Cheek
 * date:2018.1.22
 */

public class PrintEventActivity extends BaseActivity {
    private long completeDownloadId;
    private File file;
    PrintDownServer.DownloadBinder mDownloadBinder;

    public static void start(Context context) {
        Intent starter = new Intent(context, PrintEventActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.common_activity;
    }

    @Override
    protected void initView() {
        Intent intent = new Intent(this, PrintDownServer.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);//绑定服务
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.print_title));
        if (Build.VERSION.SDK_INT >= KITKAT) {
            //检测到安装过服务直接打开打印预览连接
            if (isPkgInstalled("com.hp.android.printservice")) {
                showPrintReview();
            } else {
                showDownLoadHPPrintServer();
            }
        } else {
            showPrintServerNotSupport();
        }


    }

    private void showPrintReview() {
        Fragments.with(this)
                .fragment(PrintReviewFragment.class)
                .into(R.id.fragment_content);
    }

    /**
     * 安卓版本不支持无线打印服务
     */
    public void showPrintServerNotSupport() {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("检测到您的手机版本过低,无法支持打印服务")
                .positiveText("确定")
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
                .title("提示")
                .content("检测到您没有安装HP无线打印服务插件,请下载安装过后再试!")
                .positiveText("下载").cancelable(false)
                .negativeText("取消")
                .onPositive((dialog, which) -> {
                    downloadHPPrintPlugin();
                })
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

        long id = mDownloadBinder.startDownload("http://f3.market.xiaomi.com/download/AppStore/09f835139320333b72748393bb9106e649e43a171/com.hp.android.printservice.apk");
        startCheckProgress(id);

    }

    //开始监听进度
    private void startCheckProgress(long downloadId) {
        Observable
                .interval(100, 200, TimeUnit.MILLISECONDS, Schedulers.io())//无限轮询,准备查询进度,在io线程执行
                .filter(times -> mDownloadBinder != null)
                .map(i -> mDownloadBinder.getProgress(downloadId))//获得下载进度
                .takeUntil(progress -> progress >= 100)//返回true就停止了,当进度>=100就是下载完成了
                .distinct()//去重复
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressObserver());
    }


    //观察者
    private class ProgressObserver implements Observer<Integer> {

        @Override
        public void onSubscribe(Disposable d) {
            showLoadDialog("正在下载");
        }

        @Override
        public void onNext(Integer progress) {
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
            cancelDialog();
            showToast("下载出错");
        }

        @Override
        public void onComplete() {
            cancelDialog();
            showPrintReview();
            showToast("下载完成");
        }
    }

    public boolean isPkgInstalled(String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = this.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
