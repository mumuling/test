package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.zhongtie.work.app.App;
import com.zhongtie.work.util.L;

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
    private static final String TAG = "SwitchCompanyUtil";

    public static Flowable<String> switchCompany(int company) {
        FlowManager.getDatabase(CompanyDB.NAME).destroy();

        return Flowable.fromCallable(() -> { //公司文纪念
            File dbFile = App.getInstance().getDatabasePath("company.db");
            if (dbFile.exists()) {
                dbFile.delete();
            }
            try {
                File sourceDBFile = App.getInstance().getDatabasePath("company_" + company + ".db");
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
                L.e(TAG, "数据库转换成功");
            } catch (Exception e) {
                e.printStackTrace();
                L.e(TAG, "数据库转换失败");
            }

            return "";
        });
    }

    public static void changeCompany(int company) {
        switchCompany(company)
                .subscribe(s -> {
//                    FlowManager.destroy();
                    FlowManager.init(App.getInstance());
                }, throwable -> {

                });
    }
}
