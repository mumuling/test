package com.zhongtie.work.ui.main;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.db.CacheCompanyTable;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.event.ExitEvent;
import com.zhongtie.work.list.OnRefreshListener;
import com.zhongtie.work.ui.base.BasePresenterActivity;
import com.zhongtie.work.ui.main.presenter.MainContract;
import com.zhongtie.work.ui.main.presenter.MainPresenterImpl;
import com.zhongtie.work.util.L;

import java.util.List;

/**
 * 主界面
 *
 * @author Chaek
 */
public class MainActivity extends BasePresenterActivity<MainContract.Presenter> implements CompanySelectPopup.OnCompanySelectListener,
        MainContract.View, OnRefreshListener {
    private static final String TAG = "MainActivity";
    public static final int EXIT_LOAD_TIME = 2000;
    private TextView mUserCompanyName;
    private ImageView mArrowView;
    public DrawerLayout mDrawerLayout;
    private List<CacheCompanyTable> companyEntityList;
    private MainFragment mainFragment;

    private long mExitTime = 0;

    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        LinearLayout lvHomeMenu = findViewById(R.id.lv_home_menu);
        LinearLayout lvHomeTitle = findViewById(R.id.lv_home_title);
        mUserCompanyName = findViewById(R.id.user_company_name);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mArrowView = findViewById(R.id.arrowView);

        //点击按钮打开菜单
        lvHomeMenu.setOnClickListener(v -> mDrawerLayout.openDrawer(Gravity.START));
        lvHomeTitle.setOnClickListener(view -> showSelectCompany());
    }


    private void showSelectCompany() {
        CompanySelectPopup companySelectPopup = new CompanySelectPopup(this, companyEntityList);
        companySelectPopup.showAsDropDown(mUserCompanyName);
        companySelectPopup.setOnCompanySelectListener(this);
        companySelectPopup.setOnDismissListener(() -> mArrowView.animate().rotation(0).setDuration(300).start());
        mArrowView.animate().rotation(180).setDuration(300).start();
    }


    @Override
    protected void initData() {
        mPresenter.fetchInitData();

        mainFragment = (MainFragment) Fragments.with(this)
                .fragment(MainFragment.class)
                .single(false)
                .into(R.id.fragment_content);

        Fragments.with(this)
                .fragment(MenuFragment.class)
                .single(false)
                .into(R.id.nav_view);
    }

    @Override
    public void onSelectCompany(CacheCompanyTable companyEntity, int position) {
        mPresenter.switchSelectCompany(companyEntity);
    }

    @Override
    public void setUserCompany(String company) {
        mUserCompanyName.setText(company);
    }

    @Override
    public void setAllCompanyList(List<CacheCompanyTable> allCompanyList) {
        this.companyEntityList = allCompanyList;
        if (mainFragment != null) {
            mainFragment.onRefreshComplete();
        }
    }

    @Override
    public void setUserInfo(LoginUserInfoEntity userInfo) {
    }

    @Override
    public void onSyncCompanySuccess() {
        L.d(TAG, "onSyncCompanySuccess: 同步刷新成功");
        if (mainFragment != null) {
            mainFragment.onRefreshComplete();
        }
    }

    @Override
    public void onSyncCompanyFail() {
        if (mainFragment != null) {
            mainFragment.onRefreshComplete();
        }
    }

    @Override
    protected MainContract.Presenter getPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void onRefresh() {
        mPresenter.fetchInitData();
    }

    @Override
    public void onRefreshComplete() {
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > EXIT_LOAD_TIME) {
                showToast(R.string.exit_app);
                mExitTime = System.currentTimeMillis();
            } else {
                new ExitEvent().post();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
