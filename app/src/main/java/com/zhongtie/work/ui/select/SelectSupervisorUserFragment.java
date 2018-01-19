package com.zhongtie.work.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.CompanyTeamEntity;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.db.CompanyUserData;
import com.zhongtie.work.db.CompanyUserData_Table;
import com.zhongtie.work.db.CompanyUserGroupTable;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.select.item.SelectSupervisorGroupItemView;
import com.zhongtie.work.ui.select.item.SelectSupervisorUserItemView;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.widget.EmptyFragment;
import com.zhongtie.work.widget.InputMethodRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;

/**
 * 选择检查人，验证人，整改人，查阅组
 * Auth:Cheek
 * date:2018.1.11
 */

public class SelectSupervisorUserFragment extends BaseFragment implements InputMethodRelativeLayout.OnInputMethodChangedListener, OnSearchContentListener {

    private List<CommonUserEntity> mSelectUserList = new ArrayList<>();

    private List<CompanyTeamEntity> mTeamEntityList;

    private CommonAdapter mSearchAdapter;

    private List<CommonUserEntity> mSearchListData = new ArrayList<>();
    private boolean isInput;

    private InputMethodRelativeLayout mInput;
    private LinearLayout mBottom;
    private LinearLayout mBottomBtn;
    private TextView mCancel;
    private TextView mConfirm;
    private EmptyFragment mSearchList;
    private RecyclerView mTeamList;

    private CommonAdapter mCommonAdapter;

    private List<CommonUserEntity> mAllUserNameInfo;

    @Override
    public int getLayoutViewId() {
//        mSelectUserList = (List<CreateUserEntity>) getArguments().getSerializable(LIST);
        return R.layout.select_user_supervise_fragment;
    }

    @Override
    public void initView() {
        mInput = (InputMethodRelativeLayout) findViewById(R.id.input);
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mBottomBtn = (LinearLayout) findViewById(R.id.bottom_btn);
        mCancel = (TextView) findViewById(R.id.cancel);
        mConfirm = (TextView) findViewById(R.id.confirm);
        mTeamList = (RecyclerView) findViewById(R.id.team_list);
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
        mSearchList.getRecyclerView().setLayoutManager(new GridLayoutManager(getAppContext(), 2));

        mSearchAdapter = new CommonAdapter(mSearchListData).register(SelectSupervisorUserItemView.class);
        mSearchList.getRecyclerView().setAdapter(mSearchAdapter);
        mSearchList.setEmptyView(R.layout.placeholder_empty_view);
    }

    private void resultList() {
        Flowable.fromIterable(mTeamEntityList)
                .flatMap(companyTeamEntity -> Flowable.fromIterable(companyTeamEntity.getTeamUserEntities()))
                .filter(CommonUserEntity::isSelect)
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
    public void userEntityEvent(CommonUserEntity createUserEntity) {
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
                .compose(Network.networkIO())
                .subscribe(createUserEntities -> {
                    mTeamList.getAdapter().notifyDataSetChanged();
                }, throwable -> {
                });
    }


    @Override
    protected void initData() {
        mAllUserNameInfo = new ArrayList<>();
        Flowable.fromCallable(() -> SQLite.select().from(CompanyUserGroupTable.class)
                .queryList())
                .flatMap(Flowable::fromIterable)
                .map(this::getCompanyTeamData)
                .toList()
                .toFlowable()
                .compose(Network.networkIO())
                .subscribe(companyTeamEntities -> {
                    mTeamEntityList = companyTeamEntities;
                    mCommonAdapter = new CommonAdapter(companyTeamEntities).register(SelectSupervisorGroupItemView.class).register(SelectSupervisorUserItemView.class);
                    mTeamList.setAdapter(mCommonAdapter);
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    @WorkerThread
    @NonNull
    private CompanyTeamEntity getCompanyTeamData(CompanyUserGroupTable group) {
        CompanyTeamEntity companyTeamEntity = new CompanyTeamEntity();
        companyTeamEntity.setTeamName(group.getGroupName());
        List<CompanyUserData> userDataList = SQLite.select().from(CompanyUserData.class).where(CompanyUserData_Table.id.in(group.getUserList())).queryList();
        List<CommonUserEntity> createUserEntities = new ArrayList<>();
        for (int i = 0; i < userDataList.size(); i++) {
            CommonUserEntity entity = new CommonUserEntity().convertUser(userDataList.get(i));
            for (int j = 0; j < mSelectUserList.size(); j++) {
                CommonUserEntity b = mSelectUserList.get(j);
                if (b.getUserId() == entity.getUserId()) {
                    entity.setSelect(true);
                    break;
                }
            }
            createUserEntities.add(entity);
        }
        mAllUserNameInfo.addAll(createUserEntities);
        companyTeamEntity.setTeamUserEntities(createUserEntities);
        return companyTeamEntity;
    }

    @Override
    public void showInputMethod() {
        this.isInput = true;
        mBottom.setVisibility(View.GONE);
    }

    @Override
    public void hideInputMethod() {
        this.isInput = false;
        mBottom.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSearch(String searchContent) {
        if (TextUtil.isEmpty(searchContent)) {
            mSearchList.setVisibility(View.GONE);
            mTeamList.setVisibility(View.VISIBLE);
        } else {
            mSearchList.setVisibility(View.VISIBLE);
            mTeamList.setVisibility(View.GONE);
        }

        Flowable.fromIterable(mTeamEntityList)
                .flatMap(companyTeamEntity -> Flowable.fromIterable(companyTeamEntity.getTeamUserEntities()))
                .filter(createUserEntity -> createUserEntity.getUserName().contains(searchContent))
                .toList()
                .toFlowable()
                .subscribe(createUserEntities -> {
                    mSearchListData.clear();
                    mSearchListData.addAll(createUserEntities);
                    mSearchAdapter.notifyDataSetChanged();
                }, throwable -> {
                });
    }
}
