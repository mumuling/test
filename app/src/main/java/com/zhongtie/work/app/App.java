package com.zhongtie.work.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.util.ImageConfigFactory;
import com.zhongtie.work.util.ToastUtil;

import static com.raizlabs.android.dbflow.config.FlowManager.destroy;

/**
 * Auth:Cheek
 * date:2018.1.8
 */

public class App extends Application {
    private static final String TAG = "App";

    private static App instance;

    private LoginUserInfoEntity userInfo;

    public static App getInstance() {
        return instance;
    }


    public LoginUserInfoEntity getUser() {
        return userInfo;
    }

    public void setUserInfo(LoginUserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initUtil();
        initDataBase();
        initNetwork();
        initServer();
        initPush();
    }

    private void initPush() {
    }

    private void initServer() {

    }

    private void initUtil() {
        ToastUtil.initContext(instance);
        Fresco.initialize(this, ImageConfigFactory.getImagePipelineConfig(this));
    }

    private void initDataBase() {
        initDB();
    }

    //初始化数据库
    public void initDB() {
//        writeCityDb();
        FlowConfig flowConfig = new FlowConfig.Builder(instance)
                .addDatabaseConfig(DatabaseConfig.builder(App.class)
                        .databaseName("company")
                        .build()).build();
        FlowManager.init(flowConfig);
//        FlowManager.getDatabase(App.class).reset(DatabaseConfig.builder(App.class)
//                .databaseName("company1").build());

    }


//    public void writeCityDb() {
//        File dbFile = this.getApplicationContext().getDatabasePath("company.db");
////        if (!dbFile.exists()) {
////            dbFile.mkdir();
////        }
////        File file2 = new File(dbFile.toString().replace("city", ""));
////        file2.mkdirs();
////        try {
////            dbFile.createNewFile();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        try {
//            File sqliteDb = new File(Environment.getExternalStorageDirectory() + "/zhongtie/company_db/company1.db");
//            InputStream is = new FileInputStream(sqliteDb);
//            OutputStream os = new FileOutputStream(dbFile);
//            byte[] buffer = new byte[10240];
//            int length;
//            while ((length = is.read(buffer)) > 0) {
//                os.write(buffer, 0, length);
//            }
//            os.flush();
//            os.close();
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //重新加载数据库
    public static void deleteDB() {
        destroy();// 释放引用，才能重新创建表
    }

    private void initNetwork() {


    }
}
