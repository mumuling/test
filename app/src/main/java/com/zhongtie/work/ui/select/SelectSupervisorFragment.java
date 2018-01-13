package com.zhongtie.work.ui.select;

import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.select.item.SelectSupervisorNameItemView;
import com.zhongtie.work.widget.DividerItemDecoration;
import com.zhongtie.work.widget.KDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择督导信息
 * Auth:Cheek
 * date:2018.1.13
 */

public class SelectSupervisorFragment extends BaseFragment implements OnRecyclerItemClickListener<SupervisorInfoEntity> {
    private CommonAdapter commonAdapter;
    private RecyclerView mList;


    @Override
    public int getLayoutViewId() {
        return R.layout.base_recyclerview;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), KDividerItemDecoration.VERTICAL);
        decoration.setDividerHeight(1);
        mList.addItemDecoration(decoration);
    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter().register(SelectSupervisorNameItemView.class);
        commonAdapter.setOnItemClickListener(this);
        mList.setAdapter(commonAdapter);


        List<SupervisorInfoEntity> entityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entityList.add(new SupervisorInfoEntity());
        }
        commonAdapter.setListData(entityList);
        commonAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(SupervisorInfoEntity supervisorInfoEntity, int index) {
        supervisorInfoEntity.post();
        getActivity().finish();
    }
}
