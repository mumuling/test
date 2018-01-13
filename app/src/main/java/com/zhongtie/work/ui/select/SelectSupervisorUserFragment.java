package com.zhongtie.work.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.CompanyTeamEntity;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.select.item.SelectSupervisorGroupItemView;
import com.zhongtie.work.widget.EmptyFragment;
import com.zhongtie.work.widget.InputMethodRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.safe.SafeSupervisionCreate2Fragment.imageUrls;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;

/**
 * 选择检查人，验证人，整改人，查阅组
 * Auth:Cheek
 * date:2018.1.11
 */

public class SelectSupervisorUserFragment extends BaseFragment implements InputMethodRelativeLayout.OnInputMethodChangedListener, OnSearchContentListener {

    private CommonAdapter mSelectInfoAdapter;
    private List<CreateUserEntity> createUserEntities = new ArrayList<>();
    private InputMethodRelativeLayout mInput;

    private List<CompanyTeamEntity> mTeamEntityList;

    private CommonAdapter mSearchAdapter;
    private boolean isInput;


    private LinearLayout mBottom;
    private LinearLayout mBottomBtn;
    private TextView mCancel;
    private TextView mConfirm;
    private EmptyFragment mSearchList;
    private RecyclerView mTeamList;


    @Override
    public int getLayoutViewId() {
        createUserEntities = (List<CreateUserEntity>) getArguments().getSerializable(LIST);
        return R.layout.select_user_supervise_fragment;
    }

    @Override
    public void initView() {
        mInput = (InputMethodRelativeLayout) findViewById(R.id.input);
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mBottomBtn = (LinearLayout) findViewById(R.id.bottom_btn);
        mCancel = (TextView) findViewById(R.id.cancel);
        mConfirm = (TextView) findViewById(R.id.confirm);
        mSearchList = (EmptyFragment) findViewById(R.id.search_list);
        mTeamList = (RecyclerView) findViewById(R.id.team_list);

        mInput.setInputMethodChangedListener(this);


        mCancel.setOnClickListener(v -> getActivity().finish());
        mConfirm.setOnClickListener(v -> {
            //点击确定返回数据
            resultList();
        });
    }

    private void resultList() {
        Flowable.fromIterable(mTeamEntityList)
                .flatMap(companyTeamEntity -> Flowable.fromIterable(companyTeamEntity.getTeamUserEntities()))
                .filter(CreateUserEntity::isSelect)
                .toList()
                .toFlowable()
                .subscribe(createUserEntities -> {
                    if (!createUserEntities.isEmpty()) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(TITLE, getArguments().getString(TITLE));
                        bundle.putSerializable(LIST, (Serializable) createUserEntities);
                        intent.putExtras(bundle);
                        getActivity().setResult(RESULT_OK, intent);
                        getActivity().finish();
                    } else {
                        showToast("请选择");
                    }
                }, throwable -> {
                });

    }

    @Subscribe
    public void userEntityEvent(CreateUserEntity createUserEntity) {
        Flowable.fromIterable(mTeamEntityList)
                .flatMap(companyTeamEntity -> Flowable.fromIterable(companyTeamEntity.getTeamUserEntities()))
                .map(data -> {
                    if (!data.equals(createUserEntity)) {
                        data.setSelect(false);
                    }
                    return data;
                })
                .toList()
                .toFlowable()
                .compose(Network.netorkIO())
                .subscribe(createUserEntities -> {
                    mTeamList.getAdapter().notifyDataSetChanged();
                }, throwable -> {
                });
    }


    @Override
    protected void initData() {
//        mSearchAdapter = new CommonAdapter().register(SelectTeamUserItemView.class);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL_LIST);
//        dividerItemDecoration.setLineColor(Util.getColor(R.color.white));
//        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(5));
//        mSearchList.getRecyclerView().addItemDecoration(dividerItemDecoration);
//        mSearchList.getRecyclerView().setAdapter(mSearchAdapter);
//        mSearchList.setEmptyView(R.layout.placeholder_empty_view);

        initTest();
//        mSelectInfoAdapter = new CommonAdapter(createUserEntities);
//        mSelectInfoAdapter.register(SelectSupervisorGroupItemView.class);
//        mTeamList.setAdapter(mSelectInfoAdapter);

    }

    private void initTest() {
        mTeamEntityList = new ArrayList<>();
        for (int i = 1, len = 6; i < len; i++) {
            CompanyTeamEntity temp = new CompanyTeamEntity();
            temp.setTeamName("业务部" + i);

            List<CreateUserEntity> userList = new ArrayList<>();
            for (int j = 1, l = 7; j < l; j++) {
                userList.add(new CreateUserEntity("用户" + i, imageUrls[i], j * i));
            }
            temp.setTeamUserEntities(userList);
            mTeamEntityList.add(temp);
        }
        CommonAdapter list = new CommonAdapter(mTeamEntityList).register(SelectSupervisorGroupItemView.class);
        mTeamList.setAdapter(list);
    }

    @Override
    public void showInputMethod() {
        this.isInput = true;
//        hideTeamGroupView();
        mBottom.setVisibility(View.GONE);
    }

    @Override
    public void hideInputMethod() {
        this.isInput = false;
        mBottom.setVisibility(View.VISIBLE);
    }

    private void showTeamGroupView() {
        mSearchList.setVisibility(View.GONE);
    }

    private void hideTeamGroupView() {
        mSearchList.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSearch(String searchContent) {
//        Flowable.fromIterable(mTeamEntityList)
//                .flatMap(companyTeamEntity -> Flowable.fromIterable(companyTeamEntity.getTeamUserEntities()))
//                .filter(createUserEntity -> createUserEntity.getUserName().contains(searchContent))
//                .toList()
//                .toFlowable()
//                .subscribe(createUserEntities -> {
//                    mSearchAdapter.setListData(createUserEntities);
//                    mSearchAdapter.notifyDataSetChanged();
//                }, throwable -> {
//                });
    }
}
