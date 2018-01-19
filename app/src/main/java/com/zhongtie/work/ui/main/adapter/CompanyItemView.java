package com.zhongtie.work.ui.main.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.AbstractItemView;
import com.zhongtie.work.base.adapter.BindItemData;
import com.zhongtie.work.base.adapter.CommonViewHolder;
import com.zhongtie.work.db.CacheCompanyTable;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

@BindItemData(CacheCompanyTable.class)
public class CompanyItemView extends AbstractItemView<CacheCompanyTable, CommonViewHolder> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_company_select;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder vh, @NonNull CacheCompanyTable data) {
        TextView mHomePageName = vh.findViewById(R.id.company_title);
        mHomePageName.setText(data.getName());
    }


    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull View view, int viewType) {
        return new CommonViewHolder(view);
    }
}

