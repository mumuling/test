package com.zhongtie.work.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.list.OnListPopupListener;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.main.adapter.PopupWindowAdapter;
import com.zhongtie.work.ui.scan.ScanQRCodeActivity;
import com.zhongtie.work.util.ListPopupWindowUtil;

/**
 * @author Chaek
 */
public class MainActivity extends BaseActivity {


    private android.widget.LinearLayout mLvHomeMenu;
    private android.widget.LinearLayout mLvHomeTitle;
    private android.widget.TextView mUserCompanyName;
    private android.widget.LinearLayout mBottom;
    private android.widget.LinearLayout mQrCodeScan;
    private android.widget.LinearLayout mWordFeed;
    private android.widget.FrameLayout mFragmentContent;

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

        //点击按钮打开菜单
        mLvHomeMenu.setOnClickListener(v -> mDrawerLayout.openDrawer(Gravity.LEFT));

        mQrCodeScan.setOnClickListener(v -> startActivity(new Intent(getAppContext(), ScanQRCodeActivity.class)));

        findViewById(R.id.safe).setOnClickListener(this::showSafePopup);
        findViewById(R.id.quality).setOnClickListener(this::showQualityPopup);
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
}
