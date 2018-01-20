package com.zhongtie.work.ui.safe.order;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.event.ReplyEvent;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment;
import com.zhongtie.work.ui.safe.dialog.OnSignatureListener;
import com.zhongtie.work.ui.safe.dialog.SignatureDialog;
import com.zhongtie.work.ui.safe.item.CommonDetailContentItemView;
import com.zhongtie.work.ui.safe.item.DetailCommonItemView;
import com.zhongtie.work.ui.safe.item.ReplyItemView;
import com.zhongtie.work.ui.safe.item.SafeTitleItemView;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.util.upload.UploadUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeOrderDetailFragment extends BasePresenterFragment<SafeDetailContract.Presenter> implements SafeDetailContract.View, OnSignatureListener {
    public static final String ID = "id";
    private int mSafeOrderID;
    private SafeDetailHeadView mHeadInfoView;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();

    private LinearLayout mBottom;
    private LinearLayout mBottomBtn;
    private TextView mModify;
    private TextView mReply;
    private TextView mApprove;
    private TextView mCheck;
    private RecyclerView mList;


    public static SafeSupervisionCreateFragment newInstance(int id) {
        Bundle args = new Bundle();
        SafeSupervisionCreateFragment fragment = new SafeSupervisionCreateFragment();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutViewId() {
        if (getArguments() != null) {
            mSafeOrderID = getArguments().getInt(ID);
        }
        mSafeOrderID = 1174;
//        mSafeOrderID = 927;
        return R.layout.safe_order_info_fragment;
    }


    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mBottomBtn = (LinearLayout) findViewById(R.id.bottom_btn);
        mModify = (TextView) findViewById(R.id.modify);
        mReply = (TextView) findViewById(R.id.reply);
        mApprove = (TextView) findViewById(R.id.approve);
        mList = (RecyclerView) findViewById(R.id.list);

        mCheck = (TextView) findViewById(R.id.check);

        mModify.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(SafeOrderDetailFragment.ID, mSafeOrderID);
            SafeSupervisionCreateActivity.newInstance(SafeOrderDetailFragment.this, SafeSupervisionCreateFragment.class, getString(R.string.safe_supervision_title), bundle);

        });
        mReply.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ReplyEditFragment.EVENT_ID, mSafeOrderID);
            CommonFragmentActivity.newInstance(SafeOrderDetailFragment.this, ReplyEditFragment.class, "回复", bundle);
        });

        mCheck.setOnClickListener(view -> new SignatureDialog(getActivity(), mSignCheck).show());

        mApprove.setOnClickListener(view -> new SignatureDialog(getActivity(), SafeOrderDetailFragment.this).show());
        mHeadInfoView = new SafeDetailHeadView(getActivity());
        mHeadInfoView.initData();
        initAdapter();
    }

    private OnSignatureListener mSignCheck = imagePath -> {
        addDispose(UploadUtil.uploadSignPNG(imagePath)
                .flatMap(uploadData -> Http.netServer(SafeApi.class).validateEvent(Cache.getUserID(), mSafeOrderID, uploadData.getPicname()))
                .compose(Network.convertDialogTip(this))
                .subscribe(integer -> {
                    mPresenter.getItemList(mSafeOrderID);
                }, throwable -> {
                }));
    };

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //输入的文字展示
                .register(CommonDetailContentItemView.class)
                //标题
                .register(SafeTitleItemView.class)
                //回复
                .register(ReplyItemView.class)
                //基本界面 展示数据
                .register(DetailCommonItemView.class);
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
        mPresenter.getItemList(mSafeOrderID);
    }

    @Subscribe
    public void replyEvent(ReplyEvent replyEvent) {
        mPresenter.getItemList(mSafeOrderID);
    }

    @Override
    protected SafeDetailContract.Presenter getPresenter() {
        return new SafeDetailPresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        mCommonAdapter.setListData(itemList);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSafeDetailInfo(SafeEventEntity titleUserInfo) {
        mHeadInfoView.setHeadInfo(titleUserInfo);
    }

    @Override
    public void setEventStatus(SafeEventEntity.ButstateBean status) {
        mModify.setVisibility(status.edit == 1 ? View.VISIBLE : View.GONE);
        mReply.setVisibility(status.reply == 1 ? View.VISIBLE : View.GONE);
        mApprove.setVisibility(status.sign == 1 ? View.VISIBLE : View.GONE);
        mCheck.setVisibility(status.check == 1 ? View.VISIBLE : View.GONE);
        findViewById(R.id.bottom).setVisibility(status.print == 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void noLookAuthority() {
        mStatusView.showEmpty();
    }


    @Override
    public void onSignature(String imagePath) {
        addDispose(UploadUtil.uploadSignPNG(imagePath)
                .flatMap(uploadData -> Http.netServer(SafeApi.class).eventSign(Cache.getUserID(), mSafeOrderID, uploadData.getPicname()))
                .compose(Network.convertDialogTip(this))
                .subscribe(integer -> {
                    mPresenter.getItemList(mSafeOrderID);
                }, throwable -> {
                }));

    }
}
