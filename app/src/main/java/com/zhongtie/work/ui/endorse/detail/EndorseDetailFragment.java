package com.zhongtie.work.ui.endorse.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.endorse.create.EndorseCommonItemView;
import com.zhongtie.work.ui.rewardpunish.detail.ApproceIdeaDialog;
import com.zhongtie.work.ui.rewardpunish.detail.OnApproveListener;
import com.zhongtie.work.ui.rewardpunish.detail.SendBackDialog;
import com.zhongtie.work.ui.safe.dialog.OnSignatureListener;
import com.zhongtie.work.ui.safe.dialog.SignatureDialog;
import com.zhongtie.work.ui.safe.detail.SafeDividerItemDecoration;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class EndorseDetailFragment extends BasePresenterFragment<EndorseDetailContract.Presenter> implements EndorseDetailContract.View, OnSignatureListener, OnApproveListener, SendBackDialog.OnSendBackListener {
    public static final String ID = "id";
    private int mSafeOrderID;
    private View mHeadInfoView;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();

    private RecyclerView mList;


    @Override
    public int getLayoutViewId() {
        if (getArguments() != null) {
            mSafeOrderID = getArguments().getInt(ID);
        }
        return R.layout.file_endorse_create_fragment;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mHeadInfoView = LayoutInflater.from(getContext()).inflate(R.layout.layout_file_endorse_head, mList, false);
        initAdapter();
    }

    private void showApproveDialog() {
        new ApproceIdeaDialog(getActivity(), this).show();

    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //基本界面
                .register(EndorseCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);
    }


    @Override
    protected void initData() {
        SafeDividerItemDecoration dividerItemDecoration = new SafeDividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(10));
        dividerItemDecoration.setEndPosition(4);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getItemList(mSafeOrderID);
    }

    @Override
    protected EndorseDetailContract.Presenter getPresenter() {
        return new EndorseDetailPresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        mCommonAdapter.setListData(itemList);
        mCommonAdapter.notifyDataSetChanged();
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
