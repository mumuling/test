package com.zhongtie.work.ui.safe.order;

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
import com.zhongtie.work.ui.safe.item.CreatePicItemView;
import com.zhongtie.work.widget.BaseImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * 创建头部数据
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class SafeDetailHeadView extends LinearLayout {


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
        mSafeOrderName = findViewById(R.id.safe_order_name);
        mSafeOrderReplyTime = findViewById(R.id.safe_order_reply_time);
        mDetailSite = findViewById(R.id.detail_site);
        mProjectTeam = findViewById(R.id.project_team);
        mInfoCompanyOffer = findViewById(R.id.info_company_offer);
        mQuestionType = findViewById(R.id.question_type);
        mDetailTime = findViewById(R.id.detail_time);
        mDetailContent = findViewById(R.id.detail_content);
        mSafeOrderReplyImgView = findViewById(R.id.safe_order_reply_img_view);
        mList = findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));

    }

    public void initData() {
        mSafeOrderReplyHead.loadImage(imageUrls[0]);
        mSafeOrderName.setText("刘玉梅");
        mDetailSite.setText("一号工地");
        mProjectTeam.setText("城建部");
        mProjectTeam.setText("某某劳务公司");
        mQuestionType.setText("安全防护\t安全管理\t");
        mDetailContent.setText("今年初，中铁建设对各二级单位安全目标指标完成情况进行考核，对16家二级单位的69名单位党政主管及安全总监进行了55.6万元的安全包保责任兑现奖励，2家单位因未完成安全目标指标受到处罚确保安全责任落地。");
        mSafeOrderReplyTime.setText("2017-06-20 11:02");
        commonAdapter = new CommonAdapter().register(new CreatePicItemView(false));
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(imageUrls).subList(0, 10));
        commonAdapter.setListData(list);
        mList.setAdapter(commonAdapter);

    }


}
