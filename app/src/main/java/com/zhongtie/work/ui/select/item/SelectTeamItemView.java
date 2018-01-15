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

/**
 * Auth:Cheek
 * date:2018.1.11
 */

@BindItemData(CompanyTeamEntity.class)
public class SelectTeamItemView extends AbstractItemView<CompanyTeamEntity, SelectTeamItemView.ViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_select_uset_group;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTeamItemView.ViewHolder vh, @NonNull CompanyTeamEntity data) {

        vh.mItemTeamTitle.setText(data.getTeamName());
        vh.setOnClickListener(view -> {
            data.setExpansion(!data.isExpansion());
            getCommonAdapter().notifyItemChanged(vh.getItemPosition());
        });
        vh.mItemTeamSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (CreateUserEntity createUserEntity : data.getTeamUserEntities()) {
                    createUserEntity.setSelect(!data.isSelect());
                    if (!createUserEntity.isSelect()) {
                        createUserEntity.setAt(false);
                    }
                    createUserEntity.post();
                }
                data.setSelect(!data.isSelect());
                if (!data.isExpansion()) {
                    data.setExpansion(true);
                }

                getCommonAdapter().notifyItemChanged(vh.getLayoutPosition());
//                vh.mCheckExamineList.getAdapter().notifyDataSetChanged();
            }
        });
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

        if (vh.mCheckExamineList.getAdapter() == null) {
            CommonAdapter adapter = new CommonAdapter(data.getTeamUserEntities());
            adapter.register(SelectTeamUserItemView.class);
//            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//                @Override
//                public void onChanged() {
//                    super.onChanged();
//                    getCommonAdapter().notifyDataSetChanged();
//                }
//            });
            vh.mCheckExamineList.setLayoutManager(new LinearLayoutManager(vh.mContext));
            //用户信息
            vh.mCheckExamineList.setAdapter(adapter);
        } else {
            CommonAdapter adapter = (CommonAdapter) vh.mCheckExamineList.getAdapter();
            adapter.setListData(data.getTeamUserEntities());
            adapter.notifyDataSetChanged();
        }


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
        private RecyclerView mCheckExamineList;
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