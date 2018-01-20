package com.zhongtie.work.ui.safe.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.data.ReplyEntity;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.widget.BaseImageView;

import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

@BindItemData(value = {ReplyEntity.class, ApproveEntity.class})
public class ReplyItemView extends AbstractItemView<Object, ReplyItemView.ViewHolder> {

    public static final int APPROVE = 1;

    @Override
    public int getItemViewType(int position, @NonNull Object data) {
        if (data instanceof ApproveEntity)
            return APPROVE;
        return super.getItemViewType(position, data);
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == APPROVE) {
            return R.layout.item_approve_view;
        }
        return R.layout.item_safe_order_reply;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull Object data) {
        if (data instanceof ReplyEntity) {
            ReplyEntity replyEntity = (ReplyEntity) data;
            vh.safeOrderReplyHead.loadImage(replyEntity.userpic);
            vh.safeOrderReplySign.loadImageSign(replyEntity.url);
            vh.safeOrderName.setText(replyEntity.username);
            vh.safeOrderReplyTime.setText(replyEntity.getTime());
            vh.safeOrderReplyContent.setText(replyEntity.detail);
            List<String> list = TextUtil.getPicList(replyEntity.pic);
            if (vh.list.getAdapter() == null) {
                vh.list.setLayoutManager(new LinearLayoutManager(vh.mContext, LinearLayoutManager.HORIZONTAL, false));
                CreatePicItemView createPicItemView = new CreatePicItemView(false);
                CommonAdapter commonAdapter = new CommonAdapter().register(createPicItemView);
                vh.list.setAdapter(commonAdapter);
                commonAdapter.setListData(list);
            }

            if (list.isEmpty()) {
                vh.list.setVisibility(View.GONE);
                vh.mSlideLookMore.setVisibility(View.GONE);
            } else {
                vh.list.setVisibility(View.VISIBLE);
                vh.mSlideLookMore.setVisibility(View.VISIBLE);
            }
        } else {
            //签认情况
            ApproveEntity approve= (ApproveEntity) data;
            vh.safeOrderReplyHead.loadImage(approve.getUserpic());
            vh.safeOrderReplySign.loadImageSign(approve.getUrl());
            vh.safeOrderName.setText(approve.getUsername());
            vh.safeOrderReplyTime.setText(approve.getTime());
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }


    public class ViewHolder extends CommonViewHolder {
        private BaseImageView safeOrderReplyHead;
        private BaseImageView safeOrderReplySign;
        private TextView safeOrderName;
        private TextView safeOrderReplyTime;
        private TextView safeOrderReplyContent;
        private LinearLayout safeOrderReplyImgView;
        private RecyclerView list;
        private TextView mSlideLookMore;
        public ViewHolder(View itemView) {
            super(itemView);
            safeOrderReplyHead = (BaseImageView) findViewById(R.id.safe_order_reply_head);
            safeOrderReplySign = (BaseImageView) findViewById(R.id.safe_order_reply_sign);
            safeOrderName = (TextView) findViewById(R.id.safe_order_name);
            safeOrderReplyTime = (TextView) findViewById(R.id.safe_order_reply_time);
            safeOrderReplyContent = (TextView) findViewById(R.id.safe_order_reply_content);
            safeOrderReplyImgView = (LinearLayout) findViewById(R.id.safe_order_reply_img_view);
            list = (RecyclerView) findViewById(R.id.list);


            mSlideLookMore = (TextView) findViewById(R.id.slide_look_more);

        }
    }
}
