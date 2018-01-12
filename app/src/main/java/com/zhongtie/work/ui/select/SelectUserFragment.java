package com.zhongtie.work.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.CompanyTeamEntity;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.safe.item.CreateUserItemView;
import com.zhongtie.work.ui.select.item.SelectTeamItemView;
import com.zhongtie.work.ui.select.item.SelectTeamUserItemView;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.DividerItemDecoration;
import com.zhongtie.work.widget.EmptyFragment;
import com.zhongtie.work.widget.InputMethodRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.safe.SafeSupervisionCreate2Fragment.imageUrls;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 选择检查人，验证人，整改人，查阅组
 * Auth:Cheek
 * date:2018.1.11
 */

public class SelectUserFragment extends BaseFragment implements InputMethodRelativeLayout.OnInputMethodChangedListener, TextWatcher {
    private LinearLayout mBottom;
    private TextView mItemUserListTitle;
    private TextView mItemUserListTip;
    private ImageView mItemUserAddImg;
    private RecyclerView mTeamGroupList;
    private TextView mUpdateDownloadCancel;
    private TextView mUpdateBackGroundDownload;
    private AppCompatEditText mSearch;
    private RecyclerView mTempList;

    private CommonAdapter mSelectInfoAdapter;
    private List<CreateUserEntity> createUserEntities = new ArrayList<>();
    private InputMethodRelativeLayout mInput;

    private List<CompanyTeamEntity> mTeamEntityList;
    private EmptyFragment mSearchList;

    private CommonAdapter mSearchAdapter;
    private boolean isInput;


    @Override
    public int getLayoutViewId() {
        createUserEntities = (List<CreateUserEntity>) getArguments().getSerializable(LIST);
        return R.layout.select_user_fragment;
    }

    @Override
    public void initView() {
        mInput = (InputMethodRelativeLayout) findViewById(R.id.input);
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mItemUserListTitle = (TextView) findViewById(R.id.item_user_list_title);
        mItemUserListTip = (TextView) findViewById(R.id.item_user_list_tip);
        mItemUserAddImg = (ImageView) findViewById(R.id.item_user_add_img);
        mTeamGroupList = (RecyclerView) findViewById(R.id.check_examine_list);
        mTeamGroupList.setLayoutManager(new LinearLayoutManager(getContext()));
        mUpdateDownloadCancel = (TextView) findViewById(R.id.cancel);
        mUpdateBackGroundDownload = (TextView) findViewById(R.id.confirm);
        mSearch = (AppCompatEditText) findViewById(R.id.search);
        mSearchList = (EmptyFragment) findViewById(R.id.search_list);
        mTempList = (RecyclerView) findViewById(R.id.temp_list);
        mInput.setInputMethodChangedListener(this);
        mSearch.addTextChangedListener(this);
        mUpdateDownloadCancel.setOnClickListener(v -> getActivity().finish());
        mUpdateBackGroundDownload.setOnClickListener(v -> {
            //点击确定返回数据
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(TITLE, getArguments().getString(TITLE));
            bundle.putSerializable(LIST, (Serializable) createUserEntities);
            intent.putExtras(bundle);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        });
    }

    @Subscribe
    public void userEntityEvent(CreateUserEntity createUserEntity) {
        Iterator iterator = createUserEntities.iterator();
        while (iterator.hasNext()) {
            CreateUserEntity userEntity = (CreateUserEntity) iterator.next();
            if (userEntity.getUserId() == createUserEntity.getUserId()) {
                iterator.remove();
            }
        }
        if (createUserEntity.isSelect()) {
            createUserEntities.add(createUserEntity);
        }
        mSelectInfoAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initData() {
        mSearchAdapter = new CommonAdapter().register(SelectTeamUserItemView.class);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.white));
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(5));
        mSearchList.getRecyclerView().addItemDecoration(dividerItemDecoration);
        mSearchList.getRecyclerView().setAdapter(mSearchAdapter);
        mSearchList.setEmptyView(R.layout.placeholder_empty_view);

        initTest();
        mItemUserListTitle.setText("已选成员");
        mItemUserListTip.setText("向右滑动查看更多");
        mSelectInfoAdapter = new CommonAdapter(createUserEntities);
        mTeamGroupList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        mSelectInfoAdapter.register(CreateUserItemView.class);
        mTeamGroupList.setAdapter(mSelectInfoAdapter);

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
        CommonAdapter list = new CommonAdapter(mTeamEntityList).register(SelectTeamItemView.class).register(SelectTeamUserItemView.class);
        mTempList.setAdapter(list);
    }

    @Override
    public void showInputMethod() {
        this.isInput = true;
        hideTeamGroupView();
        mBottom.setVisibility(View.GONE);
    }

    @Override
    public void hideInputMethod() {
        this.isInput = false;
        mBottom.setVisibility(View.VISIBLE);
    }

    private void showTeamGroupView() {
        mSearchList.setVisibility(View.GONE);
        mTeamGroupList.setVisibility(View.VISIBLE);
    }

    private void hideTeamGroupView() {
        mSearchList.setVisibility(View.VISIBLE);
        mTeamGroupList.setVisibility(View.GONE);
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
        Flowable.fromIterable(mTeamEntityList)
                .flatMap(companyTeamEntity -> Flowable.fromIterable(companyTeamEntity.getTeamUserEntities()))
                .filter(createUserEntity -> createUserEntity.getUserName().contains(search))
                .toList()
                .toFlowable()
                .subscribe(createUserEntities -> {
                    mSearchAdapter.setListData(createUserEntities);
                    mSearchAdapter.notifyDataSetChanged();
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
