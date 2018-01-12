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
import com.zhongtie.work.data.ReplyEntity;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.widget.BaseImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreate2Fragment.imageUrls;

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
            vh.safeOrderReplyHead.loadImage(imageUrls[0]);
            vh.safeOrderReplySign.loadImage(imageUrls[4]);
            vh.safeOrderName.setText("测试");
            vh.safeOrderReplyTime.setText("2012-01-12");
            vh.safeOrderReplyContent.setText("确定确定确定确定确定确定确定确定");
            if (vh.list.getAdapter() == null) {
                vh.list.setLayoutManager(new LinearLayoutManager(vh.mContext, LinearLayoutManager.HORIZONTAL, false));
                CreatePicItemView createPicItemView = new CreatePicItemView(false);
                CommonAdapter commonAdapter = new CommonAdapter().register(createPicItemView);
                vh.list.setAdapter(commonAdapter);
                List<String> list = new ArrayList<>();
                list.addAll(Arrays.asList(imageUrls).subList(0, 10));
                commonAdapter.setListData(list);
            }

        } else {
            vh.safeOrderReplyHead.loadImage(imageUrls[3]);
            vh.safeOrderReplySign.loadImage(imageUrls[1]);
            vh.safeOrderName.setText("测试");
            vh.safeOrderReplyTime.setText("2012-01-12");
//            vh.safeOrderReplyContent.setText("同意，注意安全注意安全注意安全");
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

        public ViewHolder(View itemView) {
            super(itemView);
            safeOrderReplyHead = (BaseImageView) findViewById(R.id.safe_order_reply_head);
            safeOrderReplySign = (BaseImageView) findViewById(R.id.safe_order_reply_sign);
            safeOrderName = (TextView) findViewById(R.id.safe_order_name);
            safeOrderReplyTime = (TextView) findViewById(R.id.safe_order_reply_time);
            safeOrderReplyContent = (TextView) findViewById(R.id.safe_order_reply_content);
            safeOrderReplyImgView = (LinearLayout) findViewById(R.id.safe_order_reply_img_view);
            list = (RecyclerView) findViewById(R.id.list);

        }
    }
}
