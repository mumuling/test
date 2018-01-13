package com.zhongtie.work.ui.main;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.model.CompanyEntity;
import com.zhongtie.work.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chaek
 */
public class MainActivity extends BaseActivity implements CompanySelectPopup.OnCompanySelectListener {


    private android.widget.LinearLayout mLvHomeMenu;
    private android.widget.LinearLayout mLvHomeTitle;
    private android.widget.TextView mUserCompanyName;
    private android.widget.LinearLayout mBottom;
    private android.widget.LinearLayout mQrCodeScan;
    private android.widget.LinearLayout mWordFeed;
    private android.widget.FrameLayout mFragmentContent;

    private ImageView mArrowView;


    public DrawerLayout mDrawerLayout;


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

        mLvHomeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectCompany();
            }
        });
    }


    private void showSelectCompany() {
        List<CompanyEntity> companyEntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setCompanyCode(i);
            companyEntity.setName("公司" + i);
            companyEntityList.add(companyEntity);
        }

        CompanySelectPopup companySelectPopup = new CompanySelectPopup(this, companyEntityList);
        companySelectPopup.showAsDropDown(mUserCompanyName);
        companySelectPopup.setOnCompanySelectListener(this);
        companySelectPopup.setOnDismissListener(() -> mArrowView.animate().rotation(0).setDuration(300).start());
        mArrowView.animate().rotation(180).setDuration(300).start();
    }


    @Override
    protected void initData() {

        Fragments.with(this)
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
        mUserCompanyName.setText(companyEntity.getName());
    }
}
