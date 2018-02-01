package com.zhongtie.work.ui.rewardpunish.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.enums.SignatureType;
import com.zhongtie.work.list.OnEventPrintListener;
import com.zhongtie.work.list.OnSignatureTypeListener;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.rewardpunish.RewardPunishCreateFragment;
import com.zhongtie.work.ui.rewardpunish.adapter.RewardPunishCommonItemView;
import com.zhongtie.work.ui.rewardpunish.dialog.ApproceIdeaDialog;
import com.zhongtie.work.ui.rewardpunish.dialog.SendBackDialog;
import com.zhongtie.work.ui.rewardpunish.presenter.RPDetailContract;
import com.zhongtie.work.ui.rewardpunish.presenter.RPDetailPresenterImpl;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.ui.safe.dialog.SignatureDialog;
import com.zhongtie.work.ui.safe.item.CommonDetailContentItemView;
import com.zhongtie.work.util.parse.BindKey;
import com.zhongtie.work.widget.SafeDividerItemDecoration;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 安全处罚详情
 *
 * @author Chaek
 * @date:2018.1.9
 */

public class PunishDetailFragment extends BasePresenterFragment<RPDetailContract.Presenter> implements RPDetailContract.View,
        OnSignatureTypeListener, OnApproveListener, SendBackDialog.OnSendBackListener {
    public static final String ID = "id";

    /**
     * 签认
     */
    public static final int PUNISH_SIGN_TYP = 11;
    /**
     * 退回
     */
    public static final int PUNISH_SEND_BACK_TYPE = 12;
    /**
     * 同意
     */
    public static final int PUNISH_CONSENT_TYPE = 13;
    /**
     * 作废&取消
     */
    public static final int PUNISH_CANCEL_TYPE = 14;


    @BindKey(ID)
    private int mPunishId;
    private RPDetailHeadView mHeadInfoView;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();

    private LinearLayout mBottom;
    private RecyclerView mList;

    private LinearLayout mBottomBtn;
    private TextView mModify;
    private TextView mSendBack;
    private TextView mApprove;
    private TextView mSign;
    private TextView mCancel;

    private OnEventPrintListener mOnEventPrintListener;

    public static void start(Context context, int punishId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, punishId);
        SafeSupervisionCreateActivity.newInstance(context, PunishDetailFragment.class, context.getString(R.string.safe_punish_title), bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventPrintListener) {
            mOnEventPrintListener = (OnEventPrintListener) context;
        }
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.safe_order_info_fragment;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mBottom = (LinearLayout) findViewById(R.id.bottom);

        LayoutInflater.from(getActivity()).inflate(R.layout.layout_punish_detail_bottom, mBottom, true);
        mModify = (TextView) findViewById(R.id.modify);
        mApprove = (TextView) findViewById(R.id.approve);
        mBottomBtn = (LinearLayout) findViewById(R.id.bottom_btn);
        mModify = (TextView) findViewById(R.id.modify);
        mSendBack = (TextView) findViewById(R.id.send_back);
        mApprove = (TextView) findViewById(R.id.approve);
        mSign = (TextView) findViewById(R.id.sign);
        mCancel = (TextView) findViewById(R.id.cancel);

        mList = (RecyclerView) findViewById(R.id.list);

        mModify.setOnClickListener(view -> RewardPunishCreateFragment.start(getActivity(), mPunishId));
        //同意
        mApprove.setOnClickListener(view -> showSignDialog(PUNISH_CONSENT_TYPE));
        //签认
        mSign.setOnClickListener(v -> showSignDialog(PUNISH_SIGN_TYP));
        //作废
        mCancel.setOnClickListener(v -> showSignDialog(PUNISH_CANCEL_TYPE));

        //退回
        mSendBack.setOnClickListener(v -> new SendBackDialog(getActivity(), PunishDetailFragment.this).show());

        mHeadInfoView = new RPDetailHeadView(getActivity());
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
                .register(RewardPunishCommonItemView.class);
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
        mPresenter.getDetailInfo(mPunishId);
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
    public void consentPunishSuccess() {
        //同意成功

    }


    /**
     * 弹出签名窗口
     *
     * @param signType 类型  {@link SignatureType}
     */
    private void showSignDialog(@SignatureType int signType) {
        new SignatureDialog(getActivity(), signType, this).show();
    }

    @Override
    public void onConsent() {
        showSignDialog(PUNISH_CONSENT_TYPE);
    }

    @Override
    public void onSendBack() {

    }

    @Override
    public void onSendBackCancel() {

    }

    private String reason;

    @Override
    public void onSendBackSuccess(String reason) {
        this.reason = reason;
        showSignDialog(PUNISH_SEND_BACK_TYPE);
    }

    @Override
    public void onSignature(int type, String imagePath) {
        if (type == PUNISH_SIGN_TYP) {
            //处罚认罚
            mPresenter.signPunish(imagePath);
        } else if (type == PUNISH_CONSENT_TYPE) {
            //处罚同意
            mPresenter.consentPunish(imagePath);
        } else if (type == PUNISH_CANCEL_TYPE) {
            //作废
            mPresenter.cancellationPunish(imagePath);
        } else if (type == PUNISH_SEND_BACK_TYPE) {
            //退回
            mPresenter.sendBackPunish(imagePath, reason);
        }
    }

    @Override
    public void showStatusView(int edit, int agree, int retreat, int sign, int cancel) {
        mModify.setVisibility(edit == 1 ? View.VISIBLE : View.GONE);
        mApprove.setVisibility(agree == 1 ? View.VISIBLE : View.GONE);
        mSendBack.setVisibility(retreat == 1 ? View.VISIBLE : View.GONE);
        mSign.setVisibility(sign == 1 ? View.VISIBLE : View.GONE);
        mCancel.setVisibility(cancel == 1 ? View.VISIBLE : View.GONE);
    }


    @Override
    public void showPrint(int print) {
        if (print == 1) {
            mBottom.setVisibility(View.GONE);
            if (mOnEventPrintListener != null) {
                mOnEventPrintListener.onShowPrint(2, mPunishId);
            }
        } else {
            if (mOnEventPrintListener != null) {
                mOnEventPrintListener.onHidePrint();
            }
        }

    }
}
