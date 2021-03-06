package com.zhongtie.work.ui.safe.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.event.ReplyEvent;
import com.zhongtie.work.list.OnChangeTitleListener;
import com.zhongtie.work.list.OnEventPrintListener;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment;
import com.zhongtie.work.ui.safe.dialog.OnSignatureListener;
import com.zhongtie.work.ui.safe.dialog.SignatureDialog;
import com.zhongtie.work.ui.safe.item.CommonDetailContentItemView;
import com.zhongtie.work.ui.safe.item.DetailCommonItemView;
import com.zhongtie.work.ui.safe.item.EventSignUserItemView;
import com.zhongtie.work.ui.safe.item.ReplyItemView;
import com.zhongtie.work.ui.safe.item.SafeTitleItemView;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ResourcesUtils;
import com.zhongtie.work.util.parse.BindKey;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 安全督导详情
 * date:2018.1.9
 *
 * @author Chaek
 */

public class SafeOrderDetailFragment extends BasePresenterFragment<SafeDetailContract.Presenter> implements SafeDetailContract.View, OnSignatureListener, OnRecyclerItemClickListener {
    public static final String ID = "id";
    public static final int SHOW = 1;

    @BindKey(ID)
    private int mSafeOrderID;
    private SafeDetailHeadView mHeadInfoView;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();
    private LinearLayout mBottom;


    private TextView mModify;
    private TextView mReply;
    private TextView mApprove;
    private TextView mCheck;
    private RecyclerView mList;
    private SafeDetailDividerItemDecoration dividerItemDecoration;
    private OnChangeTitleListener mOnChangeTitleListener;
    private OnEventPrintListener mOnEventPrintListener;


    public static void start(Context context, int id) {
        Bundle args = new Bundle();
        args.putInt(ID, id);
        SafeSupervisionCreateActivity.newInstance(context, SafeOrderDetailFragment.class, context.getString(R.string.safe_supervision_title), args);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnEventPrintListener) {
            mOnEventPrintListener = (OnEventPrintListener) context;
        }
        if (context instanceof OnChangeTitleListener) {
            mOnChangeTitleListener = (OnChangeTitleListener) context;
        }
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.safe_order_info_fragment;
    }

    @Override
    public void onClickRefresh() {
        super.onClickRefresh();
        mPresenter.getEventDetailItemList(mSafeOrderID);
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        LayoutInflater.from(getActivity()).inflate(R.layout.layout_safe_event_bottom, mBottom, true);

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
        initAdapter();
    }

    private OnSignatureListener mSignCheck = imagePath -> mPresenter.validateEvent(imagePath);

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //输入的文字展示
                .register(CommonDetailContentItemView.class)
                //标题
                .register(SafeTitleItemView.class)
                //回复
                .register(EventSignUserItemView.class)
                .register(ReplyItemView.class)
                //基本界面 展示数据
                .register(new DetailCommonItemView(mList));

        mHeadInfoView = new SafeDetailHeadView(getActivity());
        mCommonAdapter.addHeaderView(mHeadInfoView);
    }


    @Override
    protected void initData() {
        dividerItemDecoration = new SafeDetailDividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ResourcesUtils.dip2px(10));
        dividerItemDecoration.setEndPosition(5);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getEventDetailItemList(mSafeOrderID);
        mCommonAdapter.setOnItemClickListener(this);
    }

    /**
     * 回复之后刷新界面数据
     *
     * @param replyEvent 回复事件
     */
    @Subscribe
    public void replyEvent(ReplyEvent replyEvent) {
        mPresenter.getEventDetailItemList(mSafeOrderID);
    }

    @Override
    protected SafeDetailContract.Presenter getPresenter() {
        return new SafeDetailPresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList, boolean isHideNullItem) {
        mCommonAdapter.setListData(itemList);
        changeTitle(isHideNullItem);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCheckCount(int checkCount) {
        dividerItemDecoration.setCheckCount(checkCount);
    }

    /**
     * 更改标题
     */
    private void changeTitle(boolean isHideNullItem) {
        if (isHideNullItem) {
            dividerItemDecoration.setEndPosition(3);
            if (mOnChangeTitleListener != null) {
                mOnChangeTitleListener.setTitle("例行检查");
            }
        } else {
            dividerItemDecoration.setEndPosition(5);
        }
    }

    @Override
    public void setSafeDetailInfo(SafeEventEntity titleUserInfo) {
        mHeadInfoView.setHeadInfo(titleUserInfo);
    }

    @Override
    public void setEventStatus(SafeEventEntity.ButstateBean status) {
        mModify.setVisibility(status.edit == SHOW ? View.VISIBLE : View.GONE);
        mReply.setVisibility(status.reply == SHOW ? View.VISIBLE : View.GONE);
        mApprove.setVisibility(status.sign == SHOW ? View.VISIBLE : View.GONE);
        mCheck.setVisibility(status.check == SHOW ? View.VISIBLE : View.GONE);
        mBottom.setVisibility(status.isHide() ? View.GONE : View.VISIBLE);

        if (mOnEventPrintListener != null) {
            if (status.print == SHOW) {
                mOnEventPrintListener.onShowPrint(0, mSafeOrderID);
            } else {
                mOnEventPrintListener.onHidePrint();
            }
        }
    }


    @Override
    public void noLookAuthority() {
        mStatusView.showEmpty();
    }

    @Override
    public int fetchEventId() {
        return mSafeOrderID;
    }


    @Override
    public void onSignature(String imagePath) {
        mPresenter.checkUserSign(imagePath);
    }

    @Override
    public void onClick(Object o, int index) {
        if (o instanceof CommonItemType) {
            CommonItemType itemType = (CommonItemType) o;
            if (itemType.getTitle().contains("检查人")) {
                mPresenter.changeCheckListState();
                mCommonAdapter.notifyDataSetChanged();
            }
        }
    }
}
