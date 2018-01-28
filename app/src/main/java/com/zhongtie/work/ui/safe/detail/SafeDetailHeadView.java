package com.zhongtie.work.ui.safe.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.ui.safe.item.CreatePicItemView;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.widget.BaseImageView;

import java.io.Serializable;
import java.util.List;

/**
 * 创建头部数据
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class SafeDetailHeadView extends LinearLayout implements Serializable {


    private ImageView mSafeOrderState;
    private BaseImageView mSafeOrderReplyHead;
    private BaseImageView mSafeOrderReplySign;
    private TextView mSafeOrderName;
    private TextView mSafeOrderReplyTime;
    private TextView mDetailSite;
    private TextView mProjectTeam;
    private TextView mInfoCompanyOffer;
    private TextView mQuestionType;
    private TextView mDetailTime;
    private TextView mDetailContent;
    private LinearLayout mSafeOrderReplyImgView;
    private RecyclerView mList;
    private CommonAdapter commonAdapter;

    private LinearLayout mWorkTeamLayout;
    private LinearLayout mSafeEventTypeLayout;



    public SafeDetailHeadView(Context context) {
        this(context, null);
    }

    public SafeDetailHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_safe_detail_head_view, this, true);
        mSafeOrderState = findViewById(R.id.safe_order_state);
        mSafeOrderReplyHead = findViewById(R.id.safe_order_reply_head);
        mSafeOrderReplySign = findViewById(R.id.safe_order_reply_sign);
        mSafeOrderReplySign.setVisibility(GONE);
        mSafeOrderName = findViewById(R.id.safe_order_name);
        mSafeOrderReplyTime = findViewById(R.id.safe_order_reply_time);
        mDetailSite = findViewById(R.id.detail_site);
        mProjectTeam = findViewById(R.id.project_team);
        mInfoCompanyOffer = findViewById(R.id.info_company_offer);
        mQuestionType = findViewById(R.id.question_type);
        mDetailTime = findViewById(R.id.detail_time);
        mDetailContent = findViewById(R.id.detail_content);
        mSafeOrderReplyImgView = findViewById(R.id.safe_order_reply_img_view);
        mWorkTeamLayout = (LinearLayout) findViewById(R.id.work_team_layout);
        mSafeEventTypeLayout = (LinearLayout) findViewById(R.id.safe_event_type_layout);

        mList = findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));

    }



    public void setHeadInfo(SafeEventEntity headInfo) {
        mSafeOrderReplyHead.loadImage(headInfo.user_pic);
        mSafeOrderName.setText(headInfo.user_name);
        mDetailSite.setText(headInfo.event_local);
        mProjectTeam.setText(headInfo.event_unit);
        if (!TextUtil.isEmpty(headInfo.event_workerteam)) {
            mWorkTeamLayout.setVisibility(VISIBLE);
            mInfoCompanyOffer.setText(headInfo.event_workerteam);
        }else {
            mWorkTeamLayout.setVisibility(GONE);
        }
        if (!TextUtil.isEmpty(headInfo.event_troubletype)) {
            mSafeEventTypeLayout.setVisibility(VISIBLE);
            mQuestionType.setText(headInfo.event_troubletype);
        }else {
            mSafeEventTypeLayout.setVisibility(GONE);
        }


        mDetailContent.setText(headInfo.event_detail);
        mSafeOrderReplyTime.setText(TimeUtils.formatEventTime(headInfo.event_publishtime));
        mDetailTime.setText(TimeUtils.formatEventSelectTime(headInfo.event_time));
        commonAdapter = new CommonAdapter().register(new CreatePicItemView(false));
        List<String> list = TextUtil.getPicList(headInfo.getEventpiclist());

        commonAdapter.setListData(list);
        mList.setAdapter(commonAdapter);

        switch (headInfo.getState()) {
            case "审批中":
                mSafeOrderState.setImageResource(R.drawable.status_approve_loading);
                break;
            case "已审批":
                mSafeOrderState.setImageResource(R.drawable.status_approve);
                break;
            case "已取消":
                mSafeOrderState.setImageResource(R.drawable.status_cancel);
                break;
            case "已退回":
                mSafeOrderState.setImageResource(R.drawable.status_exit);
                break;
            case "已完结":
                mSafeOrderState.setImageResource(R.drawable.status_over);
                break;
            case "已通过":
                mSafeOrderState.setImageResource(R.drawable.status_pass);
                break;
            default:

        }


    }
}
