package com.zhongtie.work.ui.main;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.ImageView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.db.CompanyUserData;
import com.zhongtie.work.list.OnRefreshListener;
import com.zhongtie.work.ui.base.BasePresenterActivity;

import java.util.List;

/**
 * @author Chaek
 */
public class MainActivity extends BasePresenterActivity<MainContract.Presenter> implements CompanySelectPopup.OnCompanySelectListener, MainContract.View
        , OnRefreshListener {

    private static final String TAG = "MainActivity";

    private android.widget.LinearLayout mLvHomeMenu;
    private android.widget.LinearLayout mLvHomeTitle;
    private android.widget.TextView mUserCompanyName;
    private android.widget.LinearLayout mBottom;
    private android.widget.LinearLayout mQrCodeScan;
    private android.widget.LinearLayout mWordFeed;
    private android.widget.FrameLayout mFragmentContent;

    private ImageView mArrowView;
    public DrawerLayout mDrawerLayout;

    private List<CompanyEntity> companyEntityList;

    private MainFragment mainFragment;


    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mLvHomeMenu = findViewById(R.id.lv_home_menu);
        mLvHomeTitle = findViewById(R.id.lv_home_title);
        mUserCompanyName = findViewById(R.id.user_company_name);
        mBottom = findViewById(R.id.bottom);
        mFragmentContent = findViewById(R.id.fragment_content);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mArrowView = (ImageView) findViewById(R.id.arrowView);

        //点击按钮打开菜单
        mLvHomeMenu.setOnClickListener(v -> mDrawerLayout.openDrawer(Gravity.LEFT));

        mLvHomeTitle.setOnClickListener(view -> showSelectCompany());


        showToast(SQLite.selectCountOf().from(CompanyUserData.class)
                .count() + "条");
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
    public void onSelectCompany(CompanyEntity companyEntity, int position) {
        mPresenter.switchSelectCompany(companyEntity);
    }

    @Override
    public void setUserCompany(String company) {
        mUserCompanyName.setText(company);
    }

    @Override
    public void setAllCompanyList(List<com.zhongtie.work.data.CompanyEntity> allCompanyList) {
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
}
