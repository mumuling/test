package com.zhongtie.work.ui.print;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class InstallUtil {
    /**
     * @param context
     * @param apkPath  要安装的APK
     * @param rootMode 是否是Root模式
     */
    public static void install(Context context, String apkPath, boolean rootMode) {
        if (rootMode) {
            installRoot(context, apkPath);
        } else {
            installNormal(context, apkPath);
        }
    }

    /**
     * 通过非Root模式安装
     *
     * @param context
     * @param apkPath
     */
    public static void install(Context context, String apkPath) {
        install(context, apkPath, false);
    }

    //普通安装
    private static void installNormal(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkUri = FileProvider.getUriForFile(context, App.getInstance().getString(R.string.kt_fileProvider), file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    //通过Root方式安装
    private static void installRoot(Context context, String apkPath) {
        Observable.just(apkPath)
                .map(mApkPath -> "pm install -r " + mApkPath)
                .map(SystemManager::RootCommand)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer == 0) {
                        Toast.makeText(context, "安装成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "root权限获取失败,尝试普通安装", Toast.LENGTH_SHORT).show();
                        install(context, apkPath);
                    }
                });
    }
}