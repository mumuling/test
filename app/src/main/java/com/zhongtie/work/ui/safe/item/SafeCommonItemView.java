package com.zhongtie.work.ui.safe.item;

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
import com.zhongtie.work.ui.image.MultiImageSelector;
import com.zhongtie.work.ui.select.SelectLookGroupFragment;
import com.zhongtie.work.ui.select.SelectUserFragment;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;

import static com.zhongtie.work.ui.image.MultiImageSelector.REQUEST_CODE;

/**
 * 创建类别选择
 * Auth: Chaek
 * Date: 2018/1/11
 */
@BindItemData(CommonItemType.class)
public class SafeCommonItemView extends AbstractItemView<CommonItemType, SafeCommonItemView.ViewHolder> {
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
        changeItemView(vh, data);
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
        vh.mItemUserAddImg.setOnClickListener(v -> {
            if (data.getTitle().contains(vh.mContext.getString(R.string.photo))) {
                //照片
                int count = data.getTypeItemList() == null ? MultiImageSelector.MAX_COUNT : MultiImageSelector.MAX_COUNT - data.getTypeItemList().size();
                MultiImageSelector.create().count(count).start(getFragment(v.getContext()), REQUEST_CODE);
            } else if (data.getTitle().contains("查阅")) {
                CommonFragmentActivity.newInstance(getFragment(v.getContext()), SelectLookGroupFragment.class, data.getTitle(), data.getTypeItemList());
            } else {
                CommonFragmentActivity.newInstance(getFragment(v.getContext()), SelectUserFragment.class, data.getTitle(), data.getTypeItemList());
            }
        });

        CommonAdapter adapter;
        if (vh.mCheckExamineList.getAdapter() == null) {
            adapter = new CommonAdapter(data.getTypeItemList());
            vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext, LinearLayout.HORIZONTAL, false));
            adapter.register(new CreatePicItemView(data.isEdit()));
            adapter.register(CreateUserItemView.class);
            adapter.register(TeamNameItemView.class);
            vh.mCheckExamineList.setAdapter(adapter);

        } else {
            adapter = (CommonAdapter) vh.mCheckExamineList.getAdapter();
            adapter.setListData(data.getTypeItemList());
            adapter.notifyDataSetChanged();
        }

        if (vh.mCheckExamineList.getTag() != null) {
            adapter.unregisterAdapterDataObserver((RecyclerView.AdapterDataObserver) vh.mCheckExamineList.getTag());
        }
        RecyclerView.AdapterDataObserver observer = new CommonAdapterDataObserver(vh, commonViewHolder ->
                changeItemView((ViewHolder) commonViewHolder, (CommonItemType) getCommonAdapter().getListData(commonViewHolder.getItemPosition())));
        adapter.registerAdapterDataObserver(observer);
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
