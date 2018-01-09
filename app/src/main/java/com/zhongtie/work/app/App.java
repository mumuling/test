package com.zhongtie.work.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhongtie.work.util.ImageConfigFactory;
import com.zhongtie.work.util.ToastUtil;

/**
 * Auth:Cheek
 * date:2018.1.8
 */

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
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
        
    }

    private void initNetwork() {
        
        
    }
}
