package com.zhongtie.work.ui.select;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.SelectSafeEventEntity;
import com.zhongtie.work.data.SelectSafeEventList;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.RewardPunishApi;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.select.item.SelectSupervisorNameItemView;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.widget.DividerItemDecoration;
import com.zhongtie.work.widget.KDividerItemDecoration;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 选择督导信息
 *
 * @author Chaek
 * @date:2018.1.13
 */

public class SelectSupervisorFragment extends BaseFragment implements OnRecyclerItemClickListener<SelectSafeEventEntity> {
    private CommonAdapter commonAdapter;
    private RecyclerView mList;

    private List<SelectSafeEventEntity> mSelectSafeEventEntities = new ArrayList<>();


    public static void start(Context context) {
        CommonFragmentActivity.newInstance(context, SelectSupervisorFragment.class, R.string.select_safe_event_title);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.common_status_list;
    }

    @Override
    public void initView() {
        setTitle(R.string.select_safe_event_title);
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
        commonAdapter.setListData(mSelectSafeEventEntities);
        commonAdapter.notifyDataSetChanged();
        fetchSafeEventList();
    }

    @Override
    public void onClickRefresh() {
        super.onClickRefresh();
        fetchSafeEventList();
    }

    private void fetchSafeEventList() {
        initLoading();
        addDispose(Http.netServer(RewardPunishApi.class)
                .getPunisnSafeEvent(Cache.getUserID(), Cache.getSelectCompany())
                .map(new NetWorkFunc1<>())
                .flatMap(eventList -> Flowable.fromIterable(eventList)
                        .map(selectSafeEventList -> {
                            SelectSafeEventEntity title = new SelectSafeEventEntity();
                            title.setTime(selectSafeEventList.getTime());
                            List<SelectSafeEventEntity> list = selectSafeEventList.getEvents();
                            list.add(0, title);
                            return list;
                        }).flatMap(Flowable::fromIterable)
                        .toList()
                        .toFlowable())
                .compose(Network.networkIO())
                .subscribe(selectSafeEventEntities -> {
                    mSelectSafeEventEntities.clear();
                    mSelectSafeEventEntities.addAll(selectSafeEventEntities);
                    if (mSelectSafeEventEntities.isEmpty()) {
                        mStatusView.showEmpty();
                    } else {
                        commonAdapter.notifyDataSetChanged();
                        initSuccess();
                    }
                }, throwable -> {
                    initFail();
                    throwable.printStackTrace();
                }));
    }


    @Override
    public void onClick(SelectSafeEventEntity supervisorInfoEntity, int index) {
        if (supervisorInfoEntity.getId() == 0) {
            return;
        }
        supervisorInfoEntity.post();
        getActivity().finish();
    }
}
