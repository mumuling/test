package com.zhongtie.work.ui.rewardpunish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.rewardpunish.item.RPCommonItemView;
import com.zhongtie.work.ui.safe.item.CreateEditContentItemView;
import com.zhongtie.work.ui.safe.item.CreateSelectTypeItemView;
import com.zhongtie.work.ui.safe.order.SafeDividerItemDecoration;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class RewardPunishCreateFragment extends BasePresenterFragment<RewardPunishCreateContract.Presenter> implements RewardPunishCreateContract.View {

    public static final String ID = "id";
    private int mSafeOrderID;
    private View mHeadInfoView;
    private RecyclerView mList;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();

    public static RewardPunishCreateFragment newInstance(int id) {
        Bundle args = new Bundle();
        RewardPunishCreateFragment fragment = new RewardPunishCreateFragment();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutViewId() {
        if (getArguments() != null) {
            mSafeOrderID = getArguments().getInt(ID);
        }
        return R.layout.base_recyclerview;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mHeadInfoView = new RPCreateHeadView(getActivity());
        initAdapter();
    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //选择类别
                .register(CreateSelectTypeItemView.class)
                //输入数据
                .register(CreateEditContentItemView.class)
                //基本界面
                .register(RPCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);

        View mFooterView = LayoutInflater.from(getAppContext()).inflate(R.layout.layout_modify_pw_bottom, mList, false);
        mCommonAdapter.addFooterView(mFooterView);
    }


    @Override
    protected void initData() {
        SafeDividerItemDecoration dividerItemDecoration = new SafeDividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(10));
        dividerItemDecoration.setEndPosition(5);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getItemList(mSafeOrderID);
    }

    @Override
    protected RewardPunishCreateContract.Presenter getPresenter() {
        return new RPCreatePresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        mCommonAdapter.setListData(itemList);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CommonFragmentActivity.USER_SELECT_CODE) {
                String title = data.getStringExtra(TITLE);
                List<CommonUserEntity> createUserEntities = (List<CommonUserEntity>) data.getSerializableExtra(LIST);
                mPresenter.setSelectUserInfoList(title, createUserEntities);
                mCommonAdapter.notifyDataSetChanged();
            }

        }

    }




}
