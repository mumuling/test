package com.zhongtie.work.ui.user;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.KeyValueEntity;
import com.zhongtie.work.ui.base.BasePresenterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class UserInfoFragment extends BasePresenterFragment<UserContract.Presenter> implements UserContract.View {

    private RecyclerView mList;
    private CommonAdapter commonAdapter;
    private List<KeyValueEntity<String, String>> userInfoList = new ArrayList<>();

    private View mFooterView;

    @Override
    protected UserContract.Presenter getPresenter() {
        return new UserPresenterImpl();
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.base_recyclerview;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mFooterView = LayoutInflater.from(getAppContext()).inflate(R.layout.layout_modify_pw_bottom, mList, false);
        commonAdapter = new CommonAdapter().register(UserInfoItemView.class);
        commonAdapter.addFooterView(mFooterView);
        TextView modifyBtn = mFooterView.findViewById(R.id.modify_password);
        modifyBtn.setText(R.string.modify_password);
        modifyBtn.setOnClickListener(view -> mPresenter.modifyPassword());
    }

    @Override
    protected void initData() {
        userInfoList = new ArrayList<>();
        commonAdapter.setListData(userInfoList);
        mList.setAdapter(commonAdapter);
        mPresenter.fetchUserInfo();
    }

    @Override
    public void modifyPasswordSuccess() {
        showToast("修改成功");
        getActivity().finish();
//        Cache.exitLogin();
    }

    @Override
    public void modifyPasswordFail() {

    }

    @Override
    public void setListUserInfoList(List<KeyValueEntity<String, String>> listUserInfoList) {
        this.userInfoList.clear();
        this.userInfoList.addAll(listUserInfoList);
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public String getModifyPassword() {
        return userInfoList.get(2).getContent();
    }
}
