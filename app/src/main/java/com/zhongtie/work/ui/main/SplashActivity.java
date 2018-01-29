package com.zhongtie.work.ui.main;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BasePresenterActivity;
import com.zhongtie.work.ui.login.LoginActivity;
import com.zhongtie.work.ui.main.presenter.SplashContract;
import com.zhongtie.work.ui.main.presenter.SplashPresenterImpl;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class SplashActivity extends BasePresenterActivity<SplashContract.Presenter> implements SplashContract.View {
    private LinearLayout mSplashSyncView;


    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mSplashSyncView = findViewById(R.id.splash_sync_view);
    }


    @Override
    protected void initData() {
        //权限申请
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        mPresenter.initSync();
                    } else {
                        showToast("请授权");
                    }
                }, throwable -> showToast("请授权"));
    }

    @Override
    public void showSync() {
        mSplashSyncView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSync() {
        mSplashSyncView.setVisibility(View.GONE);
    }

    @Override
    public void userLogin() {
        mSplashSyncView.setVisibility(View.GONE);
        startActivity(new Intent(getAppContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void startMainView() {
        mSplashSyncView.setVisibility(View.GONE);
        startActivity(new Intent(getAppContext(), MainActivity.class));
        finish();
    }

    @Override
    public void showRetryDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.splash_sync_dialog_title)
                .content(R.string.splash_sync_error_message)
                .positiveText(R.string.sync_retry)
                .cancelable(false)
                .negativeText(R.string.exit)
                .onPositive((dialog, which) -> {
                    dialog.cancel();
                    mPresenter.initSync();
                })
                .onNegative((dialog, which) -> {
                    dialog.cancel();
                    finish();
                })
                .build()
                .show();

    }

    @Override
    protected SplashContract.Presenter getPresenter() {
        return new SplashPresenterImpl();
    }
}
