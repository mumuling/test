package com.zhongtie.work.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EmptyFragment extends LinearLayout implements AdapterDataObserver.OnAdapterDataChangedListener {
    private static final String TAG = "SwipeRefreshRecyclerVie";
    private RecyclerView mRecyclerView = null;
    private FrameLayout mFrameLayout;
    private RecyclerView.AdapterDataObserver mAdapterDataObserver;


    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public View setEmptyView(@LayoutRes int layoutRes) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(layoutRes, mFrameLayout, false);
        setEmptyView(view);
        return view;
    }

    public void setEmptyView(View emptyView) {
        final RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("RecyclerView adapter is null");
        }
        if (mEmptyView != null) {
            mFrameLayout.removeView(mEmptyView);
            mEmptyView = null;
            if (mAdapterDataObserver != null) {
                adapter.unregisterAdapterDataObserver(mAdapterDataObserver);
            }
        }
        if (emptyView == null) {
            return;
        }
        mFrameLayout.addView(emptyView, 0);
        mEmptyView = emptyView;
        // 监听数据变化
        mAdapterDataObserver = new AdapterDataObserver(this);
        adapter.registerAdapterDataObserver(mAdapterDataObserver);
    }

    private View mEmptyView = null;

    public boolean isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    private boolean mLoading = false;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public EmptyFragment(Context context) {
        super(context);
        init(null);
    }

    public EmptyFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (isInEditMode()) {
            TextView textView = new TextView(getContext());
            textView.setText(getClass().getName());
            addView(textView);
            setBackgroundColor(Color.GRAY);
            return;
        }
        // 布局容器
        mFrameLayout = new FrameLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mFrameLayout, params);

        // RecyclerView
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrolledHandler(recyclerView, dx, dy);
            }
        });
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mFrameLayout.addView(mRecyclerView, layoutParams);


    }

    private void onScrolledHandler(RecyclerView recyclerView, int dx, int dy) {
        boolean scrollUp = (dy > 0);
        if (!scrollUp || isLoading() || mOnLoadMoreListener == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        int itemCount = layoutManager.getItemCount();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
            int pos = llm.findLastVisibleItemPosition();
            if (pos == itemCount - 1) {
                mLoading = true;
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }


    @Override
    public void onChanged() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter.getItemCount() <= 0) {
            // RecyclerView is empty
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
            }
            mRecyclerView.setVisibility(View.GONE);
        } else {
            // RecyclerView has item(s)
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.GONE);
            }
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


}
