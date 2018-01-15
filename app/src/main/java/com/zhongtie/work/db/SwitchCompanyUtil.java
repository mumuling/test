package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.zhongtie.work.app.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Flowable;

/**
 * Auth:Cheek
 * date:2018.1.14
 */

public class SwitchCompanyUtil {

    private Flowable<String> switchCompany(int company) {
        return Flowable.fromCallable(() -> { //公司文纪念
            File dbFile = App.getInstance().getDatabasePath("company.db");
            try {
                File sourceDBFile = App.getInstance().getDatabasePath("company" + company + ".db");
                InputStream is = new FileInputStream(sourceDBFile);
                OutputStream os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[10240];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        });
    }

    public void changeCompany(int company) {
        switchCompany(company)
                .subscribe(s -> {
                    FlowManager.reset();
                    FlowManager.init(App.getInstance());
                }, throwable -> {

                });
    }
}
