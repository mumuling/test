package com.zhongtie.work.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.BuildConfig;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.data.LoginUserInfoEntity_Table;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.util.ImageConfigFactory;
import com.zhongtie.work.util.ToastUtil;

import io.reactivex.Flowable;

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
        initUser();
        initUtil();
        initDataBase();
        initNetwork();
        initServer();
        initPush();
    }

    private void initUser() {
        Flowable.fromCallable(() -> {
            if (Cache.isUserLogin()) {
                LoginUserInfoEntity userInfoEntity = SQLite.select().from(LoginUserInfoEntity.class)
                        .where(LoginUserInfoEntity_Table.id.eq(Cache.getUserID())).querySingle();
                setUserInfo(userInfoEntity);
                return userInfoEntity;
            }
            return null;
        }).compose(Network.networkIO())
                .subscribe(loginUserInfoEntity -> {
                }, throwable -> {

                });
    }

    private void initPush() {
    }

    private void initServer() {

    }

    private void initUtil() {
        ToastUtil.initContext(instance);
        Fresco.initialize(this, ImageConfigFactory.getImagePipelineConfig(this));

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("日志")
                .build();
        //只有I级别以上才能打印
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

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

    }


    private void initNetwork() {


    }
}
