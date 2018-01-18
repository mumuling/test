package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.data.CompanyTeamEntity;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.list.SelectUserAdapterDataObserver;

/**
 * date:2018.1.11
 * @author Chaek
 */

@BindItemData(CompanyTeamEntity.class)
public class SelectUserGroupItemView extends AbstractItemView<CompanyTeamEntity, SelectUserGroupItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_select_uset_group;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectUserGroupItemView.ViewHolder vh, @NonNull CompanyTeamEntity data) {
        changeItemView(vh, data);
        vh.mItemTeamTitle.setText(data.getTeamName());

        //点击头部隐藏和伸缩
        vh.mTeamTitle.setOnClickListener(view -> {
            data.setExpansion(!data.isExpansion());
            getCommonAdapter().notifyItemChanged(vh.getItemPosition());
        });

        //选择全部
        vh.mItemTeamSelectAll.setOnClickListener(view -> {
            for (CreateUserEntity createUserEntity : data.getTeamUserEntities()) {
                createUserEntity.setSelect(!data.isSelect());
                if (!createUserEntity.isSelect()) {
                    createUserEntity.setAt(false);
                }
                createUserEntity.post();
            }
            if (!data.isExpansion()) {
                data.setExpansion(true);
            }
            getCommonAdapter().notifyItemChanged(vh.getLayoutPosition());
        });
        //@已经勾选的所有
        vh.mItemTeamSelectAT.setOnClickListener(view -> {
            for (CreateUserEntity createUserEntity : data.getTeamUserEntities()) {
                if (!data.isAt() && createUserEntity.isSelect()) {
                    createUserEntity.setAt(true);
                } else {
                    createUserEntity.setAt(false);
                }
                createUserEntity.post();
            }
            data.setAt(!data.isAt());
            vh.mCheckExamineList.getAdapter().notifyDataSetChanged();
        });

        //设置人员适配适配器
        CommonAdapter adapter;
        if (vh.mCheckExamineList.getAdapter() == null) {
            adapter = new CommonAdapter(data.getTeamUserEntities()).register(SelectUserItemView.class);
            vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext));
            vh.mCheckExamineList.setAdapter(adapter);
        } else {
            adapter = (CommonAdapter) vh.mCheckExamineList.getAdapter();
            adapter.setListData(data.getTeamUserEntities());
            adapter.notifyDataSetChanged();
        }

        if (vh.mCheckExamineList.getTag() != null) {
            adapter.unregisterAdapterDataObserver((RecyclerView.AdapterDataObserver) vh.mCheckExamineList.getTag());
        }
        //设置adapter更改改变View界面
        RecyclerView.AdapterDataObserver observer = new SelectUserAdapterDataObserver(vh, commonViewHolder ->
                changeItemView((SelectUserGroupItemView.ViewHolder) commonViewHolder, (CompanyTeamEntity) getCommonAdapter().getListData(commonViewHolder.getItemPosition())));
        adapter.registerAdapterDataObserver(observer);
        vh.mCheckExamineList.setTag(observer);

    }


    private void changeItemView(SelectUserGroupItemView.ViewHolder vh, CompanyTeamEntity data) {

        //遍历查看是否全选
        boolean isSelectAll = true;
        for (CreateUserEntity entity : data.getTeamUserEntities()) {
            if (!entity.isSelect()) {
                isSelectAll = false;
            }
        }
        data.setSelect(isSelectAll && !data.getTeamUserEntities().isEmpty());
        if (data.isSelect()) {
            vh.mItemTeamSelectAll.setText(R.string.un_select_all);
        } else {
            vh.mItemTeamSelectAll.setText(R.string.select_all);
        }

        //是否隐藏RecyclerView
        if (data.getTeamUserEntities() == null || data.getTeamUserEntities().isEmpty()) {
            vh.mCheckExamineList.setVisibility(View.GONE);
        } else {
            if (data.isExpansion()) {
                vh.mCheckExamineList.setVisibility(View.VISIBLE);
            } else {
                vh.mCheckExamineList.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private TextView mItemTeamTitle;
        private TextView mItemTeamSelectAT;
        private TextView mItemTeamSelectAll;
        public RecyclerView mCheckExamineList;
        private RelativeLayout mTeamTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            mItemTeamTitle = (TextView) findViewById(R.id.item_team_title);
            mItemTeamSelectAT = (TextView) findViewById(R.id.item_team_select_add);
            mItemTeamSelectAll = (TextView) findViewById(R.id.item_team_select_all);
            mCheckExamineList = (RecyclerView) findViewById(R.id.check_examine_list);
            mTeamTitle = (RelativeLayout) findViewById(R.id.team_title);
        }

    }
}