package com.zhongtie.work.ui.rewardpunish.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.rewardpunish.RewardPunishCreateFragment;
import com.zhongtie.work.ui.rewardpunish.item.RPCommonItemView;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.ui.safe.dialog.OnSignatureListener;
import com.zhongtie.work.ui.safe.dialog.SignatureDialog;
import com.zhongtie.work.ui.safe.item.CommonDetailContentItemView;
import com.zhongtie.work.widget.SafeDividerItemDecoration;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class RPOrderDetailFragment extends BasePresenterFragment<RPDetailContract.Presenter> implements RPDetailContract.View, OnSignatureListener, OnApproveListener, SendBackDialog.OnSendBackListener {
    public static final String ID = "id";
    private int mSafeOrderID;
    private RPDetailHeadView mHeadInfoView;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();

    private LinearLayout mBottom;
    private LinearLayout mBottomBtn;
    private TextView mModify;
    private TextView mReply;
    private TextView mApprove;
    private RecyclerView mList;


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
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mBottomBtn = (LinearLayout) findViewById(R.id.bottom_btn);
        mModify = (TextView) findViewById(R.id.modify);
        mReply = (TextView) findViewById(R.id.reply);
        mReply.setVisibility(View.GONE);
        mApprove = (TextView) findViewById(R.id.approve);
        mApprove.setText(R.string.rp_approve);
        mList = (RecyclerView) findViewById(R.id.list);

        mModify.setOnClickListener(view -> SafeSupervisionCreateActivity.newInstance(getActivity(), RewardPunishCreateFragment.class, getString(R.string.safe_supervision_title)));
        mApprove.setOnClickListener(view -> showApproveDialog());

        mHeadInfoView = new RPDetailHeadView(getActivity());
        mHeadInfoView.initData();
        initAdapter();
    }

    private void showApproveDialog() {
        new ApproceIdeaDialog(getActivity(), this).show();

    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //输入的文字展示
                .register(CommonDetailContentItemView.class)
                //基本界面 展示数据
                .register(RPCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);
    }


    @Override
    protected void initData() {
        SafeDividerItemDecoration dividerItemDecoration = new SafeDividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(10));
        dividerItemDecoration.setEndPosition(5);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getDetailInfo(mSafeOrderID);
    }

    @Override
    protected RPDetailContract.Presenter getPresenter() {
        return new RPDetailPresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        mCommonAdapter.setListData(itemList);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void setHeadTitle(RewardPunishDetailEntity detailEntity) {
        mHeadInfoView.setDetailInfo(detailEntity);
    }

    @Override
    public void onSignature(String imagePath) {

    }

    @Override
    public void onConsent() {
        new SignatureDialog(getActivity(), this).show();
    }

    @Override
    public void onSendBack() {
        new SendBackDialog(getActivity(), this).show();
    }

    @Override
    public void onSendBackCancel() {

    }

    @Override
    public void onSendBackSuccess(String reason) {

    }
}
