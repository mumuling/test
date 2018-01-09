package com.zhongtie.work.ui.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.main.adapter.MenuItemView;
import com.zhongtie.work.widget.BaseImageView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class MenuFragment extends BaseFragment implements OnRecyclerItemClickListener {
    private DrawerLayout mDrawerLayout;

    private RecyclerView mList;
    private List<Pair<String, Integer>> mMenuItemList = new ArrayList<>();
    private CommonAdapter mAdapter;

    private View mHeadView;
    private com.zhongtie.work.widget.BaseImageView mHead;

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
        mHeadView = LayoutInflater.from(getAppContext()).inflate(R.layout.layout_home_left_head, mList, false);
        mHead = mHeadView.findViewById(R.id.head);
        if (mUserHead != null) {
            mHead.setImageURI(Uri.parse(mUserHead));
        }
    }


    @Override
    protected void initData() {
        fetchHomeItemList();
        mAdapter = new CommonAdapter(mMenuItemList).register(MenuItemView.class);
        mAdapter.addHeaderView(mHeadView);
        mAdapter.setOnItemClickListener(this);
        mList.setAdapter(mAdapter);
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

//        switch (pair.first) {
//            case "个人信息":
//                break;
//
//            case "统计表":
//                break;
//            case "个人信息":
//                break;
//            default:
//        }
    }
}
