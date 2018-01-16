package com.zhongtie.work.ui.main;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BasePresenterActivity;
import com.zhongtie.work.ui.login.LoginActivity;
import com.zhongtie.work.util.FileUtils;
import com.zhongtie.work.widget.ProgressWheel;

import static com.zhongtie.work.util.ToastUtil.showToast;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class SplashActivity extends BasePresenterActivity<SplashContract.Presenter> implements SplashContract.View {
    private TextView mTvSplashTitle;
    private TextView mTvSplashTitle2;
    private ImageView mImgSplashIcon;
    private TextView mTvSplashCopyRight;
    private LinearLayout mSplashSyncView;
    private ProgressWheel mLoadingViewImage;
    private TextView mMzwLoadingText;


    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mSplashSyncView = (LinearLayout) findViewById(R.id.splash_sync_view);
        mLoadingViewImage = (ProgressWheel) findViewById(R.id.loading_view_image);
        mMzwLoadingText = (TextView) findViewById(R.id.mzw_loading_text);
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
    protected SplashContract.Presenter getPresenter() {
        return new SplashPresenterImpl();
    }
}
