package com.zhongtie.work.ui.main;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.db.CacheCompanyTable;
import com.zhongtie.work.ui.main.adapter.CompanyItemView;
import com.zhongtie.work.ui.main.adapter.SelectCompanyItemDecoration;

import java.util.List;

/**
 * 首页选择公司PopupWindow
 * date:2018.1.9
 *
 * @author Chaek
 */

public class CompanySelectPopupWindow extends PopupWindow implements OnRecyclerItemClickListener {
    private List<CacheCompanyTable> companyEntityList;
    private Context context;
    private RecyclerView mList;
    private OnCompanySelectListener onCompanySelectListener;

    void setOnCompanySelectListener(OnCompanySelectListener onCompanySelectListener) {
        this.onCompanySelectListener = onCompanySelectListener;
    }

    CompanySelectPopupWindow(Context context, List<CacheCompanyTable> companyEntityList) {
        super(context);
        this.companyEntityList = companyEntityList;
        this.context = context;
        initView();
        initData();
    }

    private void initData() {
        CommonAdapter adapter = new CommonAdapter(companyEntityList).register(CompanyItemView.class);
        mList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void initView() {
        View root = View.inflate(context, R.layout.popup_comany_select, null);
        setContentView(root);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0xffffffff));

        mList = root.findViewById(R.id.list);
        mList.setLayoutManager(new GridLayoutManager(context, 3));
        mList.addItemDecoration(new SelectCompanyItemDecoration(context));
    }

    @Override
    public void onClick(Object t, int index) {
        if (onCompanySelectListener != null) {
            onCompanySelectListener.onSelectCompany((CacheCompanyTable) t, index);
        }
        dismiss();
    }

    /**
     * 公司选择回调函数
     */
    public interface OnCompanySelectListener {
        /**
         * 公司选择
         *
         * @param companyEntity 已选择的公司列表
         * @param position      选择的公司position
         */
        void onSelectCompany(CacheCompanyTable companyEntity, int position);
    }
}
