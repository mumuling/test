package com.zhongtie.work.ui.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.event.ExitEvent;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.login.LoginActivity;
import com.zhongtie.work.ui.main.adapter.MenuItemView;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.ui.setting.NoticeSettingFragment;
import com.zhongtie.work.ui.setting.VersionFragment;
import com.zhongtie.work.ui.statistics.StatisticsActivity;
import com.zhongtie.work.ui.user.UserInfoActivity;
import com.zhongtie.work.widget.BaseImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 首页左侧菜单
 * Date: 2018/1/9
 *
 * @author Chaek
 */
public class MenuFragment extends BaseFragment implements OnRecyclerItemClickListener {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mList;
    private List<Pair<String, Integer>> mMenuItemList = new ArrayList<>();
    private View mHeadView;
    private BaseImageView mHead;
    private String mUserHead;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity mMainActivity = (MainActivity) context;
            mDrawerLayout = mMainActivity.mDrawerLayout;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDrawerLayout = null;
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.fragment_menu;
    }

    /**
     * @param head 头像地址
     */
    public void setUserHead(String head) {
        this.mUserHead = head;
        if (mHead != null) {
            mHead.setImageURI(Uri.parse(mUserHead));
        }
    }

    @Override

    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_left_head, mList, false);
        mHead = mHeadView.findViewById(R.id.head);
        try {
            if (App.getInstance().getUser() != null) {
                mHead.loadUserCard(App.getInstance().getUser().getIdentity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initData() {
        fetchHomeItemList();
        CommonAdapter adapter = new CommonAdapter(mMenuItemList).register(MenuItemView.class);
        adapter.addHeaderView(mHeadView);
        adapter.setOnItemClickListener(this);
        mList.setAdapter(adapter);
    }

    /**
     * 获取每页展示list
     * 使用RxJava 过滤操作
     */
    private void fetchHomeItemList() {
        TypedArray arr = getResources().obtainTypedArray(R.array.menu_items_icon);
        String[] names = getResources().getStringArray(R.array.menu_item_name);
        mMenuItemList = Observable.range(0, names.length)
                .map(it -> new Pair<>(names[it], arr.getResourceId(it, 0)))
                .toList()
                .blockingGet();
        arr.recycle();
    }

    @Override
    public void onClick(Object t, int index) {
        Pair<String, Integer> pair = mMenuItemList.get(index);
        mDrawerLayout.closeDrawers();
        switch (pair.first) {
            case "个人信息":
                UserInfoActivity.newInstance(getActivity());
                break;
            case "统计表":
                StatisticsActivity.newInstance(getActivity());
                break;
            case "通知设置":
                CommonFragmentActivity.newInstance(getActivity(), NoticeSettingFragment.class, getString(R.string.notice_setting_title));
                break;
            case "版本信息":
                CommonFragmentActivity.newInstance(getActivity(), VersionFragment.class, getString(R.string.version_info_title));
                break;
            case "注销":
                exitLogin();
                break;
            case "退出":
                exitApp();
                break;
            default:
        }
    }

    private void exitApp() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_tip)
                .content(R.string.dialog_tip_exit_app)
                .positiveText(R.string.exit)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    dialog.cancel();
                    new ExitEvent().post();
                    getActivity().finish();
                }).onNegative((dialog, which) -> dialog.dismiss())
                .build().show();

    }

    private void exitLogin() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_tip)
                .content(R.string.dialog_tip_exit_user_login)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    Cache.exitLogin();
                    LoginActivity.newInstance(getActivity());
                }).onNegative((dialog, which) -> dialog.dismiss()).build().show();

    }
}
