package com.zhongtie.work.ui.rewardpunish.item;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.list.CommonAdapterDataObserver;
import com.zhongtie.work.ui.safe.item.SafeCommonItemView;
import com.zhongtie.work.ui.safe.item.TeamNameItemView;
import com.zhongtie.work.ui.select.CommonSelectSearchActivity;
import com.zhongtie.work.ui.select.SelectLookGroupFragment;
import com.zhongtie.work.ui.select.SelectSupervisorUserFragment;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;

/**
 * 创建基本类别
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(CommonItemType.class)
public class RPCommonItemView extends AbstractItemView<CommonItemType, RPCommonItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_safe_create_add_user;
    }

    private Fragment getFragment(Context activity) {
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            return fragmentManager.getFragments().get(0);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @NonNull CommonItemType data) {

        vh.mItemUserListTip.setText(data.getHint());
        vh.mItemUserAddImg.setImageResource((data.getIcon()));
        vh.mItemUserAddImg.setVisibility(View.VISIBLE);
        if (data.isEdit()) {
            vh.mItemUserAddImg.setVisibility(View.VISIBLE);
            vh.mItemUserListTip.setVisibility(View.VISIBLE);
        } else {
            vh.mItemUserAddImg.setVisibility(View.GONE);
            vh.mItemUserListTip.setVisibility(View.GONE);
        }
        //跳转实现
        vh.mItemUserAddImg.setOnClickListener(v -> itemStartView(v, data));
        changeItemView(vh, data);

        CommonAdapter adapter;
        if (vh.mCheckExamineList.getAdapter() == null) {
            adapter = new CommonAdapter(data.getTypeItemList());
            //查阅横向滑动查看
            if (data.getTitle().contains("查阅")) {
                vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext, LinearLayout.HORIZONTAL, false));
            } else {
                //其它选项则上下滑动
                vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext));
            }
            adapter.register(PrRecordItemView.class);
            adapter.register(TeamNameItemView.class);

            if (vh.mCheckExamineList.getTag() != null) {
                adapter.unregisterAdapterDataObserver((RecyclerView.AdapterDataObserver) vh.mCheckExamineList.getTag());
            }
            RecyclerView.AdapterDataObserver observer = new CommonAdapterDataObserver(vh, commonViewHolder ->
                    changeItemView((RPCommonItemView.ViewHolder) commonViewHolder, (CommonItemType) getCommonAdapter().getListData(commonViewHolder.getItemPosition())));
            adapter.registerAdapterDataObserver(observer);
            vh.mCheckExamineList.setAdapter(adapter);
        } else {
            adapter = (CommonAdapter) vh.mCheckExamineList.getAdapter();
            adapter.setListData(data.getTypeItemList());
            adapter.notifyDataSetChanged();
        }
    }

    private void changeItemView(ViewHolder vh, CommonItemType data) {
        vh.mItemUserListTitle.setText(data.getTitle());
        if (data.getTypeItemList().size() > 0) {
            vh.mItemUserListTitle.append("\t(" + data.getTypeItemList().size() + ")");
        }
        if (data.getTypeItemList() == null || data.getTypeItemList().isEmpty()) {
            vh.mCheckExamineList.setVisibility(View.GONE);
        } else {
            vh.mCheckExamineList.setVisibility(View.VISIBLE);
        }
    }


    private void itemStartView(View v, @NonNull CommonItemType data) {
        if (data.getTitle().contains("查阅")) {
            CommonFragmentActivity.newInstance(getFragment(v.getContext()), SelectLookGroupFragment.class, data.getTitle(), data.getTypeItemList());
        } else {
            CommonSelectSearchActivity.newInstance(getFragment(v.getContext()), SelectSupervisorUserFragment.class, data.getTitle(), data.getTypeItemList());
        }
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private TextView mItemUserListTitle;
        private TextView mItemUserListTip;
        private ImageView mItemUserAddImg;
        private RecyclerView mCheckExamineList;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemUserListTitle = findViewById(R.id.item_user_list_title);
            mItemUserListTip = findViewById(R.id.item_user_list_tip);
            mItemUserAddImg = findViewById(R.id.item_user_add_img);
            mCheckExamineList = findViewById(R.id.check_examine_list);
        }

    }
}
