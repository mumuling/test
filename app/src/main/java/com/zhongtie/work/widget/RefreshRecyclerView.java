package com.zhongtie.work.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhongtie.work.base.adapter.BaseXDiffCallback;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonListUpdateCallBack;
import com.zhongtie.work.list.OnRefreshListener;
import com.zhongtie.work.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 统一刷新加载分页数据
 *
 * @author Chaek
 */
public class RefreshRecyclerView<V extends CommonAdapter> extends SwipeRefreshRecyclerView
        implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshRecyclerView.OnLoadMoreListener, OnRefreshListener {
    private static final int FIRST_PAGE = 1;
    private RefreshPageConfig refreshRecyclerViewObservable;
    private RecyclerView refreshRecyclerView;
    private V refreshAdapter;
    public List<Object> listData = new ArrayList<>();
    volatile private int indexPage = 1;
    private View loadMoreView;


    public RefreshRecyclerView(Context context) {
        super(context, null);

    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * initAttach view  refresh
     */
    private void initView() {
        refreshRecyclerView = getRecyclerView();
        setOnRefreshListener(this);
        setOnLoadMoreListener(this);
    }

    /**
     * initTitle
     *
     * @param observable config interface
     */
    public void initConfig(RefreshPageConfig observable) {
        //获取实例化Adapter
        CommonAdapter baseAdapter = (CommonAdapter) observable.createAdapter();
        baseAdapter.setListData(listData);
        this.refreshAdapter = (V) baseAdapter;
        setRefreshRecyclerViewConfig(observable);
    }

    private void setRefreshRecyclerViewConfig(RefreshPageConfig refreshRecyclerViewObservable) {
        this.refreshRecyclerViewObservable = refreshRecyclerViewObservable;
        refreshAutoCheck();
        this.refreshRecyclerViewObservable = refreshRecyclerViewObservable;
        refreshRecyclerView.setAdapter(refreshAdapter);
        setDivider(false);
        setOnRefreshListener(this);
        setOnLoadMoreListener(this);
    }


    /**
     * initTitle auto refresh
     */
    private void refreshAutoCheck() {
        if (listData != null && isNotData()) {
            refreshRecyclerView.post(this::autoRefresh);
        }
    }

    /**
     * 获取分页数据
     *
     * @param page 页数
     */
    private void obtainPageData(int page) {
        indexPage = page;
        refreshRecyclerViewObservable.fetchPageListData(page);
    }

    @Override
    public void onRefresh() {
        setLoading(true);
        indexPage = 1;
        obtainPageData(indexPage);
    }

    @Override
    public void onRefreshComplete() {

    }


    /**
     * @return 是否有数据
     */
    private boolean isNotData() {
        return isMoreListData(listData);
    }


    public void setListData(List<Object> list) {
        setRefreshing(false);
        Log.d("setListData", "setListData: re" + list.toString());
        setLoading(false);
        setLoading(isNotData());
        if (indexPage == FIRST_PAGE) {
            //update
            listData.clear();
            listData.addAll(list);
        } else {
            //add
            if (list.size() > 0) {
                listData.addAll(list);
            }
        }
        if (list.size() <= 0) {
            setLoading(true);
        } else {
            setLoading(false);
        }
//        if (indexPage == FIRST_PAGE) {
//            refreshAdapter.setListData(listData);
            refreshAdapter.notifyDataSetChanged();
//        } else {
//            notifyDataSetChanged();
//        }
//        onChanged();
    }

    private void notifyDataSetChanged() {
        BaseXDiffCallback xDiffCallback = new BaseXDiffCallback<Object>(refreshAdapter.getListData(), listData) {
            @Override
            protected boolean areItemsTheSame(Object oldItem, Object newItem) {
                return oldItem.toString().equals(newItem.toString());
            }

            @Override
            protected boolean areContentsTheSame(Object oldItem, Object newItem) {
                return oldItem.toString().equals(newItem.toString());
            }
        };
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(xDiffCallback);
        refreshAdapter.setListData(listData);
        diffResult.dispatchUpdatesTo(new CommonListUpdateCallBack(refreshAdapter));
    }

    private boolean isMoreListData(List<Object> list) {
        return list == null || list.size() <= 0;
    }

    private void onFail(String message) {
        setRefreshing(false);
        ToastUtil.showToast(message);
        setLoading(false);
        onChanged();
        notifyDataSetChanged();
    }


    /**
     * set is show recycler divider
     *
     * @param isDivider true/false
     */
    public void setDivider(boolean isDivider) {
        if (isDivider) {
            DividerItemDecoration decoration = new DividerItemDecoration(getContext(), KDividerItemDecoration.VERTICAL);
            decoration.setDividerHeight(1);
            refreshRecyclerView.addItemDecoration(decoration);
        }
    }

    @Override
    public void onLoadMore() {
        indexPage++;
        setLoading(true);
        if (refreshAdapter != null && loadMoreView != null) {
            refreshAdapter.addFooterView(loadMoreView);
            notifyDataSetChanged();
        }
        obtainPageData(indexPage);
    }


    public List<Object> getListData() {
        return listData;
    }

    public interface RefreshPageConfig {
        void fetchPageListData(int page);

        RecyclerView.Adapter createAdapter();
    }
}
