package com.zhongtie.work.ui.main;

import android.content.Intent;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.login.LoginActivity;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    final int count = 2;

    @Override
    protected void initData() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getAppContext(), LoginActivity.class));
                finish();
                if (mDisposable != null) {
                    mDisposable.clear();
                }
            }
        },0);
//        addDispose(Observable.interval(0, 1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(aLong -> count - aLong)
//                .subscribe(aLong -> {
//
//                }, throwable -> {
//                }));
    }
}
