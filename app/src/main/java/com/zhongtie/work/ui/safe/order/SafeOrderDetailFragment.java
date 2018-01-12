package com.zhongtie.work.ui.safe.order;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.safe.SafeCreateContract;
import com.zhongtie.work.ui.safe.SafeCreateEditHeadView;
import com.zhongtie.work.ui.safe.SafeCreatePresenterImpl;
import com.zhongtie.work.ui.safe.SafeSupervisionCreate2Fragment;
import com.zhongtie.work.ui.safe.item.CommonDetailContentItemView;
import com.zhongtie.work.ui.safe.item.CreateCommonItemView;
import com.zhongtie.work.ui.safe.item.CreateEditContentItemView;
import com.zhongtie.work.ui.safe.item.CreateSelectTypeItemView;
import com.zhongtie.work.ui.safe.item.ReplyItemView;
import com.zhongtie.work.ui.safe.item.SafeTitleItemView;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeOrderDetailFragment extends BasePresenterFragment<SafeCreateContract.Presenter> implements SafeCreateContract.View {
    public static final String ID = "id";
    private int mSafeOrderID;
    private View mHeadInfoView;
    private RecyclerView mList;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();

    public static SafeSupervisionCreate2Fragment newInstance(int id) {
        Bundle args = new Bundle();
        SafeSupervisionCreate2Fragment fragment = new SafeSupervisionCreate2Fragment();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutViewId() {
        if (getArguments() != null) {
            mSafeOrderID = getArguments().getInt(ID);
        }
        return R.layout.safe_order_info_fragment;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mHeadInfoView = new SafeCreateEditHeadView(getActivity());
        initAdapter();
    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //输入的文字展示
                .register(CommonDetailContentItemView.class)
                //标题
                .register(SafeTitleItemView.class)
                //回复
                .register(ReplyItemView.class)
                //基本界面 展示数据
                .register(CreateCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);
        View mFooterView = LayoutInflater.from(getAppContext()).inflate(R.layout.layout_modify_pw_bottom, mList, false);
        mCommonAdapter.addFooterView(mFooterView);
    }


    @Override
    protected void initData() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(10));
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getItemList(mSafeOrderID);
    }

    @Override
    protected SafeCreateContract.Presenter getPresenter() {
        return new SafeDetailPresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        mInfoList.clear();
        mInfoList.addAll(itemList);
        mCommonAdapter.notifyDataSetChanged();
    }


}
