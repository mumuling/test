package com.zhongtie.work.ui.main;

import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.main.adapter.HomeItemView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class MainFragment extends BaseFragment {
    private RecyclerView mList;

    private List<Pair<String, Integer>> mHomeItemList = new ArrayList<>();
    private CommonAdapter mAdapter;

    @Override
    public int getLayoutViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new GridLayoutManager(getAppContext(), 3));
    }

    @Override
    protected void initData() {
        fetchHomeItemList();
        mAdapter = new CommonAdapter(mHomeItemList).register(HomeItemView.class);
        mList.setAdapter(mAdapter);
    }

    /**
     * 获取每页展示list
     * 使用RxJava 过滤操作
     */
    private void fetchHomeItemList() {
        TypedArray arr = getResources().obtainTypedArray(R.array.home_items_icon);
        String[] names = getResources().getStringArray(R.array.home_item_name);
        mHomeItemList = Observable.range(0, names.length)
                .map(it -> new Pair<>(names[it], arr.getResourceId(it, 0)))
                .toList()
                .blockingGet();
        arr.recycle();
    }

}
