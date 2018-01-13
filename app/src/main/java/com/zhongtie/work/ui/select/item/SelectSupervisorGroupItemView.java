package com.zhongtie.work.ui.select.item;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
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

/**
 * 安全监察选择
 * Auth:Cheek
 * date:2018.1.11
 */

@BindItemData(CompanyTeamEntity.class)
public class SelectSupervisorGroupItemView extends AbstractItemView<CompanyTeamEntity, SelectSupervisorGroupItemView.ViewHolder> {
    private SelectSupervisorUserItemView selectTeamUserItemView;

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_select_uset_group;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectSupervisorGroupItemView.ViewHolder vh, @NonNull CompanyTeamEntity data) {
        vh.mItemTeamTitle.setText(data.getTeamName());
        vh.mItemTeamTitle.setOnClickListener(view -> {
            data.setExpansion(!data.isExpansion());
            getCommonAdapter().notifyItemChanged(vh.getItemPosition());
        });

        vh.mCheckExamineList.setLayoutManager(new GridLayoutManager(vh.mContext, 2));
        if (vh.mCheckExamineList.getAdapter() == null) {
            CommonAdapter adapter = new CommonAdapter(data.getTeamUserEntities());
            //用户信息
            if (selectTeamUserItemView == null) {
                selectTeamUserItemView = new SelectSupervisorUserItemView();
            }
            adapter.register(selectTeamUserItemView);
            vh.mCheckExamineList.setAdapter(adapter);
        } else {
            CommonAdapter adapter = (CommonAdapter) vh.mCheckExamineList.getAdapter();
            adapter.setListData(data.getTeamUserEntities());
            adapter.notifyDataSetChanged();
        }

//        if (data.getTeamUserEntities() == null || data.getTeamUserEntities().isEmpty()) {
//            vh.mCheckExamineList.setVisibility(View.GONE);
//        } else {
//            if (data.isExpansion()) {
//                vh.mCheckExamineList.setVisibility(View.VISIBLE);
//            } else {
//                vh.mCheckExamineList.setVisibility(View.GONE);
//            }
//        }
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends CommonViewHolder {
        private TextView mItemTeamTitle;
        private TextView mItemTeamSelectAdd;
        private TextView mItemTeamSelectAll;
        private RecyclerView mCheckExamineList;
        private RelativeLayout mTeamTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            mItemTeamTitle = (TextView) findViewById(R.id.item_team_title);
            mItemTeamSelectAdd = (TextView) findViewById(R.id.item_team_select_add);
            mItemTeamSelectAll = (TextView) findViewById(R.id.item_team_select_all);
            mCheckExamineList = (RecyclerView) findViewById(R.id.check_examine_list);
            mTeamTitle = (RelativeLayout) findViewById(R.id.team_title);
            mItemTeamSelectAdd.setVisibility(View.GONE);
            mItemTeamSelectAll.setVisibility(View.GONE);
        }

    }
}