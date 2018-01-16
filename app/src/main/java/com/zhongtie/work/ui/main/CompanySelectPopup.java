package com.zhongtie.work.ui.main;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.main.adapter.CompanyItemView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class CompanySelectPopup extends PopupWindow implements OnRecyclerItemClickListener {

    private List<CompanyEntity> companyEntityList;
    private Context context;
    private RecyclerView mList;

    private OnCompanySelectListener onCompanySelectListener;

    public void setOnCompanySelectListener(OnCompanySelectListener onCompanySelectListener) {
        this.onCompanySelectListener = onCompanySelectListener;
    }

    public CompanySelectPopup(Context context, List<CompanyEntity> companyEntityList) {
        super(context);
        this.companyEntityList = companyEntityList;
        this.context = context;

        Http.netServer(UserApi.class).fetchCompanyList(0)
                .map(new NetWorkFunc1<>())
                .map(companyEntities -> {
//                    FlowManager.getDatabase(ZhongtieDb.class).executeTransaction(databaseWrapper ->
//                            FastStoreModelTransaction.saveBuilder(FlowManager.getModelAdapter(CompanyEntity.class)).
//                                    addAll(companyEntities).build().execute(databaseWrapper));
                    return companyEntities;
                })
                .compose(Network.netorkIO())
                .subscribe(new Consumer<List<CompanyEntity>>() {
                    @Override
                    public void accept(List<CompanyEntity> companyEntities) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        initView();
        initData();
    }

    private void initData() {
        CommonAdapter adapter = new CommonAdapter(companyEntityList).register(CompanyItemView.class);
        mList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void initView() {
        View root = LayoutInflater.from(context).inflate(R.layout.popup_comany_select, null, false);
        setContentView(root);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        setFocusable(true);
        setBackgroundDrawable(dw);
        mList = root.findViewById(R.id.list);
        mList.setLayoutManager(new GridLayoutManager(context, 3));

        mList.addItemDecoration(new PerformerGridItemDecoration(context));
    }

    @Override
    public void onClick(Object t, int index) {
        if (onCompanySelectListener != null) {
            onCompanySelectListener.onSelectCompany((CompanyEntity) t, index);
        }
        dismiss();
    }

    public interface OnCompanySelectListener {
        void onSelectCompany(CompanyEntity companyEntity, int position);
    }
}
