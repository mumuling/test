package com.zhongtie.work.ui.main;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.model.CompanyEntity;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.scan.ScanQRCodeActivity;
import com.zhongtie.work.util.ListPopupWindowUtil;

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
        mQrCodeScan = findViewById(R.id.qr_code_scan);
        mWordFeed = findViewById(R.id.word_feed);
        mFragmentContent = findViewById(R.id.fragment_content);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mArrowView = (ImageView) findViewById(R.id.arrowView);

        //点击按钮打开菜单
        mLvHomeMenu.setOnClickListener(v -> mDrawerLayout.openDrawer(Gravity.LEFT));

        mQrCodeScan.setOnClickListener(v -> startActivity(new Intent(getAppContext(), ScanQRCodeActivity.class)));

        findViewById(R.id.safe).setOnClickListener(this::showSafePopup);
        findViewById(R.id.quality).setOnClickListener(this::showQualityPopup);
        mLvHomeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectCompany();
            }
        });
    }

    /**
     * 质量弹窗选择
     */
    private void showQualityPopup(View v) {
        ListPopupWindowUtil.showListPopupWindow(v, Gravity.TOP, new String[]{"质量督导", "质量控制"}, (item, position) -> {

        });
    }

    /**
     * 安全弹窗选择
     */
    private void showSafePopup(View v) {
        ListPopupWindowUtil.showListPopupWindow(v, Gravity.TOP, new String[]{"安全督导", "奖惩流程", "文件签认"}, (item, position) -> {

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
