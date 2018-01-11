package com.zhongtie.work.ui.main;

import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.main.adapter.HomeItemView;
import com.zhongtie.work.ui.safesupervision.SafeSupervisionActivity;
import com.zhongtie.work.ui.scan.ScanQRCodeActivity;
import com.zhongtie.work.ui.select.SelectLookGroupFragment;
import com.zhongtie.work.ui.select.SelectUserFragment;
import com.zhongtie.work.ui.setting.SettingActivity;
import com.zhongtie.work.ui.statistics.StatisticsActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class MainFragment extends BaseFragment implements OnRecyclerItemClickListener {
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
        mAdapter.setOnItemClickListener(this);
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

    @Override
    public void onClick(Object t, int index) {
        Pair<String, Integer> pair = mHomeItemList.get(index);
        switch (pair.first) {
            case "安全督导":
                SafeSupervisionActivity.newInstance(getActivity());
                break;
            case "统计图表":
                StatisticsActivity.newInstance(getActivity());
                break;
            case "扫二维码":
                ScanQRCodeActivity.newInstance(getActivity());
                break;
            case "文件下载":
                SettingActivity.newInstance(getActivity(), SelectUserFragment.class,"责任人");
                break;
            case "文件签认":
                SettingActivity.newInstance(getActivity(), SelectLookGroupFragment.class,"查阅组");
                break;
        }


    }
}
