package com.zhongtie.work.ui.endorse.create;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
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
import com.zhongtie.work.ui.endorse.detail.EndorseUserItemView;
import com.zhongtie.work.ui.file.FileSelectFragment;
import com.zhongtie.work.ui.safe.item.CreateUserItemView;
import com.zhongtie.work.ui.safe.item.TeamNameItemView;
import com.zhongtie.work.ui.select.SelectReadGroupFragment;
import com.zhongtie.work.ui.select.SelectUserFragment;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;

@BindItemData(CommonItemType.class)
public class EndorseCommonItemView extends AbstractItemView<CommonItemType, EndorseCommonItemView.ViewHolder> {
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
        //查阅横向滑动查看
        if (data.getTitle().contains("查阅")) {
            vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext, LinearLayout.HORIZONTAL, false));
        } else if (data.getTitle().contains("签认人")) {
            vh.mCheckExamineList.setLayoutManager(new GridLayoutManager(vh.mContext, 5));
        } else {
            //其它选项则上下滑动
            vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext));
        }

        //跳转实现
        vh.mItemUserAddImg.setOnClickListener(v -> itemStartView(v, data));
        CommonAdapter adapter;
        if (vh.mCheckExamineList.getAdapter() == null) {
            adapter = new CommonAdapter(data.getTypeItemList());
            //选择的文件
            adapter.register(new EndorseFileItemView(data.isEdit()));
            //选择人
            adapter.register(new CreateUserItemView(data.isEdit()));
            //查阅组
            adapter.register(TeamNameItemView.class);
            //签认人
            adapter.register(EndorseUserItemView.class);

            vh.mCheckExamineList.setAdapter(adapter);

            if (vh.mCheckExamineList.getTag() != null) {
                adapter.unregisterAdapterDataObserver((RecyclerView.AdapterDataObserver) vh.mCheckExamineList.getTag());
            }
            RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    changeItemView(vh, data);
                }
            };
            adapter.registerAdapterDataObserver(observer);
            vh.mCheckExamineList.setTag(observer);

        } else {
            adapter = (CommonAdapter) vh.mCheckExamineList.getAdapter();
            adapter.setListData(data.getTypeItemList());
            adapter.notifyDataSetChanged();
        }

        changeItemView(vh, data);
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
            CommonFragmentActivity.newInstance(getFragment(v.getContext()), SelectReadGroupFragment.class, data.getTitle(), data.getTypeItemList());
        } else if (data.getTitle().contains("上传文件")) {
            CommonFragmentActivity.newInstance(v.getContext(), FileSelectFragment.class, "选择文件");
        } else {
            CommonFragmentActivity.newInstance(getFragment(v.getContext()), SelectUserFragment.class, data.getTitle(), data.getTypeItemList());
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
