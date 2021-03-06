package com.zhongtie.work.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
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
import com.zhongtie.work.event.SelectUserDelEvent;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.safe.item.CreateUserItemView;
import com.zhongtie.work.ui.select.item.SelectUserGroupItemView;
import com.zhongtie.work.ui.select.item.SelectUserItemView;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ResourcesUtils;
import com.zhongtie.work.util.parse.BindKey;
import com.zhongtie.work.widget.AdapterDataObserver;
import com.zhongtie.work.widget.DividerItemDecoration;
import com.zhongtie.work.widget.EmptyFragment;
import com.zhongtie.work.widget.InputMethodRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Flowable;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 选择检查人，验证人，整改人，查阅组
 * Auth:Cheek
 * date:2018.1.11
 */

public class SelectUserFragment extends BaseFragment implements InputMethodRelativeLayout.OnInputMethodChangedListener, TextWatcher, AdapterDataObserver.OnAdapterDataChangedListener {
    public static final String MAX_SELECT_COUNT = "max_select_count";

    private LinearLayout mBottom;
    private TextView mItemUserListTitle;
    private TextView mItemUserListTip;
    private ImageView mItemUserAddImg;
    private RecyclerView mSelectRcycler;
    private TextView mUpdateDownloadCancel;
    private TextView mUpdateBackGroundDownload;
    private AppCompatEditText mSearch;
    private RecyclerView mUserGroupList;

    private CommonAdapter mSelectInfoAdapter;
    private InputMethodRelativeLayout mInput;
    private List<CompanyTeamEntity> mTeamEntityList;

    private EmptyFragment mSearchList;
    private CommonAdapter mAllUserListAdapter;

    private CommonAdapter mSearchAdapter;
    private List<CommonUserEntity> mAllUserNameInfo;
    private boolean isInput;

    @BindKey(value = TITLE)
    private String mTitle;
    @BindKey(value = LIST)
    private List<CommonUserEntity> mSelectUserList = new ArrayList<>();

    private String mTip;
    @BindKey(MAX_SELECT_COUNT)
    private int maxSelectCount = -1;

    public static void start(Fragment fragment, String title, List list) {
        start(fragment, title, list, -1);
    }

    public static void start(Fragment fragment, String title, List list, int count) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST, (Serializable) list);
        bundle.putInt(MAX_SELECT_COUNT, count);
        CommonFragmentActivity.newInstance(fragment, SelectUserFragment.class, title, bundle);
    }


    @Override
    public int getLayoutViewId() {
        mTip = "向右滑动查看更多";

        if (mTitle.equals("验证人")) {
            maxSelectCount = 2;
        }
        if (maxSelectCount > 0) {
            mTip = getString(R.string.select_user_count, maxSelectCount);
        }
        return R.layout.select_user_fragment;
    }

    @Override
    public void initView() {
        mInput = (InputMethodRelativeLayout) findViewById(R.id.input);
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mItemUserListTitle = (TextView) findViewById(R.id.item_user_list_title);
        mItemUserListTip = (TextView) findViewById(R.id.item_user_list_tip);
        mItemUserAddImg = (ImageView) findViewById(R.id.item_user_add_img);
        mSelectRcycler = (RecyclerView) findViewById(R.id.check_examine_list);
        //设置横向
        mSelectRcycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mUpdateDownloadCancel = (TextView) findViewById(R.id.cancel);
        mUpdateBackGroundDownload = (TextView) findViewById(R.id.confirm);
        mSearch = (AppCompatEditText) findViewById(R.id.search);
        mSearchList = (EmptyFragment) findViewById(R.id.search_list);

        mUserGroupList = (RecyclerView) findViewById(R.id.temp_list);
        mUserGroupList.setLayoutManager(new LinearLayoutManager(getAppContext()));


        mInput.setInputMethodChangedListener(this);
        mSearch.addTextChangedListener(this);
        mUpdateDownloadCancel.setOnClickListener(v -> getActivity().finish());
        mUpdateBackGroundDownload.setOnClickListener(v -> {
            //点击确定返回数据
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(TITLE, getArguments().getString(TITLE));
            bundle.putSerializable(LIST, (Serializable) mSelectUserList);
            intent.putExtras(bundle);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        });
    }

    @Subscribe
    public void userEntityEvent(CommonUserEntity createUserEntity) {
        Iterator iterator = mSelectUserList.iterator();
        boolean isModify = false;
        while (iterator.hasNext()) {
            CommonUserEntity userEntity = (CommonUserEntity) iterator.next();
            if (userEntity.getUserId() == createUserEntity.getUserId()) {
                isModify = true;
                if (!createUserEntity.isSelect()) {
                    iterator.remove();
                }
                userEntity.setAt(createUserEntity.isAt());
            }
        }

        if (!isModify && maxSelectCount != -1 && mSelectUserList.size() >= maxSelectCount) {
            showToast("最多可选择" + maxSelectCount + "人");
            createUserEntity.setSelect(false);
            createUserEntity.setAt(false);
            mSelectInfoAdapter.notifyDataSetChanged();
            return;
        }

        if (!isModify && createUserEntity.isSelect()) {
            mSelectUserList.add(createUserEntity);
        }
        mSelectInfoAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void delCreateUserEvent(SelectUserDelEvent createUserEntity) {
        for (int i = 0; i < mAllUserNameInfo.size(); i++) {
            CommonUserEntity entity = mAllUserNameInfo.get(i);
            if (entity.getUserId() == createUserEntity.getCreateUserEntity().getUserId()) {
                entity.setAt(false);
                entity.setSelect(false);
                break;
            }
        }
        mAllUserListAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initData() {
        mSearchAdapter = new CommonAdapter().register(SelectUserItemView.class);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.white));
        dividerItemDecoration.setDividerHeight(ResourcesUtils.dip2px(5));

        mSearchList.getRecyclerView().addItemDecoration(dividerItemDecoration);
        mSearchList.getRecyclerView().setAdapter(mSearchAdapter);
        mSearchList.setEmptyView(R.layout.placeholder_empty_view);
        initTest();
        mItemUserListTitle.setText(mTitle);
        mItemUserListTip.setText(mTip);

        mSelectInfoAdapter = new CommonAdapter(mSelectUserList);
        mSelectInfoAdapter.register(CreateUserItemView.class);
        mSelectInfoAdapter.registerAdapterDataObserver(new AdapterDataObserver(this));

        mSelectRcycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        mSelectRcycler.setAdapter(mSelectInfoAdapter);
        onChangeSelectView();
    }

    private void onChangeSelectView() {
        mItemUserListTitle.setText(mTitle);
        if (!mSelectUserList.isEmpty()) {
            mItemUserListTitle.append("(" + mSelectUserList.size() + ")");
        }
    }

    private void initTest() {
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
                    mAllUserListAdapter = new CommonAdapter(companyTeamEntities).register(SelectUserGroupItemView.class)
                            .register(SelectUserItemView.class);
                    mUserGroupList.setAdapter(mAllUserListAdapter);
                }, throwable -> {
                    throwable.printStackTrace();
                });

    }

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
                    entity.setAt(b.isAt());
                    entity.setSelect(b.isSelect());
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

    private void showTeamGroupView() {
        mSearchList.setVisibility(View.GONE);
        mUserGroupList.setVisibility(View.VISIBLE);
    }

    private void hideTeamGroupView() {
        mSearchList.setVisibility(View.VISIBLE);
        mUserGroupList.setVisibility(View.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtil.isEmpty(s)) {
            showTeamGroupView();
        } else {
            hideTeamGroupView();
            searchTeamEntityList(s.toString());
        }
    }

    private void searchTeamEntityList(String search) {
        Flowable.fromIterable(mAllUserNameInfo)
                .filter(createUserEntity -> createUserEntity.getUserName().contains(search))
                .toList()
                .toFlowable()
                .subscribe(createUserEntities -> {
                    mSearchAdapter.setListData(createUserEntities);
                    mSearchAdapter.notifyDataSetChanged();
                }, throwable -> {
                });

    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onChanged() {
        mSearchAdapter.notifyDataSetChanged();
        onChangeSelectView();
    }
}
