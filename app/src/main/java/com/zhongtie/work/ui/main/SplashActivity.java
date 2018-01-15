package com.zhongtie.work.ui.main;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BasePresenterActivity;
import com.zhongtie.work.ui.login.LoginActivity;

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
    private ProgressBar mLoadingViewImage;
    private TextView mMzwLoadingText;


    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

        mSplashSyncView = (LinearLayout) findViewById(R.id.splash_sync_view);
        mLoadingViewImage = (ProgressBar) findViewById(R.id.loading_view_image);
        mMzwLoadingText = (TextView) findViewById(R.id.mzw_loading_text);

    }


    @Override
    protected void initData() {
        mPresenter.initSync();
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mDisposable != null) {
//                    mDisposable.clear();
//                }
//            }
//        }, 0);
////        addDispose(Observable.interval(0, 1, TimeUnit.SECONDS)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .map(aLong -> count - aLong)
////                .subscribe(aLong -> {
////
////                }, throwable -> {
////                }));
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
